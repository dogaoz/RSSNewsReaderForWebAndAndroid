<?php
ini_set('display_errors', 'On');
require 'dbconfig.php';

	function checkuser($fuid,$ffname,$flname,$femail)
	{
    	$check = mysql_query("select * from Users where fbUserID='$fuid'");
		$check = mysql_num_rows($check);
		if (empty($check)) 
		{ // if new user . Insert a new record		
			$query = "INSERT INTO Users (userName,userLastName,userEmail,fbUserID) VALUES ('$ffname','$flname','$femail','$fuid');";
			mysql_query($query);	
		} 
		else
		{   // If Returned user . update the user record		
			$query = "UPDATE Users SET userName='$ffname', userLastName='$flname', userEmail='$femail' where fbUserID='$fuid';";
			mysql_query($query);
		}
	}
	
	function getUserIDusingFID($fuid)
	{
		$query = mysql_query("SELECT userID FROM Users WHERE fbUserID='$fuid';");
		$row = mysql_fetch_array($query);

	return $row[0];
	}

?>
