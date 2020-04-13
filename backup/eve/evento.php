<?php

$codEvento= $_GET["codigo"]; 



//conexão com o servidor
$conect = mysql_connect("dbmy0042.whservidor.com", "infolove_1", "ged182");

// Caso a conexão seja reprovada, exibe na tela uma mensagem de erro
if (!$conect) die ("<h1>Falha na coneco com o Banco de Dados!</h1>");

// Caso a conexão seja aprovada, então conecta o Banco de Dados.	
$db = mysql_select_db("infolove_1");

echo "Loading...";

//$query2 = mysql_query( "SELECT * FROM `TabEstabelecimento2` WHERE `CodEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());
//$query4 = mysql_query( "SELECT * FROM `TabEstabelecimento2` WHERE `CodEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());
//$query3 = mysql_query( "SELECT * FROM `TabEvento` WHERE `codEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());
$query = mysql_query( "SELECT * FROM `TabEvento` WHERE `codEvento` = '".$codEvento."';" ) or die (mysql_error());

while($array = mysql_fetch_array($query)){
						$desc =  $array['descricao'];
						$data =  $array['data'];
						$hora =  $array['hora'];
						$img =   $array['Imagem'];
						}
						

?>
<html>
    <head>
		 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>
		  </title>
		 
		 <link rel="stylesheet"  href="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.css" />
		 <link rel="stylesheet" href="docs/_assets/css/jqm-docs.css" />

		 <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.min.js"></script>
         <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.js"></script>
    
         <script type="text/javascript" src="docs/_assets/js/jqm-docs.js"></script>
		 
	</head>	 

<body onLoad="">
	 <div data-role="page" id="Eventos" >
		<div data-role="header" data-position="fixed">
    	       <h1>Exibir Eventos</h1>
         </div>
		  
		<div data-role="content">	
		<a href="http://www.infolove.com.br/eve"><img src="home.png"></a>
		<br>
		<table>
			<tr><td><b>Data:</b> <?echo $data?>  <b>Hora:</b> <?echo $hora?> <br><br></td></tr>
			<tr background="bg.jpg">
				<td>
					
					<small>
						<?echo $desc?>
					</small>
				</td>
			</tr> 
			<tr>
			   <td><br><br><div align="center"><div id="fb-root"></div><script src="http://connect.facebook.net/en_US/all.js#xfbml=1"></script><fb:send href="http://infolove.com.br/eve/evento.php?codigo=<?php echo $codEvento;?>" font="">Convide Amigos:</fb:send></div></td>
			</tr>
			<tr>
			 <td>
			 <a href="http://www.facebook.com/sharer.php?u=http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>&t=<?php echo $array['descricao'];?>" target="_blank" rel="nofollow"><img src="facebook-icon.png"  width="45" height="50"></a>
			 <a href="http://twitter.com/share" class="twitter-share-button" data-url="infolove.com.br" data-text="Estou divulgando o evento da <?echo $estab?> na #infoEventos http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>" data-count="vertical" data-via="infolovecombr">Tweet</a><script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>	
			 </td>
			 
			</tr> 
		</table>
		<br>
		<img src="logo.png">
 
 	 </div>

 
 

</html>