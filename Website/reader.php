<!DOCTYPE html>
<?php
session_start();
?>
   <?php if (!$_SESSION['USERID']): ?> 
		<script type='text/javascript'>
		window.location.href = 'index.php';
		</script>
		<?php else: ?> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

 	<title><?php $_SESSION['temp_postTitle'] ?> </title>
	<link href="css/reader_style.css" rel="stylesheet">

	
</head>

<body>

  <div id="container">
    <iframe src="<?php $_SESSION['temp_postURL'] ?> http://google.com"></iframe>
  </div>
  <div id="footer"> Hello </div>

</body>
</html>





<?php endif?>