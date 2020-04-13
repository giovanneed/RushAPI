<?php 
$ch = curl_init();
// informar URL e outras funções ao CURL
curl_setopt($ch, CURLOPT_URL, "http://www.google.nl/");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
// Acessar a URL e retornar a saída
$output = curl_exec($ch);
// liberar
curl_close($ch);
// Substituir 'Google' por 'PHP Curl'
$output = str_replace('Google', 'PHP Curl', $output);
// Imprimir a saída
echo $output;
?>