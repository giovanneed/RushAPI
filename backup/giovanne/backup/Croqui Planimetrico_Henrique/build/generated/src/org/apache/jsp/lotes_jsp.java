package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import entidades.Resposta;

public final class lotes_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.apache.jasper.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n");
      out.write("        <meta HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, no-store,must-revalidate, max-age=-1\" />\n");
      out.write("        <meta HTTP-EQUIV=\"Pragma\" CONTENT=\"no-store,no-cache\" />\n");
      out.write("        <meta HTTP-EQUIV=\"Expires\" CONTENT=\"-1\" />\n");
      out.write("        <title>Croqui Digital</title>\n");
      out.write("        <link REL=\"SHORTCUT ICON\" HREF=\"images/geoweb.ico\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/estilos-croqui-planimetrico.css\" />\n");
      out.write("        <script src=\"js/sorttable.js\"></script>\n");
      out.write("        <script src=\"js/checktable.js\"></script>\n");
      out.write("        <script src=\"js/util.js\"></script>\n");
      out.write("    </head>\n");
      out.write("    <body style=\"border: none;\">\n");
      out.write("\n");
      out.write("        <center>\n");
      out.write("            <div style=\"margin-bottom:-4px\">\n");
      out.write("                <a href=\"http://genio.pbh\" style=\"border:0px;\">\n");
      out.write("                    <img src=\"http://genio.pbh/geotecnologia/images/logo-topo-geo.jpg\"  style=\"border:0px;\" alt=\"Geoprocessamento\" width=\"980\" height=\"120\" hspace=\"0\" vspace=\"0\" />\n");
      out.write("                </a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<TABLE BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\">\n");
      out.write("\t\t\t\t<TR>\n");
      out.write("\t\t\t\t\t<TD WIDTH=\"980\" HEIGHT=\"34\" BACKGROUND=\"http://genio.pbh/geotecnologia/pro_drop_1/six_0.jpg\" VALIGN=\"center\">\n");
      out.write("\t\t\t\t\t\t<strong style=\"color:white; padding-left:15px;\">Croqui Planimétrico</strong>\n");
      out.write("\t\t\t\t\t</TD>\n");
      out.write("\t\t\t\t</TR>\n");
      out.write("\t\t\t</TABLE>\n");
      out.write("            <table width=\"975\" border=\"0\" bgcolor=\"#FFFFFF\"><tr><td> <div align=\"right\"><a href=\"");
      out.print(request.getContextPath());
      out.write("/josso_logout/\" class=\"button\">Logout</a></div></td></tr></table>\n");
      out.write("           \n");
      out.write("            <!-- Buscar Lotes -->\n");
      out.write("            <table width=\"975\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#dfdfdf\" bgcolor=\"#FFFFFF\">\n");
      out.write("                <tr>\n");
      out.write("                    <td style=\"vertical-align:middle\" align=\"left\" width=\"220\">\n");
      out.write("                        <table width=\"220\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\">\n");
      out.write("                            <form name=\"buscaLotes\" method=\"post\" action=\"ServletControle\">\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td class=\"default\">\n");
      out.write("                                        Entre com a quadra ou o lote:<br>\n");
      out.write("                                        <input type=\"text\" name=\"codigo\" value=\"");
      out.print(session.getAttribute("codigo"));
      out.write("\">\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td class=\"default\">\n");
      out.write("                                        <input type=\"radio\" name=\"tipo\" value=\"iptu\" ");
if (((String) session.getAttribute("radio")).compareTo("iptu") == 0) {
      out.write("checked=\"true\"");
}
      out.write("> IPTU\n");
      out.write("                                        <input type=\"radio\" name=\"tipo\" value=\"ctm\" ");
