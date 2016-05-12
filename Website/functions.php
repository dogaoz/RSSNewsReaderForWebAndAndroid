<?php
ini_set('display_errors', 'On');
require 'dbconfig.php';

	function checkuser($fuid,$ffname,$flname,$femail)
	{
		global $dbConnection;
			$query = $dbConnection->prepare('select * from Users where fbUserID= ?');
			$query->execute(array($fuid));
			$result  = $query->fetchAll(PDO::FETCH_ASSOC);
			
		$check = count($result);
		if (empty($check)) 
		{ // if new user . Insert a new record		
			$q = "INSERT INTO Users (userName,userLastName,userEmail,fbUserID) VALUES (?,?,?,?);";
			$query = $dbConnection->prepare($q);
			$query->execute(array($ffname,$flname,$femail,$fuid));
				
		} 
		else
		{   // If Returned user . update the user record		
			$q = "UPDATE Users SET userName=?, userLastName=?, userEmail=? where fbUserID=?;";
			$query = $dbConnection->prepare($q);
			$query->execute(array($ffname,$flname,$femail,$fuid));
		}
	}
	
	function getUserIDusingFID($fuid)
	{
		global $dbConnection;	
		$q = "SELECT userID FROM Users WHERE fbUserID=?;";
		$query = $dbConnection->prepare($q);
		$query->execute(array($fuid));
		
		$rows = $query->fetchAll(PDO::FETCH_ASSOC);
	foreach( $rows as $row ) {
	return $row['userID'];
	}
	
	}

?>
