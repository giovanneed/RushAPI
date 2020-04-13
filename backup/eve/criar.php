<?php

$codEstab= $_GET["codigo"]; 
$desc= $_GET["desc"]; 
$hora= $_GET["hora"]; 
$data= $_GET["data"];


$sql = "insert into TabEvento (`Imagem`,`confirmados`,`descricao`,`hora`,`data`,`codEstabelecimento`) values  (' ',' ','$desc','$hora','$data','$codEstab')";

//conexão com o servidor
$conect = mysql_connect("dbmy0042.whservidor.com", "infolove_1", "ged182");

// Caso a conexão seja reprovada, exibe na tela uma mensagem de erro
if (!$conect) die ("<h1>Falha na coneco com o Banco de Dados!</h1>");

// Caso a conexão seja aprovada, então conecta o Banco de Dados.	
$db = mysql_select_db("infolove_1");
$query2 = mysql_query( $sql) or die (mysql_error());

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
    	       <h1>Evento Criado</h1>
         </div>
		  
		<div data-role="content">	
		<a href="http://www.infolove.com.br/eve"><img src="home.png"></a>
		<br>
		<h1>Evento Criado com sucesso!</h1>
	  
	  
     	</div>
	 

 

</html>