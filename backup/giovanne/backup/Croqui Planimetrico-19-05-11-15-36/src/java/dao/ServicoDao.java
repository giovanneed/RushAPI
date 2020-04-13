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
            String table_edificacao, String table_lote_ctm, String table_iptu_ctm_geo) throws SQLException
    {
        Connection con = getCon(pc);

        String sql =
            "SELECT icg.indice_cadastral,"+
            "       L.NUM_LOTE_CTM,"+
            "       icg.area_construida,"+
            "       ROUND(sum(SDO_GEOM.SDO_AREA(E.geoloc, .5)), 2) AREA_CONSTRUIDA_GEO,"+
            "       ROUND(sum(SDO_GEOM.SDO_AREA(E.geoloc, .5)) / icg.AREA_CONSTRUIDA, 4) PERCENTUAL"+
            "  FROM " + table_edificacao + " E,"+
            "       " + table_lote_ctm + " L,"+
            "       (SELECT icg.INDICE_CADASTRAL,"+
            "               icg.NULOTCTM,"+
            "               icg.AREA_CONSTRUIDA"+
            "          FROM " + table_iptu_ctm_geo + " icg,"+
            "               (SELECT NULOTCTM, COUNT(*) LOTES"+
            "                  FROM " + table_iptu_ctm_geo +
            "                 WHERE NULOTCTM LIKE '" + lote + "'"+
            "                    OR INDICE_CADASTRAL LIKE '" + loteIptu + "'"+
            "                 GROUP BY NULOTCTM) i"+
            "         WHERE icg.NULOTCTM = i.NULOTCTM"+
            "           AND I.LOTES = 1"+
            "           AND icg.AREA_CONSTRUIDA > 0"+
            "         ORDER BY icg.NULOTCTM) icg"+
            " WHERE L.NUM_LOTE_CTM like icg.NULOTCTM"+
            "   and (SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E.geoloc, .5), 'INSIDE', l.geoloc, 0.5) = 'INSIDE' or"+
            "       SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E.geoloc, .5), 'COVEREDBY', l.geoloc, 0.5) = 'COVEREDBY')"+
            "   and SDO_RELATE(e.geoloc, l.geoloc, 'MASK=ANYINTERACT') = 'TRUE'"+
            " GROUP BY L.NUM_LOTE_CTM, icg.indice_cadastral, icg.area_construida"+
            " ORDER BY PERCENTUAL DESC";

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
