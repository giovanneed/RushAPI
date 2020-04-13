<?php
require("class.login.php");

$log = new logmein();
$log->encrypt = true; //set encryption
//parameters are(SESSION, name of the table, name of the password field, name of the username field)


if($log->logincheck($_SESSION['loggedin'], "usuarios", "senha", "login") && $_SESSION['userlevel'] == 1){
	header("Location: ../index.php");
	exit();
}


if($log->logincheck($_SESSION['loggedin'], "usuarios", "senha", "login") && $_SESSION['userlevel'] != 1){
	header("Location: listagem.php");
	exit();
}





echo "<BODY OnLoad='document.loginformname.username.focus();'><center>";
$log->loginform("loginformname", "loginformid", "logon.php");
switch ($_GET['m'])
{
 case 1:
  echo '<font color=red>Autentica&ccedil;&atilde;o Inv&aacute;lida</font>';
 break;
 
 case 2:
  echo '<font color=red>Para acesso a p&aacute;gina, &eacute; necess&aacute;ria a autentica&ccedil;&atilde;o no sistema.</font>';
 break; 
 
case 3:
  echo '<font color=red>Sess&atilde;o finalizada.</font>';
 break;  
}

echo "<BR><a href='criar_usuario.php'>Cadastrar Usu&aacute;rio</a></center></BODY>";
?>