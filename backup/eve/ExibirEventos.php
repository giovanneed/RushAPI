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

while($array = mysql_fetch_array($query2)){
						echo $array['Nome'];
						$estab = $array['Nome'];
						$site = $array['Site'];
						$foto = $array['Foto'];
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
	 <div data-role="page" id="ExibirEventos" >
		<div data-role="header" data-position="fixed">
    	       <h1>Exibir Eventos</h1>
         </div>
		  
		<div data-role="content"  >
		<table align="">
		<tr>
		   <td >
				<div><a href="http://www.infolove.com.br/eve"><img src="home.png"></a><h2><?echo $estab?></h2></div>
				<p><a href="http://<?echo $site?>" onClick=location.href='http://<?echo $site?>'><?echo $site?></a>
			
				
		   </td>
		 </tr>
		</table>
		
		<table align="" border="0" width="80%" >
		<? while($array = mysql_fetch_array($query)){
		   $cont=0;	
		?>
		 <tr background="bg.jpg">
			
		     <td width="70%" valign="top" >
			 <small>
				<br>
				<br>
				<div>
				<div align="left"><b>Descricao:</b> <? echo $array['descricao'];?></div>				
				<br>
				Data: <? echo $array['data'];?>
				<br>	
				Hora: <? echo  $array['hora'];?>
				</div>
				<br>
				<br>
				<div align="center"><div id="fb-root"></div><script src="http://connect.facebook.net/en_US/all.js#xfbml=1"></script><fb:send href="http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>" font="">Convide Amigos:</fb:send></div>
				<br></br>
				<a href="http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>" ><small> + mais info </small></a>
				</td>
				<td width="10%">
				<table border="0">
				  <tr>
				  <td valign="top" align="center">
					<a href="http://www.facebook.com/sharer.php?u=http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>&t=<?php echo $array['descricao'];?>" target="_blank" rel="nofollow"><img src="facebook-icon.png"  width="45" height="50"></a>
				  </td>
				  </tr>
				  <tr>
				  <td align="center" valign="top">
				  <a href="http://twitter.com/share" class="twitter-share-button" data-url="infolove.com.br" data-text="Estou divulgando o evento da <?echo $estab?> na #infoEventos http://infolove.com.br/eve/evento.php?codigo=<?php echo $array['codEvento'];?>" data-count="vertical" data-via="infolovecombr">Tweet</a><script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>	
				  </td>
				 
				  </tr>
				 </table> 
			   </td>
			  </small>
		 </tr>
		 <tr>
		 
		   <td><hr WIDTH=100%></td>
		 </tr>
		 <?}?>
		
		 </table>
		  
		</div>
			 
		  
		
		
		
	 </div>
</html>
		 