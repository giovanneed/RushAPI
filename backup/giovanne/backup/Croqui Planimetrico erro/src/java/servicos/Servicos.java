/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sql.PooledConnection;

/**
 *
 * @author bruno.finelli
 */
public class Servicos {

    public static void limparDir(String path)
    {
        File dir = new File(path);

        if( dir.exists() )
        {
            File[] files = dir.listFiles();
            for(int i=0; i<files.length; i++)
            {
                files[i].delete();
            }
        }
    }

    public static String[] gerarCroquis(PooledConnection pc, String path, String sessionId, String [] listaLotes, String numQuadraCtm, String [] listaLotesIptu, String quadraIptu, int folhaQuadra, int folhaLote,
            String table_quadra_ctm, String table_trecho, String table_lote_ctm, String table_edificacao, String table_bairro_popular, String table_iptu_ctm_geo)
    {
        quadraIptu = quadraIptu.replace(' ', '_');
        for (int i = 0; i < listaLotesIptu.length; i++)
            listaLotesIptu[i] = listaLotesIptu[i].replace(' ', '_');

        BufferedImage bi = null;

        Connection con = null;
        try {
            con = pc.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Servicos.class.getName()).log(Level.SEVERE, null, ex);
        }

        Croqui croqui = new Croqui(con);
        ArrayList<String> lotesADestacar = new ArrayList<String>();

        for (int i = 0; i < listaLotes.length; i++)
            lotesADestacar.add(listaLotes[i]);

        Rectangle ps;

        String owner = "Piedade";

        String [] images = new String [1 + lotesADestacar.size()];

        // Gera croqui da quadra
        switch(folhaQuadra)
        {
            case 1:
                ps = PageSize.A4;
                break;
            case 2:
                ps = PageSize.A3;
                break;
            case 3:
                ps = PageSize.A2;
                break;
            case 4:
                ps = PageSize.LETTER;
                break;
            default:
                ps = PageSize.A4;
        }

        try {
            bi = croqui.gerarCroquiTipo1(ps, new Integer(numQuadraCtm).intValue(), lotesADestacar, 20, table_quadra_ctm, table_trecho, table_lote_ctm, table_edificacao, table_bairro_popular, table_iptu_ctm_geo);
        } catch (SQLException ex) {
            Logger.getLogger(Servicos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Croqui.CroquiException ex) {
            Logger.getLogger(Servicos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File outputFile = new File(path + sessionId + quadraIptu + ".png");
        try {
            ImageIO.write(bi, "PNG", outputFile);
        } catch (IOException ex) {
            Logger.getLogger(Servicos.class.getName()).log(Level.SEVERE, null, ex);
        }
        images[0] = "output/" + sessionId + quadraIptu + ".png";
        
        // Gera croqui dos lotes
        switch(folhaLote)
        {
            case 1:
                ps = PageSize.A4;
                break;
            case 2:
                ps = PageSize.A3;
                break;
            case 3:
                ps = PageSize.A2;
                break;
            case 4:
                ps = PageSize.LETTER;
                break;
            default:
                ps = PageSize.A4;
        }

        for (int i = 0; i < lotesADestacar.size(); i++)
        {
            try {
                bi = croqui.gerarCroquiTipo2(ps, lotesADestacar.get(i), 20, table_lote_ctm, table_edificacao, table_iptu_ctm_geo);
            } catch (SQLException ex) {
                Logger.getLogger(Servicos.class.getName()).log(Level.SEVERE, null, ex);
            }

            outputFile = new File(path + sessionId + listaLotesIptu[i] + ".png");
            try {
                ImageIO.write(bi, "PNG", outputFile);
            } catch (IOException ex) {
                Logger.getLogger(Servicos.class.getName()).log(Level.SEVERE, null, ex);
            }
            images[1 + i] = "output/" + sessionId + listaLotesIptu[i] + ".png";
        }

        return images;
    }
}
