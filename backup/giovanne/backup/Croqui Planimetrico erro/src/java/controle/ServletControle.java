/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.PooledConnection;


import servicos.Zipando;

/**
 *
 * @author bruno.finelli
 */
public class ServletControle extends HttpServlet
{


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String outputpath = request.getSession().getServletContext().getRealPath("") + "\\" + session.getServletContext().getInitParameter("output_path") + "\\";
        
        

        PooledConnection pc = (PooledConnection) session.getAttribute("pooledConnection");

        if (request.getParameter("function") != null)
        {
           
            // <editor-fold  defaultstate="collapsed" desc="Busca dos lotes.">
            if (request.getParameter("function").compareTo("blotes") == 0)
            {
                String lote = "";
                String loteIptu = "";
                if (request.getParameter("tipo").compareTo("iptu") == 0)
                {
                    session.setAttribute("radio", "iptu");
                    loteIptu = request.getParameter("codigo"); //REMOVER O "asdasd" PARA VOLTAR A FUNCIONAR
                } else if (request.getParameter("tipo").compareTo("ctm") == 0)
                {
                    session.setAttribute("radio", "ctm");
                    lote = request.getParameter("codigo"); //REMOVER O "asdasd" PARA VOLTAR A FUNCIONAR
                }

                session.setAttribute("codigo", request.getParameter("codigo"));

                String table_edificacao = getServletConfig().getServletContext().getInitParameter("table_edificacao");
                String table_lote_ctm = getServletConfig().getServletContext().getInitParameter("table_lote_ctm");
                String table_iptu_ctm_geo = getServletConfig().getServletContext().getInitParameter("table_iptu_ctm_geo");

                session.setAttribute("lotes", servicos.ServicoPesquisar.pesquisaPorLote(lote, loteIptu, pc, table_edificacao, table_lote_ctm, table_iptu_ctm_geo));

                session.setAttribute("lotes_old", session.getAttribute("lotes"));

                
                
                //servicos.Servicos.limparDir(session.getServletContext().getInitParameter("output_path") + session.getId());
                servicos.Servicos.limparDir(outputpath);

                session.setAttribute("images", null);
                session.setAttribute("listaLotes", null);
                session.setAttribute("zip_path", null);
            // </editor-fold>
            }else
              
            // <editor-fold  defaultstate="collapsed" desc="Geração dos croquis.">
            if (request.getParameter("function").compareTo("croqui") == 0)
            {
                if (session.getAttribute("lotes") == null)
                    session.setAttribute("lotes", session.getAttribute("lotes_old"));

                String lotes = request.getParameter("lotes");
                String [] listaLotes = lotes.split(";");

                session.setAttribute("listaLotes", listaLotes);

                String quadra = request.getParameter("quadra");

                String lotesIptu = request.getParameter("lotesIptu");
                String [] listaLotesIptu = lotesIptu.split(";");
                String quadraIptu = request.getParameter("quadraIptu");

                int folhaQuadra = Integer.parseInt((String)request.getParameter("folhaQuadra"));
                session.setAttribute("folhaQuadra", String.valueOf(folhaQuadra));
                int folhaLote = Integer.parseInt((String)request.getParameter("folhaLote"));
                session.setAttribute("folhaLote", String.valueOf(folhaLote));

                session.setAttribute("quadraIptu", quadraIptu);

                servicos.Servicos.limparDir(outputpath);

                String table_quadra_ctm = getServletConfig().getServletContext().getInitParameter("table_quadra_ctm");
                String table_trecho = getServletConfig().getServletContext().getInitParameter("table_trecho");
                String table_lote_ctm = getServletConfig().getServletContext().getInitParameter("table_lote_ctm");
                String table_edificacao = getServletConfig().getServletContext().getInitParameter("table_edificacao");
                String table_bairro_popular = getServletConfig().getServletContext().getInitParameter("table_bairro_popular");
                String table_iptu_ctm_geo = getServletConfig().getServletContext().getInitParameter("table_iptu_ctm_geo");

                

                session.setAttribute("images",
                    servicos.Servicos.gerarCroquis(pc,
                                                      outputpath,
                                                      session.getId() + "/",
                                                      listaLotes,
                                                      quadra,
                                                      listaLotesIptu,
                                                      quadraIptu,
                                                      folhaQuadra,
                                                      folhaLote,
                                                      table_quadra_ctm,
                                                      table_trecho,
                                                      table_lote_ctm,
                                                      table_edificacao,
                                                      table_bairro_popular,
                                                      table_iptu_ctm_geo));

                session.setAttribute("zip_path", "output/"+
                        session.getId() + "/" +
                        ((String)session.getAttribute("quadraIptu")).replace(' ', '_') + ".zip");

                Zipando.zipa(outputpath +
                        session.getId() + "/" +
                        ((String)session.getAttribute("quadraIptu")).replace(' ', '_') + ".zip",
                        outputpath +
                        session.getId() + "/");

            // </editor-fold>
            }else
            // <editor-fold defaultstate="collapsed" desc="Autorizar Usuário">
            if (request.getParameter("function").compareTo("autorizar") == 0)
            {
                //redirecionar josso
                //getServletConfig().getServletContext().getRequestDispatcher("/josso_login/").forward(request, response);
                //getServletConfig().getServletContext().getRequestDispatcher("/login-redirect.jsp").forward(request, response);

               
//                String user_name = ((Assertion) session.getAttribute("_const_cas_assertion_")).getPrincipal().getName();
//
//                String aplic_geo = getServletConfig().getServletContext().getInitParameter("table_aplic_geo");
//                String usuarios_geo = getServletConfig().getServletContext().getInitParameter("table_usuarios_geo");
//                String usuarios_aplic_geo = getServletConfig().getServletContext().getInitParameter("table_usuarios_aplic_geo");
//
//                if(dao.ServicoDao.autorizar(pc, user_name, "Croqui Planimétrico", aplic_geo, usuarios_geo, usuarios_aplic_geo))
//                    session.setAttribute("autorizado", "autorizado");
//                else
//                {
//                    session.invalidate();
//                    getServletConfig().getServletContext().getRequestDispatcher("/naoAutorizado.html").forward(request, response);
//                    return;
//                }
                //getServletConfig().getServletContext().getRequestDispatcher("/lotes.jsp").forward(request, response);
                session.setAttribute("autorizado", "autorizado");

            }// </editor-fold>
        }

        getServletConfig().getServletContext().getRequestDispatcher("/lotes.jsp").forward(request, response);


    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Clique no sinal de + à esquerda para editar o código.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}