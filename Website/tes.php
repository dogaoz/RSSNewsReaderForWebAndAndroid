<?php
ini_set('display_errors', 'On');
require 'dbconfig.php';
require 'bitPopsAPI_operations.php';

$userID = 1;
echo loadFeed($userID);

?>
