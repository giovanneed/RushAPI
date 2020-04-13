<?php

$codEstabelecimento= $_GET["codigo"]; 

//conexão com o servidor
$conect = mysql_connect("dbmy0042.whservidor.com", "infolove_1", "ged182");

// Caso a conexão seja reprovada, exibe na tela uma mensagem de erro
if (!$conect) die ("<h1>Falha na coneco com o Banco de Dados!</h1>");

// Caso a conexão seja aprovada, então conecta o Banco de Dados.	
$db = mysql_select_db("infolove_1");

echo "Loading...";
echo "<br />";
$query2 = mysql_query( "SELECT * FROM `TabEstabelecimento2` WHERE `CodEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());

//$query3 = mysql_query( "SELECT * FROM `TabEvento` WHERE `codEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());
$query = mysql_query( "SELECT * FROM `TabEvento` WHERE `codEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());


?>
<script type="text/javascript">


function testealert(){
  alert("cade?");
}


</script>


<html>
    <head>
		 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title><?while($array = mysql_fetch_array($query2)){
						echo $array['Nome'];
						}?>
		  </title>
		 
		 <link rel="stylesheet"  href="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.css" />
		 <link rel="stylesheet" href="docs/_assets/css/jqm-docs.css" />

		 <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.min.js"></script>
         <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.js"></script>
    
         <script type="text/javascript" src="docs/_assets/js/jqm-docs.js"></script>
		 
	</head>	 
	
</html>



	 <body onLoad="">
	 <div data-role="page" id="ExibirEventos">
		<div data-role="header">
    	       <a href="index.php"  data-icon="back"  data-transition="slide">Back</a>
               <h1>Exibir Eventos</h1>
         </div>
		  
		<div data-role="content">
		<table align="center">
		<tr>
		   <td>
				<h1>Nome Estabelecimento </h1>
		   </td>
		 </tr>
		</table>
		
		<table align="center" border="0" background="#FFFFFF" width="150px" >
		<? while($array = mysql_fetch_array($query)){?>
		 <tr width="150">
		     <td  background="#FFFFFF">
				<br>
				<br>
				<div background="#FFFFFF">
				<div align="center"><b>Descricao:</b> <? echo $array['descricao'];?></div>				
				<br>
				Data: <? echo $array['data'];?>
				<br>	
				Hora: <? echo  $array['hora'];?>
				</div>
				<br>
				<br>
				<div align="center"><div id="fb-root"></div><script src="http://connect.facebook.net/en_US/all.js#xfbml=1"></script><fb:send href="example.com" font="">Convide Amigos:</fb:send></div>
				
				<table border="0">
				<tr>
				  <td>
					<a href="http://www.facebook.com/sharer.php?u=http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>&t=<?php echo $array['descricao'];?>" target="_blank" rel="nofollow"><img src="facebook-icon.png"  width="45" height="50"></a>
				  </td>
				  <td>
				  <iframe src="http://www.facebook.com/plugins/like.php?href=YOUR_URL"
							scrolling="no" frameborder="0"
							style="border:none; width:450px; height:80px"><iframe>
							<iframe src="http://www.facebook.com/plugins/recommendations.php?site=infolove.com.br&amp;width=300&amp;height=300&amp;header=true&amp;colorscheme=light&amp;font&amp;border_color" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:300px; height:300px;" allowTransparency="true"></iframe>
				  </td>
				  <td>
						
						
						
				  </td>
				  <td>
				  <a href="http://twitter.com/share" class="twitter-share-button" data-url="infolove.com.br" data-text="Eu olhei o evento TESTE na #infoEventos http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>" data-count="vertical" data-via="infolovecombr">Tweet</a><script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>	
				  </td>
				
				  
				  
				  
				</tr>
				</table>
								
				<a href="http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>" > + mais info </a>
				
				<br>
			</td>
		 </tr>
		 <tr>
		   <td><hr WIDTH=100%></td>
		 </tr>
		 <?}?>
		
		 </table>
		  
		</div>
			 
		  
		
		<div data-role="footer">

              <div data-role="navbar">
                   <ul>

			<li>
                            <a href="listar01.html" id="chat" data-icon="grid" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" data-transition="pop">
                             <span class="ui-btn-text">LISTAR</span>
                            </a>
                        </li>
                        <li>
                            <a href="criar.html" id="chat" data-icon="plus" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a"  data-transition="pop">
                             <span class="ui-btn-text">CRIAR</span>

                            </a>
                        </li>
                        <li>
                            <a href="#" id="chat" data-icon="home" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a">
                             <span class="ui-btn-text">MEUS EVE</span>
                            </a>
                        </li>
		   </ul>

              </div>

          </div>
		
	 </div>
		 