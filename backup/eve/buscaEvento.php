<?

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
while($array = mysql_fetch_array($query2)){
 echo $array['Nome'];
}
$query = mysql_query( "SELECT * FROM `TabEvento` WHERE `codEstabelecimento` = '".$codEstabelecimento."';" ) or die (mysql_error());


while($array = mysql_fetch_array($query)){
  
	echo "<br>-------------------<br>";
	echo $array['codEvento'];
	echo "<br>";
	echo $array['codEstabeleciemnto'];
	echo "<br>";
	echo $array['data'];
	echo "<br>";
	echo $array['hora'];
	echo "<br>";
	echo $array['descricao'];
	echo "<br>";
	echo $array['confirmados'];
	echo "<br>";
	
}



//$query = mysql_query("SELECT * FROM `TabEvento` WHERE `codEstabelecimento` = '".$codEstabelecimentox"'") or die(mysql_error());


?>