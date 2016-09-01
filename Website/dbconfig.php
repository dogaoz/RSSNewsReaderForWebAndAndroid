<?php
define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'dogaozka_bitpopW');    // DB username
// TO-DO : db password will be changed after project launch
define('DB_PASSWORD', '#Do2981@wq.q!');    // DB password
// TO-DO : db password will be changed after project launch
define('DB_DATABASE', 'dogaozka_bitpops');      // DB name
//$connection = mysql_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD) or die( "Unable to connect");
//$database = mysql_select_db(DB_DATABASE) or die( "Unable to select database");
$dbConnection = new PDO('mysql:host=localhost;dbname='.DB_DATABASE, DB_USERNAME, DB_PASSWORD);
$dbConnection->query("SET NAMES UTF8"); //for Turkish characters and also other unicode characters
?>
