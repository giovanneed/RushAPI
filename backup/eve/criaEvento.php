<?php

$codEstab= $_GET["codigo"]; 



//conexão com o servidor
$conect = mysql_connect("dbmy0042.whservidor.com", "infolove_1", "ged182");

// Caso a conexão seja reprovada, exibe na tela uma mensagem de erro
if (!$conect) die ("<h1>Falha na coneco com o Banco de Dados!</h1>");

// Caso a conexão seja aprovada, então conecta o Banco de Dados.	
$db = mysql_select_db("infolove_1");
$query2 = mysql_query( "SELECT * FROM `TabEstabelecimento2` WHERE `CodEstabelecimento` = '".$codEstab."';" ) or die (mysql_error());

while($array = mysql_fetch_array($query2)){
						$nomeestab =  $array['Nome'];
						
}
echo "Loading...";

						

?>
<script type="text/javascript">

	
			function enviaForm(){
				
				document.criareventos.submit() ;
			 
			
			}
</script>
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
		<h1>Cria Evento</h1>
	   <form name="criareventos" method="GET" action="criar.php">
		<table border="0">
		  <tr>
		    <td>Local: <input type="text" size="15" name="estab" id="estab" value="<?echo $nomeestab?>"></td>
		  <tr>
		    <td>
			   Data: <input type="text" size="8" name="data" id="data" value="  /  /  ">
			 </td> 
	       </tr> 
		 <tr>
		    <td>
			   Hora: <input type="text" size="5" name="hora" id="hora" value="  :  ">
			 </td> 
	       </tr> <tr>
		    <td>
			   Descrição: <input type="text"  size="" name="desc" id="desc" value=" " style="height: 150px;">
			 </td> 
	       </tr> 
		   <tr>
		    <td> 
			    <input type="hidden" id="codigo" name="codigo" value="<?echo $codEstab?>">
			   <input type="button" value="criar eventos" OnClick=enviaForm()>
			</td>
		  </tr>	
		</table>	
 	 </div>
	 

 

</html>