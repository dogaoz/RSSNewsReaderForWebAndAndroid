<!DOCTYPE html>
<?php
session_start();
?>
   <?php if (!$_SESSION['USERID']): ?> 
		<script type='text/javascript'>
		window.location.href = 'index.php';
		</script>
		<?php else: ?> 
<html lang="en">
<head>
    <!-- jQuery from CDN -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script src="js/jribbble.js"></script>
      <script src="js/bootstrap.js"></script>
 
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    
    <title>BitPops</title>
    
    <!-- Bootstrap + Font Awesome + Main CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- customize this file if needed -->
    <link href="css/main.css" rel="stylesheet">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script>

function follow(fid) {
  
   jQuery.ajax({
    type: "POST",
    url: 'bitPopsAPI.php',
    data: {functionname: 'addFeedToUser',feedID: fid},
    dataType: 'json',
    success: function (obj, textstatus) 
    {
                  if( !('error' in obj) )
                  {
                    data = obj.result;
                    document.getElementById("myFeeds").innerHTML = data.toString();

                  }
            	  else
               	  {
                      document.getElementById("myFeeds").innerHTML = obj.error;
                  }
            }
	});

    }

function unfollow(fid) {
  
   jQuery.ajax({
    type: "POST",
    url: 'bitPopsAPI.php',
    data: {functionname: 'deleteFeedFromUser',feedID: fid},
    dataType: 'json',
    success: function (obj, textstatus) 
    {
                  if( !('error' in obj) )
                  {
                    data = obj.result;
                    document.getElementById("myFeeds").innerHTML = data.toString();

                  }
            	  else
               	  {
                      document.getElementById("myFeeds").innerHTML = obj.error;
                  }
            }
	});

    }
    
    jQuery.ajax({
    type: "POST",
    url: 'bitPopsAPI.php',
    data: {functionname: 'myFeeds'},
    dataType: 'json',
    success: function (obj, textstatus) {
                  if( !('error' in obj) ) {
                      data = obj.result;
                    document.getElementById("myFeeds").innerHTML = data.toString();

                  }
                  else {
                      document.getElementById("myFeeds").innerHTML = obj.error;
                  }
            }
});
</script>  

  					
</head>

<body>
    <div class="wrapper">
        <div class="container">
                  <?php include_once 'header.php'; ?>
                  
			<div class="row">
                    <div id="myFeeds" class="col-md-12 col-lg-12">

                    </div>
                </div>

            <div class="row grid-list-wrapper no-gutter-space" id="shots"></div>
         </div>
		 
    </div>


              <?php include_once 'footer.php'; ?>

</body>
</html>
<?php endif ?>