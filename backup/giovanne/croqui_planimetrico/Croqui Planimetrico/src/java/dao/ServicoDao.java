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
import java.util.Collection;
import java.util.Collections;
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
        //System.out.println(sql);
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

    public static List<Resposta> consultaPorIptu(String loteIptu,PooledConnection pc,
            String table_edificacao, String table_lote_ctm, String table_iptu_ctm_geo, String geometria_edificacao,String geometria_lote_ctm,String numero_do_lote_ctm,String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_area_construida,
            String iptu_ctm_numero_do_lote
            ) throws SQLException{

        ArrayList<Iptu> iptus = new ArrayList<Iptu>();
        Connection con = getCon(pc);
        String sql =    "SELECT icg."+ iptu_ctm_geo_indice_cadastral + ","+
            "            icg."+ iptu_ctm_numero_do_lote + ","+
            "            icg."+ iptu_ctm_geo_area_construida +
            "            FROM " + table_iptu_ctm_geo + " icg,"+
            "               (SELECT "+ iptu_ctm_numero_do_lote + ", COUNT(*) LOTES"+
            "                  FROM " + table_iptu_ctm_geo +
            "           WHERE "+iptu_ctm_geo_indice_cadastral+" like '" + loteIptu + "'" +
            "                 GROUP BY "+ iptu_ctm_numero_do_lote + ") i"+
            " WHERE icg."+iptu_ctm_numero_do_lote +" = i." + iptu_ctm_numero_do_lote +
            "         and icg."+iptu_ctm_geo_indice_cadastral+" like '" + loteIptu + "'"+
            "           AND I.LOTES = 1"+
            "           AND icg." +iptu_ctm_geo_area_construida +" > 0"+
            "         ORDER BY icg."+ iptu_ctm_numero_do_lote;
       // System.out.println(sql);
        Statement stmt = con.createStatement();
       // Long horaInicial = System.currentTimeMillis();
        ResultSet rs = stmt.executeQuery(sql);
       // System.out.println("Tempo de calculo: " +  (System.currentTimeMillis() - horaInicial) + " milisegundos\n-----------------------------");
        while (rs.next())
        {
            iptus.add(new Iptu(rs.getString(1), rs.getString(2), rs.getDouble(3)));
        }
        fecharCon(con);

        List<Resposta> lotes = new ArrayList<Resposta>();
        for (Iptu iptu : iptus) {
            con = getCon(pc);
            sql = "SELECT ROUND(sum(SDO_GEOM.SDO_AREA(E."+geometria_edificacao+", .5)), 2) AREA_CONSTRUIDA_GEO,"+
                  "ROUND(sum(SDO_GEOM.SDO_AREA(E."+geometria_edificacao+", .5)) / "+iptu.getArea_construída()+", 4) PERCENTUAL" +
                  " FROM " + table_lote_ctm + " L,"+
                  " " + table_edificacao+ " E"+
                  " WHERE SDO_relate(l."+geometria_lote_ctm+",SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+", .5),'MASK=CONTAINS+COVERS') = 'TRUE'"+
                  " and SDO_FILTER(E."+geometria_edificacao+", l."+geometria_lote_ctm+") = 'TRUE'"+
                  " and L."+numero_do_lote_ctm+" = '"+iptu.getNulotctm()+"'"+
                  " and L.status_trabalho = 'OK' and L.tipo_fechamento = 'LOTE CTM'";

           // System.out.println(sql);
            stmt = con.createStatement();
            //horaInicial = System.currentTimeMillis();
            rs = stmt.executeQuery(sql);
           // System.out.println("Tempo de calculo: " +  (System.currentTimeMillis() - horaInicial) + " milisegundos\n-----------------------------");
             while (rs.next())
            {
                double areaGeo = rs.getDouble(1);
                double percentual = (Math.round((rs.getDouble(2) - 1) * 10000))/100.0;
                Resposta p = new Resposta(iptu.getIndice_cadastral(), iptu.getNulotctm(), iptu.getArea_construída(), areaGeo, percentual);
                lotes.add(p);
            }
           fecharCon(con);
        }
       return lotes;
    }

    //table_edificacao = piedade.EDIFICACAO
    //table_lote_ctm = piedade.LOTE_CTM
    //table_iptu_ctm_geo = piedade.iptu_ctm_geo
    public static List<Resposta> consultaPorLote(String lote,PooledConnection pc,
            String table_edificacao, String table_lote_ctm, String table_iptu_ctm_geo, String geometria_edificacao,String geometria_lote_ctm,String numero_do_lote_ctm,String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_area_construida,
            String iptu_ctm_numero_do_lote,String iptu_ctm_geo_numero_do_imovel) throws SQLException
    {
    ArrayList<Iptu> iptus = new ArrayList<Iptu>();
        Connection con = getCon(pc);
        String sql =    "SELECT icg."+ iptu_ctm_geo_indice_cadastral + ","+
            "               icg."+ iptu_ctm_numero_do_lote + ","+
            "               icg."+ iptu_ctm_geo_area_construida +
            "          FROM " + table_iptu_ctm_geo + " icg,"+
            "               (SELECT "+ iptu_ctm_numero_do_lote + ", COUNT(*) LOTES"+
            "                  FROM " + table_iptu_ctm_geo +
            "   WHERE "+ iptu_ctm_numero_do_lote + " like '" + lote + "'"+
            "   GROUP BY "+ iptu_ctm_numero_do_lote + ") i"+
            "         WHERE icg."+iptu_ctm_numero_do_lote +" = i."+iptu_ctm_numero_do_lote+
            "           AND I.LOTES = 1"+
            "           AND icg." +iptu_ctm_geo_area_construida +" > 0"+
            "         ORDER BY icg."+ iptu_ctm_numero_do_lote;

        //System.out.println(sql);
        Statement stmt = con.createStatement();
        //Long horaInicial = System.currentTimeMillis();
        ResultSet rs = stmt.executeQuery(sql);
        //System.out.println("Tempo de calculo: " + (System.currentTimeMillis() - horaInicial) + " milisegundos\n-----------------------------");
        while (rs.next())
        {
            iptus.add(new Iptu(rs.getString(1), rs.getString(2), rs.getDouble(3)));
        }
        fecharCon(con);

        List<Resposta> lotes = new ArrayList<Resposta>();
        for (Iptu iptu : iptus) {
            con = getCon(pc);
            sql = "SELECT ROUND(sum(SDO_GEOM.SDO_AREA(E."+geometria_edificacao+", .5)), 2) AREA_CONSTRUIDA_GEO,"+
                  "ROUND(sum(SDO_GEOM.SDO_AREA(E."+geometria_edificacao+", .5)) / "+iptu.getArea_construída()+", 4) PERCENTUAL" +
                  " FROM " + table_lote_ctm + " L,"+
                  " " + table_edificacao+ " E"+
                  " WHERE SDO_relate(l."+geometria_lote_ctm+",SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+", .5),'MASK=CONTAINS+COVERS') = 'TRUE'"+
                  " and SDO_FILTER(E."+geometria_edificacao+", l."+geometria_lote_ctm+") = 'TRUE'"+
                  " and L."+numero_do_lote_ctm+" = '"+iptu.getNulotctm()+"'"+
                  " and L.status_trabalho = 'OK' and L.tipo_fechamento = 'LOTE CTM'";
               
            //System.out.println(sql);
            stmt = con.createStatement();
            //horaInicial = System.currentTimeMillis();
            rs = stmt.executeQuery(sql);
            //System.out.println("Tempo de calculo: " +  (System.currentTimeMillis() - horaInicial) + " milisegundos\n-----------------------------");

             while (rs.next())
            {
                double areaGeo = rs.getDouble(1);
                double percentual = (Math.round((rs.getDouble(2) - 1) * 10000))/100.0;
                Resposta p = new Resposta(iptu.getIndice_cadastral(), iptu.getNulotctm(), iptu.getArea_construída(), areaGeo, percentual);
                lotes.add(p);
            }
           fecharCon(con);
        }
       return lotes;
    }
}
