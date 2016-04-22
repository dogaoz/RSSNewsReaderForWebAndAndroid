<!DOCTYPE html>
<?php session_start();?> 
   <?php if ($_SESSION['FBID']): ?> 
		<script type='text/javascript'>
		window.location.href = 'home.php';
		</script>
		<?php else: ?> 
<html>
  <head>    
  
  
    <meta charset="UTF-8">
    <title>BitPops</title>
    
     <meta name="description" content="BitPops is the platform that allows users to comment on and discuss any article on the internet with other users." />

    
    
        <link rel="stylesheet" href="css/login_style.css">

    
    
    
  </head>

  <body>
   <div class="login-page">
	<a class="title"> BitPops </a>
  <div class="form">

 <form class="register-form">
		<a class="sub">BitPops is the platform that allows users to comment on and discuss any article on the internet with other users. Bitpops generates timeline for you that is created by you at the beginning by using websites and blogs. And while reading an article it allows you to discuss the article with other people.</a>

      <p class="message"><a class="sub" href="#">Sign In Now!</a></p>
    </form>
      <!-- Before login --> 
   <form class="login-form" action="fbconfig.php">
    

	<a class="sub">Comment on anything on the web!</a>

       <!--<input type="text" placeholder="username"/>
      <input type="password" placeholder="password"/>-->
	  </br>
	  <input type="image" src="images/fb_button.png" />
	  
	  <!--<input type="image" src="images/gplus_button.png" onsubmit="gSignIn();" /> -->
	  </br>
			<p class="message"><a class="sub" href="#">What is BitPops?</a></p>

    </form>
    
	
	

    
 
  </div>
</div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
     <script src="js/login_anim.js"></script>
</body>
</html>
	<?php endif ?>