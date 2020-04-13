<%--
    Document   : lotes
    Created on : 16/02/2009, 12:18:22
    Author     : bruno.finelli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.ArrayList" %>
<%@ page import="entidades.Resposta" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, no-store,must-revalidate, max-age=-1" />
        <meta HTTP-EQUIV="Pragma" CONTENT="no-store,no-cache" />
        <meta HTTP-EQUIV="Expires" CONTENT="-1" />
        <title>Croqui Digital</title>
        <link REL="SHORTCUT ICON" HREF="images/geoweb.ico">
        <link rel="stylesheet" type="text/css" href="css/estilos-croqui-planimetrico.css" />
        <script src="js/sorttable.js"></script>
        <script src="js/checktable.js"></script>
        <script src="js/util.js"></script>
    </head>
    <body style="border: none;">

        <center>
            <div style="margin-bottom:-4px">
                <a href="http://genio.pbh" style="border:0px;">
                    <img src="http://genio.pbh/geotecnologia/images/logo-topo-geo.jpg"  style="border:0px;" alt="Geoprocessamento" width="980" height="120" hspace="0" vspace="0" />
                </a>
			</div>
			<TABLE BORDER="0" cellpadding="0" CELLSPACING="0">
				<TR>
					<TD WIDTH="980" HEIGHT="34" BACKGROUND="http://genio.pbh/geotecnologia/pro_drop_1/six_0.jpg" VALIGN="center">
						<strong style="color:white; padding-left:15px;">Croqui Planimétrico</strong>
					</TD>
				</TR>
			</TABLE>
            <table width="975" border="0" bgcolor="#FFFFFF"><tr><td> <div align="right"><a href="<%=request.getContextPath()%>/josso_logout/" class="button">Logout</a></div></td></tr></table>
           
            <!-- Buscar Lotes -->
            <table width="975" border="1" cellpadding="0" cellspacing="0" bordercolor="#dfdfdf" bgcolor="#FFFFFF">
                <tr>
                    <td style="vertical-align:middle" align="left" width="220">
                        <table width="220" border="0" cellpadding="4" cellspacing="0">
                            <form name="buscaLotes" method="post" action="ServletControle">
                                <tr class="default">
                                    <td class="default">
                                        Entre com a quadra ou o lote:<br>
                                        <input type="text" name="codigo" value="<%=session.getAttribute("codigo")%>">
                                    </td>
                                </tr>
                                <tr class="default">
                                    <td class="default">
                                        <input type="radio" name="tipo" value="iptu" <%if (((String) session.getAttribute("radio")).compareTo("iptu") == 0) {%>checked="true"<%}%>> IPTU
                                        <input type="radio" name="tipo" value="ctm" <%if (((String) session.getAttribute("radio")).compareTo("ctm") == 0) {%>checked="true"<%}%>> CTM<BR />
                                        <input type="hidden" name="function" value="blotes">
                                    </td>
                                </tr>
                                <tr class="default">
                                    <td align="left">
                                        <img class="botao" src="images/lupa.jpg" width="128" height="31" hspace="10" vspace="0" onClick="buscarLotes()">
                                        <!--<input type="button" onClick="buscarLotes()" value="Buscar Lotes">-->
                                    </td>
                                </tr>
                            </form>
                        </table>
                    </td>
                    <!-- /Buscar Lotes -->
                <!-- Listar Lotes e Gerar Croqui -->
                    <td id="td_tabela" class="tabela" align="center">
                        <%
        ArrayList<Resposta> lotes = (ArrayList<Resposta>) session.getAttribute("lotes");

        String[] images = (String[]) session.getAttribute("images");

        if (lotes != null) {
            if (lotes.size() == 0) {%>
                        <br> Não existe lotes para este código
                        <%} else {
                        %>
                        <div class="tabela">
                            <table id="lotes" width="717px" border="1" cellpadding="0" cellspacing="0" class="sortable">
                                <tr height="19px">
                                    <td align="center" width="5%" style="vertical-align:middle"></td>
                                    <td align="center" width="21%" class="temaazul1">Índice Cadastral</td>
                                    <td align="center" width="19%" class="temaazul1">Código CTM</td>
									<td align="center" width="20%" class="temaazul1">Área Construída</td>
									<td align="center" width="20%" class="temaazul1">Área Calculada</td>
									<td align="center" width="15%" class="temaazul1">Percentual</td>
                                </tr>
                                <%
                                for (int i = 0; i < lotes.size(); i++) {
                                    Resposta lote = (Resposta) lotes.get(i);
                                %>
                                <tr  onclick="clickage(event, this)">
                                    <td width="5%" align="center"><input type="checkbox"/></td>
                                    <td width="21%" align="center"><%=lote.getIndiceCadastral()%></td>
                                    <td width="19%" align="center"><%=lote.getLotectm()%></td>
                                    <td width="20%" align="center"><%=lote.getAreaConstruida()%></td>
                                    <td width="20%" align="center"><%=lote.getAreaTerreno()%></td>
                                    <td width="15%" align="center"><%=lote.getPercentual()%></td>
                                </tr>
                                <%
                                }
                                %>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td height="198">
                        <table width="220" border="0" cellpadding="4" cellspacing="0">
                            <form name="enviaLotes" method="post" action="ServletControle">
                                <tr class="default">
                                    <td class="default" colspan="2">
                                        Selecione o formato da página:
                                    </td>
                                </tr>
                                <tr class="default">
                                    <td class="default">
                                        Quadra:
                                    </td>
                                    <td>
                                        <select name="folhaQuadra">
                                            <option value="1" <%if (session.getAttribute("folhaQuadra").equals("1")) {%>selected="true"<%}%>>A4</option>
                                            <option value="2" <%if (session.getAttribute("folhaQuadra").equals("2")) {%>selected="true"<%}%>>A3</option>
                                            <option value="3" <%if (session.getAttribute("folhaQuadra").equals("3")) {%>selected="true"<%}%>>A2</option>
                                            <option value="4" <%if (session.getAttribute("folhaQuadra").equals("4")) {%>selected="true"<%}%>>Letter</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr class="default">
                                    <td class="default">
                                        Lote:
                                    </td>
                                    <td>
                                        <select name="folhaLote">
                                            <option value="1" <%if (session.getAttribute("folhaLote").equals("1")) {%>selected="true"<%}%>>A4</option>
                                            <option value="2" <%if (session.getAttribute("folhaLote").equals("2")) {%>selected="true"<%}%>>A3</option>
                                            <option value="3" <%if (session.getAttribute("folhaLote").equals("3")) {%>selected="true"<%}%>>A2</option>
                                            <option value="4" <%if (session.getAttribute("folhaLote").equals("4")) {%>selected="true"<%}%>>Letter</option>
                                        </select><BR>
                                    </td>
                                </tr>
                                <tr class="default">
                                    <td class="default" colspan="2">
                                        <input type="hidden" name="function" value="croqui">
                                        <input type="hidden" name="lotes">
                                        <input type="hidden" name="quadra">
                                        <input type="hidden" name="lotesIptu">
                                        <input type="hidden" name="quadraIptu">
                                        <img class="botao" src="images/gerar-croqui.jpg" width="128" height="31" hspace="0" vspace="0" onClick="enviarLotes()">
                                        <!--<input type="button" onClick="enviarLotes()" value="Gerar Croquis">-->
                                    </td>
                                </tr>
                            </form>
                        </table>
                    </td>
                    <%
                    if (images == null)
                    {%>
                    <td id="td_croqui" align="center">
                    </td>
                    <%
                    }
            }
        }%>
                    <!-- /Listar Lotes e Gerar Croqui -->
                <!-- Exibir Croquis e Salvar ZIP -->
                        <%
        String zip_Path = (String) session.getAttribute("zip_path");
        if ((images != null) && (zip_Path != null)) {
            String[] listaLotes = (String[]) session.getAttribute("listaLotes");
            for (int i = 0; i < listaLotes.length; i++) {
                    %><SCRIPT>checkLote("<%=listaLotes[i]%>");</SCRIPT><%
            }%>
                    <td id="td_croqui" rowspan="2" align="center">
                        <div class="image" style="vertical-align:middle;">
                            <img border="1px" name="croqui" src=<%=images[0] + "?" + System.currentTimeMillis()%> />
                        </div>
                    </td>
                </tr>
                <SCRIPT>
                    var images;
                    var count = 0;
                    var images_size;
                    function init()
                    {
                        images = new Array();
                        images_size = <%=Integer.toString(images.length)%>;
    <%
            for (int i = 0; i < images.length; i++) {
                %>images[<%=Integer.toString(i)%>] = "<%=images[i]%>";<%
            }%>
                }

                init();

                function proxima_imagem(){
                    count ++;
                    document["botao_anterior"].style.display = "block";
                    if (count == images_size - 1)
                    {
                        document["botao_proximo"].style.display = "none";
                    }
                    d = new Date();
                    document["croqui"].src = images[count] + "?" + d.getMilliseconds();
                }

                function anterior_imagem(){
                    count --;
                    document["botao_proximo"].style.display = "block";
                    if (count == 0)
                    {
                        document["botao_anterior"].style.display = "none";
                    }
                    d = new Date();
                    document["croqui"].src = images[count] + "?" + d.getMilliseconds();
                }
                </SCRIPT>
                <tr height="198">
                    <td align="left">
                        <table cellpadding="4" cellspacing="0">
                            <tr class="default">
                                <td class="default" colspan="2">
                                    Navegar entre imagens:
                                </td>
                            </tr>
                            <tr class="default">
                                <td width="50%" class="default">
                                    <img name="botao_anterior" class="botao" src="images/seta-anterior.jpg" width="78" height="28" ONCLICK="anterior_imagem()" style="display:none;">
                                    <!--<button type="button" ONCLICK="anterior_imagem()">Anterior</button>-->
                                </td>
                                <td width="50%" class="default">
                                   <img name="botao_proximo" class="botao" src="images/seta-proxima.jpg" width="78" height="28" ONCLICK="proxima_imagem()">
                                    <!--<button type="button" ONCLICK="proxima_imagem()">Próximo</button>-->
                                </td>
                            </tr>
                            <tr class="default">
                                <td class="default" colspan="2">
                                    <a href="<%=zip_Path + "?" + System.currentTimeMillis()%>">
                                        <img class="botao" src="images/grava-croqui.jpg" width="128" height="31" hspace="0" border="0px" vspace="10" />
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%}%>
                <!-- /Exibir Croquis e Salvar ZIP -->
            </table>
        </center>
       
        <img src="images/carregando.gif" style="display: none">
    </body>
</html>