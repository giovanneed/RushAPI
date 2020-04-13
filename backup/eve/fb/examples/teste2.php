<?php
require '../src/facebook.php';
//
define('YOUR_APP_ID', '107388209349965');
define('YOUR_APP_SECRET', '9865cded1e8a036c4e59839314d7e99a');

function get_facebook_cookie($app_id, $app_secret) {

  echo "facebook";	

  $args = array();
  parse_str(trim($_COOKIE['fbs_' . $app_id], '\\"'), $args);
  ksort($args);
  $payload = '';
  foreach ($args as $key => $value) {
    if ($key != 'sig') {
      $payload .= $key . '=' . $value;
    }
  }
  if (md5($payload . $app_secret) != $args['sig']) {
    return null;
  }
  return $args;
}

$cookie = get_facebook_cookie(YOUR_APP_ID, YOUR_APP_SECRET);
echo "<br>";
echo $cookie['access_token'];
echo "teste <br>";

 //$graph_url = "https://graph.facebook.com/me?access_token=" 
   //    . $cookie['access_token'];

     //$user = json_decode(file_get_contents($graph_url));
     //echo("Hello " . $user->name);





//$user = json_decode(file_get_contents('https://graph.facebook.com/me?access_token='.$cookie['access_token']));
 
 /*
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

*/
?>
<html>
  <body>
    <?php if ($cookie) { ?>
      Welcome <?= $user->name ?>
    <?php } else { ?>
      <fb:login-button></fb:login-button>
    <?php } ?>
    <div id="fb-root"></div>
    <script src="http://connect.facebook.net/en_US/all.js"></script>
    <script>
      FB.init({appId: '<?= YOUR_APP_ID ?>', status: true,
               cookie: true, xfbml: true});
      FB.Event.subscribe('auth.login', function(response) {
        window.location.reload();
      });
    </script>
  </body>
</html>

