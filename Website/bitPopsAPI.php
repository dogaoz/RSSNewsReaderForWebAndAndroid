<?php
//ini_set('display_errors', 'On');
require 'dbconfig.php';
require 'bitPopsAPI_operations.php';
session_start();
header('Content-Type: application/json');


  $aResult = array();

if( !isset($_POST['functionname']) ) { $aResult['error'] = 'Error bp908!'; }

if(isset($_POST['functionname']) 
	AND $_POST['functionname'] == 'deleteFeedFromUser' 
	AND !isset($_POST['feedID']) )
{ $aResult['error'] = 'Error bp915!'; }


	$userID = $_SESSION['USERID'];



    if( !isset($aResult['error']) ) {
        switch($_POST['functionname']) {
            case 'loadFeed':
               $aResult['result'] = loadFeed($userID);
               break;
               
            case 'myFeeds':
               $aResult['result'] = myFeeds($userID);
               break;
            case 'deleteFeedFromUser':
               $aResult['result'] = removeFeed($userID,$_POST['feedID']);
               break;
            default:
               //$aResult['error'] = 'Not found function '.$_POST['functionname'].'!';
               break;
        }
    	

    }
    
    
echo json_encode($aResult);

?>