if (((String) session.getAttribute("radio")).compareTo("ctm") == 0) {
      out.write("checked=\"true\"");
}
      out.write("> CTM<BR />\n");
      out.write("                                        <input type=\"hidden\" name=\"function\" value=\"blotes\">\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td align=\"left\">\n");
      out.write("                                        <img class=\"botao\" src=\"images/lupa.jpg\" width=\"128\" height=\"31\" hspace=\"10\" vspace=\"0\" onClick=\"buscarLotes()\">\n");
      out.write("                                        <!--<input type=\"button\" onClick=\"buscarLotes()\" value=\"Buscar Lotes\">-->\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                            </form>\n");
      out.write("                        </table>\n");
      out.write("                    </td>\n");
      out.write("                    <!-- /Buscar Lotes -->\n");
      out.write("                <!-- Listar Lotes e Gerar Croqui -->\n");
      out.write("                    <td id=\"td_tabela\" class=\"tabela\" align=\"center\">\n");
      out.write("                        ");

        ArrayList<Resposta> lotes = (ArrayList<Resposta>) session.getAttribute("lotes");

        String[] images = (String[]) session.getAttribute("images");

        if (lotes != null) {
            if (lotes.size() == 0) {
      out.write("\n");
      out.write("                        <br> Não existe lotes para este código\n");
      out.write("                        ");
} else {
                        
      out.write("\n");
      out.write("                        <div class=\"tabela\">\n");
      out.write("                            <table id=\"lotes\" width=\"717px\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"sortable\">\n");
      out.write("                                <tr height=\"19px\">\n");
      out.write("                                    <td align=\"center\" width=\"5%\" style=\"vertical-align:middle\"></td>\n");
      out.write("                                    <td align=\"center\" width=\"21%\" class=\"temaazul1\">Índice Cadastral</td>\n");
      out.write("                                    <td align=\"center\" width=\"19%\" class=\"temaazul1\">Código CTM</td>\n");
      out.write("\t\t\t\t\t\t\t\t\t<td align=\"center\" width=\"20%\" class=\"temaazul1\">Área Construída</td>\n");
      out.write("\t\t\t\t\t\t\t\t\t<td align=\"center\" width=\"20%\" class=\"temaazul1\">Área Calculada</td>\n");
      out.write("\t\t\t\t\t\t\t\t\t<td align=\"center\" width=\"15%\" class=\"temaazul1\">Percentual</td>\n");
      out.write("                                </tr>\n");
      out.write("                                ");

                                for (int i = 0; i < lotes.size(); i++) {
                                    Resposta lote = (Resposta) lotes.get(i);
                                
      out.write("\n");
      out.write("                                <tr  onclick=\"clickage(event, this)\">\n");
      out.write("                                    <td width=\"5%\" align=\"center\"><input type=\"checkbox\"/></td>\n");
      out.write("                                    <td width=\"21%\" align=\"center\">");
      out.print(lote.getIndiceCadastral());
      out.write("</td>\n");
      out.write("                                    <td width=\"19%\" align=\"center\">");
      out.print(lote.getLotectm());
      out.write("</td>\n");
      out.write("                                    <td width=\"20%\" align=\"center\">");
      out.print(lote.getAreaConstruida());
      out.write("</td>\n");
      out.write("                                    <td width=\"20%\" align=\"center\">");
      out.print(lote.getAreaTerreno());
      out.write("</td>\n");
      out.write("                                    <td width=\"15%\" align=\"center\">");
      out.print(lote.getPercentual());
      out.write("</td>\n");
      out.write("                                </tr>\n");
      out.write("                                ");

                                }
                                
      out.write("\n");
      out.write("                            </table>\n");
      out.write("                        </div>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td height=\"198\">\n");
      out.write("                        <table width=\"220\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\">\n");
      out.write("                            <form name=\"enviaLotes\" method=\"post\" action=\"ServletControle\">\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td class=\"default\" colspan=\"2\">\n");
      out.write("                                        Selecione o formato da página:\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td class=\"default\">\n");
      out.write("                                        Quadra:\n");
      out.write("                                    </td>\n");
      out.write("                                    <td>\n");
      out.write("                                        <select name=\"folhaQuadra\">\n");
      out.write("                                            <option value=\"1\" ");
if (session.getAttribute("folhaQuadra").equals("1")) {
      out.write("selected=\"true\"");
}
      out.write(">A4</option>\n");
      out.write("                                            <option value=\"2\" ");
if (session.getAttribute("folhaQuadra").equals("2")) {
      out.write("selected=\"true\"");
}
      out.write(">A3</option>\n");
      out.write("                                            <option value=\"3\" ");
if (session.getAttribute("folhaQuadra").equals("3")) {
      out.write("selected=\"true\"");
}
      out.write(">A2</option>\n");
      out.write("                                            <option value=\"4\" ");
if (session.getAttribute("folhaQuadra").equals("4")) {
      out.write("selected=\"true\"");
}
      out.write(">Letter</option>\n");
      out.write("                                        </select>\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td class=\"default\">\n");
      out.write("                                        Lote:\n");
      out.write("                                    </td>\n");
      out.write("                                    <td>\n");
      out.write("                                        <select name=\"folhaLote\">\n");
      out.write("                                            <option value=\"1\" ");
if (session.getAttribute("folhaLote").equals("1")) {
      out.write("selected=\"true\"");
}
      out.write(">A4</option>\n");
      out.write("                                            <option value=\"2\" ");
if (session.getAttribute("folhaLote").equals("2")) {
      out.write("selected=\"true\"");
}
      out.write(">A3</option>\n");
      out.write("                                            <option value=\"3\" ");
if (session.getAttribute("folhaLote").equals("3")) {
      out.write("selected=\"true\"");
}
      out.write(">A2</option>\n");
      out.write("                                            <option value=\"4\" ");
if (session.getAttribute("folhaLote").equals("4")) {
      out.write("selected=\"true\"");
}
      out.write(">Letter</option>\n");
      out.write("                                        </select><BR>\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                                <tr class=\"default\">\n");
      out.write("                                    <td class=\"default\" colspan=\"2\">\n");
      out.write("                                        <input type=\"hidden\" name=\"function\" value=\"croqui\">\n");
      out.write("                                        <input type=\"hidden\" name=\"lotes\">\n");
      out.write("                                        <input type=\"hidden\" name=\"quadra\">\n");
      out.write("                                        <input type=\"hidden\" name=\"lotesIptu\">\n");
      out.write("                                        <input type=\"hidden\" name=\"quadraIptu\">\n");
      out.write("                                        <img class=\"botao\" src=\"images/gerar-croqui.jpg\" width=\"128\" height=\"31\" hspace=\"0\" vspace=\"0\" onClick=\"enviarLotes()\">\n");
      out.write("                                        <!--<input type=\"button\" onClick=\"enviarLotes()\" value=\"Gerar Croquis\">-->\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                            </form>\n");
      out.write("                        </table>\n");
      out.write("                    </td>\n");
      out.write("                    ");

                    if (images == null)
                    {
      out.write("\n");
      out.write("                    <td id=\"td_croqui\" align=\"center\">\n");
      out.write("                    </td>\n");
      out.write("                    ");

                    }
            }
        }
      out.write("\n");
      out.write("                    <!-- /Listar Lotes e Gerar Croqui -->\n");
      out.write("                <!-- Exibir Croquis e Salvar ZIP -->\n");
      out.write("                        ");

        String zip_Path = (String) session.getAttribute("zip_path");
        if ((images != null) && (zip_Path != null)) {
            String[] listaLotes = (String[]) session.getAttribute("listaLotes");
            for (int i = 0; i < listaLotes.length; i++) {
                    
      out.write("<SCRIPT>checkLote(\"");
      out.print(listaLotes[i]);
      out.write("\");</SCRIPT>");

            }
      out.write("\n");
      out.write("                    <td id=\"td_croqui\" rowspan=\"2\" align=\"center\">\n");
      out.write("                        <div class=\"image\" style=\"vertical-align:middle;\">\n");
      out.write("                            <img border=\"1px\" name=\"croqui\" src=");
      out.print(images[0] + "?" + System.currentTimeMillis());
      out.write(" />\n");
      out.write("                        </div>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <SCRIPT>\n");
      out.write("                    var images;\n");
      out.write("                    var count = 0;\n");
      out.write("                    var images_size;\n");
      out.write("                    function init()\n");
      out.write("                    {\n");
      out.write("                        images = new Array();\n");
      out.write("                        images_size = ");
      out.print(Integer.toString(images.length));
      out.write(";\n");
      out.write("    ");

            for (int i = 0; i < images.length; i++) {
                
      out.write("images[");
      out.print(Integer.toString(i));
      out.write("] = \"");
      out.print(images[i]);
      out.write('"');
      out.write(';');

            }
      out.write("\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                init();\n");
      out.write("\n");
      out.write("                function proxima_imagem(){\n");
      out.write("                    count ++;\n");
      out.write("                    document[\"botao_anterior\"].style.display = \"block\";\n");
      out.write("                    if (count == images_size - 1)\n");
      out.write("                    {\n");
      out.write("                        document[\"botao_proximo\"].style.display = \"none\";\n");
      out.write("                    }\n");
      out.write("                    d = new Date();\n");
      out.write("                    document[\"croqui\"].src = images[count] + \"?\" + d.getMilliseconds();\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                function anterior_imagem(){\n");
      out.write("                    count --;\n");
      out.write("                    document[\"botao_proximo\"].style.display = \"block\";\n");
      out.write("                    if (count == 0)\n");
      out.write("                    {\n");
      out.write("                        document[\"botao_anterior\"].style.display = \"none\";\n");
      out.write("                    }\n");
      out.write("                    d = new Date();\n");
      out.write("                    document[\"croqui\"].src = images[count] + \"?\" + d.getMilliseconds();\n");
      out.write("                }\n");
      out.write("                </SCRIPT>\n");
      out.write("                <tr height=\"198\">\n");
      out.write("                    <td align=\"left\">\n");
      out.write("                        <table cellpadding=\"4\" cellspacing=\"0\">\n");
      out.write("                            <tr class=\"default\">\n");
      out.write("                                <td class=\"default\" colspan=\"2\">\n");
      out.write("                                    Navegar entre imagens:\n");
      out.write("                                </td>\n");
      out.write("                            </tr>\n");
      out.write("                            <tr class=\"default\">\n");
      out.write("                                <td width=\"50%\" class=\"default\">\n");
      out.write("                                    <img name=\"botao_anterior\" class=\"botao\" src=\"images/seta-anterior.jpg\" width=\"78\" height=\"28\" ONCLICK=\"anterior_imagem()\" style=\"display:none;\">\n");
      out.write("                                    <!--<button type=\"button\" ONCLICK=\"anterior_imagem()\">Anterior</button>-->\n");
      out.write("                                </td>\n");
      out.write("                                <td width=\"50%\" class=\"default\">\n");
      out.write("                                   <img name=\"botao_proximo\" class=\"botao\" src=\"images/seta-proxima.jpg\" width=\"78\" height=\"28\" ONCLICK=\"proxima_imagem()\">\n");
      out.write("                                    <!--<button type=\"button\" ONCLICK=\"proxima_imagem()\">Próximo</button>-->\n");
      out.write("                                </td>\n");
      out.write("                            </tr>\n");
      out.write("                            <tr class=\"default\">\n");
      out.write("                                <td class=\"default\" colspan=\"2\">\n");
      out.write("                                    <a href=\"");
      out.print(zip_Path + "?" + System.currentTimeMillis());
      out.write("\">\n");
      out.write("                                        <img class=\"botao\" src=\"images/grava-croqui.jpg\" width=\"128\" height=\"31\" hspace=\"0\" border=\"0px\" vspace=\"10\" />\n");
      out.write("                                    </a>\n");
      out.write("                                </td>\n");
      out.write("                            </tr>\n");
      out.write("                        </table>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
}
      out.write("\n");
      out.write("                <!-- /Exibir Croquis e Salvar ZIP -->\n");
      out.write("            </table>\n");
      out.write("        </center>\n");
      out.write("       \n");
      out.write("        <img src=\"images/carregando.gif\" style=\"display: none\">\n");
      out.write("    </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
