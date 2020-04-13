/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

import entidades.Resposta;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.PooledConnection;

/**
 *
 * @author Filipe Lomelino Cardoso.
 */
public class ServicoPesquisar {

    /**
     * Método que acessa o serviço dao para buscar os dados do bd.
     * @param lote - Número ctm do lote.
     * @param loteIptu - Número do índice cadastral do iptu.
     * @return- Retorna uma tabela com 4 colunas indice_cadastral, lotectm,
     * área construida da tabela iptu, área construida da tabela geo, percentual.
     */
    public static List<Resposta> pesquisaPorLote(String lote, String loteIptu, PooledConnection pc,
            String table_edificacao, String table_lote_ctm, String table_iptu_ctm_geo,
            String geometria_edificacao,String geometria_lote_ctm,String numero_do_lote_ctm,String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_area_construida,
            String table_iptu_ctm_numero_do_lote,String table_iptu_ctm_geo_numero_do_imovel)
    {
        if (loteIptu.length() > 0)
        {
            if (loteIptu.length() == 6)
                loteIptu += " ";
            loteIptu += '%';
        }
        else if (lote.length() > 0)
            lote += '%';
        try {
            return dao.ServicoDao.consultaPorLote(lote, loteIptu, pc, table_edificacao, table_lote_ctm, table_iptu_ctm_geo,geometria_edificacao,geometria_lote_ctm,numero_do_lote_ctm,iptu_ctm_geo_indice_cadastral,iptu_ctm_geo_area_construida,
            table_iptu_ctm_numero_do_lote,table_iptu_ctm_geo_numero_do_imovel);
        } catch (SQLException ex) {
            Logger.getLogger(ServicoPesquisar.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        return null;
    }
}
