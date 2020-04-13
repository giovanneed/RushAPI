<?php
echo "conecta facebook api";

require '../src/facebook.php';

$facebook = new Facebook(array(
  'appId'  => '107388209349965',
  'secret' => '9865cded1e8a036c4e59839314d7e99a',
  'cookie' => true,
));
$session = $facebook->getSession();

echo  $session ;


$me = null;
// Session based API call.

if ($session) {
  try {
    $uid = $facebook->getUser();
    $me = $facebook->api('/me');
  } catch (FacebookApiException $e) {
    error_log($e);
  }
}

// login or logout url will be needed depending on current user state.
if ($me) {
  $logoutUrl = $facebook->getLogoutUrl();
} else {
  $loginUrl = $facebook->getLoginUrl();
}

// This call will always work since we are fetching public data.
$naitik = $facebook->api('/naitik');


echo "<br /> fim";


?>