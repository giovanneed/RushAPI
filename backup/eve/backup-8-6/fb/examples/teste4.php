<?php 

   $app_id ="107388209349965";
   $app_secret = "9865cded1e8a036c4e59839314d7e99a";
   $my_url = "http://www.infolove.com.br/mysql/fb/examples/";

   $session_start();
   $code = $_REQUEST["code"];

   if(empty($code)) {
     $_SESSION['state'] = md5(uniqid(rand(), TRUE)); //CSRF protection
     $dialog_url = "http://www.facebook.com/dialog/oauth?client_id=" 
       . $app_id . "&redirect_uri=" . urlencode($my_url) . "&state"
       . $_SESSION['state'];

     echo("<script> top.location.href='" . $dialog_url . "'</script>");
   }

   if($_REQUEST['state']== $_SESSION['state']) {
     $token_url = "https://graph.facebook.com/oauth/access_token?"
       . "client_id=" . $app_id . "&redirect_uri=" . urlencode($my_url)
       . "&client_secret=" . $app_secret . "&code=" . $code;

     $response = file_get_contents($token_url);
     $params = null;
     parse_str($response, $params);

     $graph_url = "https://graph.facebook.com/me?access_token=" 
       . $params[‘access_token’];

     $user = json_decode(file_get_contents($graph_url));
     echo("Hello " . $user->name);
   }
   else {
     echo("The state does not match. You may be a victim of CSRF.");
   }

 ?>