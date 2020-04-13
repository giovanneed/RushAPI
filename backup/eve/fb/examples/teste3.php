<?php
$data = file_get_contents("http://www.infolove.com.br/mysql/fb/examples/input.txt"); //read the file
$convert = explode("\n", $data); //create array separate by new line

for ($i=0;$i<count($convert);$i++)  
{
    echo $convert[$i].', '; //write value by index
}
?>