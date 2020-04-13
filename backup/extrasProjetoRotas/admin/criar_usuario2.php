<?php
require("class.login.php");

if(!$_POST['ok'])
{
?>

<form method=post name=formulario>
<table>
 <tr>
  <td><b>Senha Mestre:</b></td>
  <td><input type=password name=master></td>
 </tr> 
 <tr>
  <td><b>Nome:</b></td>
  <td><input type=text name=login></td>
 </tr>
 <tr>
  <td><b>Senha:</b></td>
  <td><input type=password name=senha></td>
 </tr>
 <tr>
  <td><b>Nivel:</b></td>
  <td><input type=text name=nivel></td>
 </tr>
</table>
<center><input type=submit name=ok value=Incluir></center>
</form>

<?php
if ($_GET['falha']==1)
{
 echo '<font color=red>Senha Mestre Inv&aacute;lida!</font>';
}
}
else
{
 $log = new logmein();
 if ($_POST['master'] == 'prodabel')
 {
  $log->register("usuarios",$_POST['login'],$_POST['senha'],$_POST['nivel']);
  echo "Usu&aacute;rio Cadastrado com sucesso - <a href='index.php'>Tela Login</a>";
 }
 else
 {
  header("Location: criar_usuario.php?falha=1");  
 }
}
?>
