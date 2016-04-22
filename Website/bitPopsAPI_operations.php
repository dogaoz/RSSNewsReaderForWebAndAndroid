<?php
//ini_set('display_errors', 'On');


	function loadFeed($userID)
	{
    		$userFeeds = mysql_query("SELECT feedID FROM FeedsOfUsers WHERE userID='$userID';") or die(mysql_error());
			
			$count = mysql_num_rows($userFeeds);
			if ($count == 0) 
			{
				//User don't have any feeds
				//return getArticles('http://feeds2.feedburner.com/androidcentral');			
				return loadFeedPicker($userID);
			} 
			else 
			{ 
				$result = "";
				
				$debug = ""; //"DEBUG : These feeds urls loaded for userID =". $userID ." : </br>";
				while ($feedRow = mysql_fetch_array($userFeeds))
				{
				
		
					$feedID = $feedRow['feedID'];
					$feeds  = mysql_query("SELECT feedURL FROM Feeds WHERE feedID='$feedID';");
						while ($feed = mysql_fetch_array($feeds))
						{
						//$debug  = $debug . $feed['feedURL'] . '</br>';
						$result = $result . getArticles($feed['feedURL']);	
						
						}
						
				}
				$load_more_button = '<a class="navbar-loadmore" href="#">LOAD MORE</a>';
				return $debug . $result . $load_more_button;
	
			}	
	}

	function getArticles($feedURL)
	{
	$titles = array();
	$urls = array();
	$imageurls = array();
	$postedDate = array();
		
		//TODO : Get Articles in here
		
		$json = file_get_contents("http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=100&q=" . $feedURL);

		$data = json_decode($json);

		$responseData = $data->responseData;
		$feed = $responseData->feed;

			foreach ($feed->entries as $entry) 
			{
				$titles[] = $entry->title;
				$content_snippet = $entry->contentSnippet;
				$postedDate[] = $entry->publishedDate;
				$urls[] = $entry->link;
				$content = $entry->content;
				$rand = rand(1,10);
				$imageurls[] = 'http://bitpops.com/default_feed_images/rotary_default_' . $rand . '.jpg';
			}

	
	///////////////////////////////////////////////////////////
		$result = "";		
		//Then load every article into html	
		for ($i=0;$i < count($titles);$i++)
		{
		 //	$articleTitle = titles[i];
		 //	$articleURL = urls[i];
		 //	$articleImageURL = imageurls[i];

				
				 $result = $result . '<div class="clearfix col-sm-4 col-md-4 col-lg-4">' .
				 '<div class="grid-list"> ' .
				 '<a href="'. $urls[$i] .'" target="_blank">' .
				 '<img class="img-responsive" src="'. $imageurls[$i] .'" alt="' . $titles[$i] . '">' .
				 '</a>' .
				 '<div class="overlay">' .
				 '<a target="_blank"  href="' .  $urls[$i] . '">' .
				 '<h2>' .  $titles[$i] . '</h2>' .
				 '</a>' .
				 '</div>' .
				 '</div></div>';
				
						
		}
		
	return $result;
			
	}
	
	function loadFeedPicker($userID)
	{
	
		$result = '<h3 style="text-align:center;"> Pick Some Topics </h3>';
    	$feedCatsQ = mysql_query("SELECT * FROM Categories;");
    	$i = 0;
		while ($row = mysql_fetch_array($feedCatsQ))
		{	

			$result = $result . '' .
				 '<div id="topic_item_'. $i .'" class="topic-picker-grid"> ' .
				 '<img class="topic-picker-image" '.
				 'src="images/'. $row['categoryID'] ."_black_48dp.png" .'" >' .
				 '<div style="margin:1%;">' .
				 '<h5 style="text-align:center;word-wrap:break-word;color:#242424;">' .  $row['categoryName'] . '</h5>' .
				 '</div>' .
				 '</div>';
			$i++;
		}
		
		
	$nextButton = '
						<div class="topic-picker-next"> 
						<a class="next-btn" href="http://bitpops.com">NEXT</a>
						</div>';
	
	return $result . $nextButton  ;
	
	
	}
	
	
	function myFeeds($userID)
	{
		$userFeeds = mysql_query("SELECT feedID FROM FeedsOfUsers WHERE userID='$userID';") or die(mysql_error());
			
			$count = mysql_num_rows($userFeeds);
			if ($count == 0) 
			{
				//User don't have any feeds
				//			
				return loadFeedPicker($userID);
			} 
			else 
			{ 
				
				/// My Feeds
				$myfeeds = "";
				$result = '<div class="col-sm-6 col-md-6 col-lg-6">'
				. '<div class="grid-list"> '
				. '</br><h3 style="text-align:left;">&nbsp;&nbsp;&nbsp;Following : '.$count.' </h3>'
				.'<div id="feedlist" ><ul>';

				while ($feedRow = mysql_fetch_array($userFeeds))
				{
				
		
					$feedID = $feedRow['feedID'];
					$feeds  = mysql_query("SELECT * FROM Feeds WHERE feedID='$feedID';");
						while ($feed = mysql_fetch_array($feeds))
						{
						
						$myfeeds  = $myfeeds
						. '</br><form id="feedItem_'. $feedID .'" method="POST" >'
						. '<a class="border-text"> '
						. $feed['feedName'] .'</a>'
                        . '<input class="border-button-right" style="margin-right:3%;background:white;" type="button" onclick="unfollow('.$feedID.')" value="UNFOLLOW"/>'
                        . ' '
					    . '</form></br>';
						
							
						
						}
						
				}
				
				
				
				
			return $result . $myfeeds . '</br></ul></div></div></div>' . addFeedPanel($userID);		
	
			}	


		
		
	}
	
	
	function removeFeed($userID,$feedID)
	{
		$removeQuery = "DELETE FROM FeedsOfUsers WHERE (userID='$userID' AND feedID='$feedID');";
		$query = mysql_query($removeQuery);
		if ($query)
		{
		return myFeeds($userID);
		}
		else
		{
		return "There is an error occurred";
		}
	
	}
	
	
	function addFeedPanel($userID)
	{
	
		$result = '<div class="col-sm-6 col-md-6 col-lg-6">'
				. '<div class="grid-list"> '
				. '<h3>&nbsp;&nbsp;&nbsp; Pick a category to follow more... </h3>';
    	$feedCatsQ = mysql_query("SELECT * FROM Categories;");
    	
		while ($row = mysql_fetch_array($feedCatsQ))
		{	


			$result = $result . '' .
				 '<div class="grid-list" style="display: block;float:left; width:30%;margin-right:3%"> ' .
				 '<a  href="' .  $row['categoryID'] . '">' .
				 '<img class="img-responsive" '.
				 'style="height:64px;width:auto;display:block;margin-left:auto;margin-right: auto;"'
				 .'src="images/'. $row['categoryID'] ."_black_48dp.png" .'" >' .
				 '<div class="overlay">' .
				 '<a style="text-align:center;word-wrap:break-word;">' .  $row['categoryName'] . '</a>' .
				 '</a>' .
				 '</div>' .
				 '</div>';
			
		}
		
		
	
	return $result . '</div></div>' ;
	
	
	}
	
	

?>
