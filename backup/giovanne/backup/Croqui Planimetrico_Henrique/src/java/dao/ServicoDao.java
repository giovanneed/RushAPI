    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entidades.Resposta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;
import javax.servlet.ServletContext;
import javax.sql.PooledConnection;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

/**
 *
 * @author bruno.finelli
 */
public class ServicoDao {

    public static PooledConnection criarPool (ServletContext context)
    {
        try {
            OracleConnectionPoolDataSource ocpds;
            ocpds = new OracleConnectionPoolDataSource();
            ocpds.setURL(context.getInitParameter("db_url"));
            ocpds.setUser(context.getInitParameter("db_user"));
            ocpds.setPassword(context.getInitParameter("db_password"));

            return ocpds.getPooledConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void fecharPool (PooledConnection pc)
    {
        try {
            pc.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Connection getCon (PooledConnection pc)
    {
        try {
            return pc.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void fecharCon (Connection con)
    {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //aplic_geo = aplicacoes_geo
    //usuarios_geo = usuarios_geo
    //usuarios_aplic_geo = usuarios_aplic_geo
    public static boolean autorizar(PooledConnection pc, String nome, String aplic,
            String table_aplic_geo, String table_usuarios_geo, String table_usuarios_aplic_geo)
    {
        Connection con = getCon(pc);

        String sql =
                "Select count(*) " +
                "from " + table_aplic_geo + " ag, " + table_usuarios_geo + " ug, " + table_usuarios_aplic_geo + " uag " +
                "where ag.aplicacao='" + aplic + "' AND ug.nome='"+nome+"' AND uag.aplicacao = ag.id AND uag.usuario = ug.id";

        boolean autorizado = false;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                int permissoes = rs.getInt(1);
                if (permissoes == 1)
                    autorizado = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        fecharCon(con);
        return autorizado;
    }

    //table_edificacao = piedade.EDIFICACAO
    //table_lote_ctm = piedade.LOTE_CTM
    //table_iptu_ctm_geo = piedade.iptu_ctm_geo
    public static List<Resposta> consultaPorLote(String lote, String loteIptu, PooledConnection pc,
            String table_edificacao, String table_lote_ctm, String table_iptu_ctm_geo, String geometria_edificacao,String geometria_lote_ctm,String numero_do_lote_ctm,String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_area_construida,
            String iptu_ctm_numero_do_lote,String iptu_ctm_geo_numero_do_imovel) throws SQLException
    {
        Connection con = getCon(pc);

        String sql =
            "SELECT icg."+iptu_ctm_geo_indice_cadastral + ","+
            "       L."+ numero_do_lote_ctm + ","+
            "       icg." +iptu_ctm_geo_area_construida +","+
            "       ROUND(sum(SDO_GEOM.SDO_AREA(E."+geometria_edificacao+", .5)), 2) AREA_CONSTRUIDA_GEO,"+
            "       ROUND(sum(SDO_GEOM.SDO_AREA(E."+geometria_edificacao+", .5)) / icg.AREA_CONSTRUIDA, 4) PERCENTUAL"+
            "  FROM " + table_edificacao + " E,"+
            "       " + table_lote_ctm + " L,"+
            "       (SELECT icg."+ iptu_ctm_geo_indice_cadastral + ","+
            "               icg."+ iptu_ctm_numero_do_lote + ","+
            "               icg."+ iptu_ctm_geo_area_construida +
            "          FROM " + table_iptu_ctm_geo + " icg,"+
            "               (SELECT "+ iptu_ctm_numero_do_lote + ", COUNT(*) LOTES"+
            "                  FROM " + table_iptu_ctm_geo +
            "                 WHERE "+ iptu_ctm_numero_do_lote + " LIKE '" + lote + "'"+
            "                    OR "+iptu_ctm_geo_indice_cadastral+" LIKE '" + loteIptu + "'"+
            "                 GROUP BY "+ iptu_ctm_numero_do_lote + ") i"+
            "         WHERE icg."+iptu_ctm_numero_do_lote +" = i."+iptu_ctm_numero_do_lote +
            "           AND I.LOTES = 1"+
            "           AND icg." +iptu_ctm_geo_area_construida +" > 0"+
            "         ORDER BY icg."+ iptu_ctm_numero_do_lote + ") icg"+
            " WHERE L."+numero_do_lote_ctm+" like icg."+ iptu_ctm_numero_do_lote +
            "   and (SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+", .5), 'INSIDE', l."+geometria_lote_ctm+", 0.5) = 'INSIDE' or"+
            "       SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+", .5), 'COVEREDBY', l."+geometria_lote_ctm+", 0.5) = 'COVEREDBY')"+
            "   and SDO_RELATE(e."+geometria_edificacao+", l."+geometria_lote_ctm+", 'MASK=ANYINTERACT') = 'TRUE'"+
            " GROUP BY L."+numero_do_lote_ctm+", icg."+ iptu_ctm_geo_indice_cadastral + ", icg."+ iptu_ctm_geo_area_construida +
            " ORDER BY PERCENTUAL DESC";

        System.out.println(sql);
        
        Statement stmt = con.createStatement();
        List<Resposta> lotes = new ArrayList<Resposta>();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next())
        {
            String indice_cadastral = rs.getString(1);
            String lotectm = rs.getString(2);
            double areaIptu = rs.getDouble(3);
            double areaGeo = rs.getDouble(4);
            double percentual = (Math.round((rs.getDouble(5) - 1) * 10000))/100.0;
            //double percentual = rs.getDouble(5);
            Resposta p = new Resposta(indice_cadastral, lotectm, areaIptu, areaGeo, percentual);
            lotes.add(p);
        }

        fecharCon(con);
        return lotes;
    }
}
