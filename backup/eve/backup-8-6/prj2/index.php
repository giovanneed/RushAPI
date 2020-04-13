<?php



?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="star.js"></script>
	<script type="text/javascript" src="tabs.js"></script>
	<script src="js/jquery-1.4.4.min.js" type="text/javascript"></script> 
		
    <link rel="stylesheet" href="css/prettyPhoto.css" type="text/css" media="screen" title="prettyPhoto main stylesheet" charset="utf-8" /> 
    <script src="js/jquery.prettyPhoto.js" type="text/javascript" charset="utf-8"></script> 
	
	
	<link rel="stylesheet" href="css_tab.css" type="text/css">
	<style type="text/css" media="screen"> 
			* { margin: 0; padding: 0; }
			
			ul li { display: inline; }
		
	</style> 


	 <script>
         FB.init({ 
            appId:'YOUR_APP_ID', cookie:true, 
            status:true, xfbml:true 
         });
      </script>

	
  </head>
  <body onload="loadStars()">
  <br><br>
  <div id="principal" align="center">
  <table border="1" width="60%"> 
    <tr>
	  <td  width="40%"  align="center"><img src="ft_prf.png"></td>
	  <td valign="top" align="center">
	  <br>
	  <ul class="gallery clearfix"> 
				<li><a href="images/fullscreen/1.JPG?lol=lol" rel="prettyPhoto[gallery1]" title="You can add caption to pictures. You can add caption to pictures. You can add caption to pictures."><img src="images/thumbnails/t_1.jpg" width="60" height="60" alt="Red round shape" /></a></li> 
				<li><a href="images/fullscreen/2.jpg" rel="prettyPhoto[gallery1]"><img src="images/thumbnails/t_2.jpg" width="60" height="60" alt="Nice building" /></a></li> 
				<li><a href="images/fullscreen/3.jpg" rel="prettyPhoto[gallery1]"><img src="images/thumbnails/t_3.jpg" width="60" height="60" alt="Fire!" /></a></li> 
				<li><a href="images/fullscreen/4.jpg" rel="prettyPhoto[gallery1]"><img src="images/thumbnails/t_4.jpg" width="60" height="60" alt="Rock climbing" /></a></li> 
				<li><a href="images/fullscreen/5.jpg" rel="prettyPhoto[gallery1]"><img src="images/thumbnails/t_5.jpg" width="60" height="60" alt="Fly kite, fly!" /></a></li> 
				<li><a href="images/fullscreen/6.jpg" rel="prettyPhoto[gallery1]"><img src="images/thumbnails/t_2.jpg" width="60" height="60" alt="Nice building" /></a></li> 
	 </ul> 
	
	
	 </td>
	 </tr>
  </table>	 
  <br>
	  

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="1" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="2" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="3" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="4" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="5" style="width:30px; height:30px; " /><br /><br />

<div id="vote" style="font-family:tahoma; color:red;"></div>



<div id="tabs" > 
 
<h2>Comentario:</h2> 
 
<table width="760" height="120" cellspacing="0" cellpadding="0" border="0" style="border-left: 1px solid #000000;"> 
	<tr> 
		<td height="10" width="100" class="menu" id="td_anonimo"
		onClick="AlternarAbas('td_anonimo','div_anonimo')"> 
			<h1>Anonimo</h1>
		</td> 
		<td height="10" width="100" class="menu" id="td_facebook"
		onClick="AlternarAbas('td_facebook','div_facebook')"> 
			<h1>Facebook</h1>
		</td> 
		 
		<td width="460" style="border-bottom: 1px solid #000000"> 
			&nbsp;
		<td> 
	</tr> 
	<tr> 
		<td height="120" class="tb-conteudo" colspan="3"> 
			<div id="div_anonimo" class="conteudo" style="display: none"> 
				<textarea rows="7" cols="90" resize="none"> </textarea>
			</div> 
			<div id="div_facebook" class="conteudo" style="display: none"> 
			<br><div id="cont_facebook">
			<div id="fb-root"></div>
      <script src="http://connect.facebook.net/en_US/all.js"></script>
      <script>
         FB.init({ 
            appId:'YOUR_APP_ID', cookie:true, 
            status:true, xfbml:true 
         });
      </script>
      <fb:login-button>Login with Facebook</fb:login-button>
		</div>		
			</div> 
		
		</td> 
	</tr> 
</table> 
<div id="enviar_button" align="center" ><input type=Button value="Enviar" onClick="AlternarAbas('td_anonimo','div_anonimo')" >

</div> 
</div>

<script type="text/javascript" charset="utf-8"> 
			$(document).ready(function(){
				AlternarAbas('td_anonimo','div_anonimo');
			
				$("area[rel^='prettyPhoto']").prettyPhoto();
				
				$(".gallery:first a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'normal',theme:'facebook',slideshow:3000, autoplay_slideshow: true});
				$(".gallery:gt(0) a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'fast',slideshow:10000, hideflash: true});	
		
				
			});
</script> 






</body>

</html>
