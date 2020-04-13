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
            String table_quadra_ctm, String table_trecho, String table_lote_ctm, String table_edificacao, String table_bairro_popular, String table_iptu_ctm_geo,
            String table_quadra_ctm_coluna_geometria,String table_quadra_ctm_coluna_numero_da_quadra,String table_trecho_coluna_geometria,String table_trecho_coluna_id_logradouro,
            String table_trecho_coluna_mi_prinx, String table_trecho_coluna_tipo_logradouro, String table_trecho_coluna_numero_do_logradouro,String table_bairro_popular_coluna_geometria,
            String table_bairro_popular_coluna_nome_do_bairro,String table_iptu_ctm_geo_coluna_indice_cadastral,String table_iptu_ctm_geo_coluna_area_construida,
            String table_iptu_ctm_geo_coluna_numero_do_lote,String table_iptu_ctm_geo_coluna_numero_do_imovel,String table_edificacao_coluna_geometria,String table_lote_ctm_coluna_geometria,
            String table_lote_ctm_coluna_numero_do_lote)
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
            bi = croqui.gerarCroquiTipo1(ps, new Integer(numQuadraCtm).intValue(), lotesADestacar, 20, table_quadra_ctm, table_trecho, table_lote_ctm, table_edificacao, table_bairro_popular, table_iptu_ctm_geo,
                 table_quadra_ctm_coluna_geometria,table_quadra_ctm_coluna_numero_da_quadra,table_trecho_coluna_geometria,table_trecho_coluna_id_logradouro,table_trecho_coluna_mi_prinx, table_trecho_coluna_tipo_logradouro,
                 table_trecho_coluna_numero_do_logradouro,table_lote_ctm_coluna_geometria,table_edificacao_coluna_geometria,table_bairro_popular_coluna_geometria,table_bairro_popular_coluna_nome_do_bairro,table_iptu_ctm_geo_coluna_indice_cadastral,
                 table_iptu_ctm_geo_coluna_area_construida,table_iptu_ctm_geo_coluna_numero_do_lote,table_iptu_ctm_geo_coluna_numero_do_imovel,table_lote_ctm_coluna_numero_do_lote);
        } catch (SQLException ex) {
            //retorna o erro na primeira posicao do vetor que será retornado, esse vetor deveria conter apenas os caminhos das imagens geradas.
            images[0] = ex.getMessage();
            return images;
        } catch (Croqui.CroquiException ex) {
            //retorna o erro na primeira posicao do vetor que será retornado, esse vetor deveria conter apenas os caminhos das imagens geradas.
            images[0] = ex.getMessage();
            return images;
        }

        File outputFile = new File(path + sessionId + quadraIptu + ".png");
        try {
            ImageIO.write(bi, "PNG", outputFile);
        } catch (IOException ex) {
            //retorna o erro na primeira posicao do vetor que será retornado, esse vetor deveria conter apenas os caminhos das imagens geradas.
            images[0] = ex.getMessage();
            return images;
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
                bi = croqui.gerarCroquiTipo2(ps, lotesADestacar.get(i), 20, table_lote_ctm, table_edificacao, table_iptu_ctm_geo,table_edificacao_coluna_geometria,
                                                      table_lote_ctm_coluna_geometria,
                                                      table_lote_ctm_coluna_numero_do_lote,table_iptu_ctm_geo_coluna_indice_cadastral,
                                                      table_iptu_ctm_geo_coluna_area_construida,
                                                      table_iptu_ctm_geo_coluna_numero_do_lote,
                                                      table_iptu_ctm_geo_coluna_numero_do_imovel);
            } catch (SQLException ex) {
                //retorna o erro na primeira posicao do vetor que será retornado, esse vetor deveria conter apenas os caminhos das imagens geradas.
                images[0] = ex.getMessage();
                return images;
            }

            outputFile = new File(path + sessionId + listaLotesIptu[i] + ".png");
            try {
                ImageIO.write(bi, "PNG", outputFile);
            } catch (IOException ex) {
                //retorna o erro na primeira posicao do vetor que será retornado, esse vetor deveria conter apenas os caminhos das imagens geradas.
                images[0] = ex.getMessage();
                return images;
            }
            images[1 + i] = "output/" + sessionId + listaLotesIptu[i] + ".png";
        }

        return images;
    }
}
