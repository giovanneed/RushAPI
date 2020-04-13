<?php



?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AJAX Photo Gallery</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" href="css_tab.css" type="text/css">
<script type="text/javascript" src="star.js"></script>
<script type="text/javascript" src="tabs.js"></script>

<script>
         FB.init({ 
            appId:'YOUR_APP_ID', cookie:true, 
            status:true, xfbml:true 
         });
</script>

</head>
<body onload="loadStars()">
<div id="principal" align="center">
<div id="gallery">
  <div id="imagearea">
    <div id="image">
      <a href="javascript:slideShow.nav(-1)" class="imgnav " id="previmg"></a>
      <a href="javascript:slideShow.nav(1)" class="imgnav " id="nextimg"></a>
    </div>
  </div>
  <div id="thumbwrapper">
    <div id="thumbarea">
      <ul id="thumbs">
        <li value="l1"><img src="thumbs/1.jpg" width="179" height="100" alt="" /></li>
        <li value="l2"><img src="thumbs/2.jpg" width="179" height="100" alt="" /></li>
        <li value="l3"><img src="thumbs/3.jpg" width="179" height="100" alt="" /></li>
        <li value="l4"><img src="thumbs/4.jpg" width="179" height="100" alt="" /></li>
        <li value="l5"><img src="thumbs/5.jpg" width="179" height="100" alt="" /></li>
      </ul>
    </div>
  </div>
</div>
<div id="stars">

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="1" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="2" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="3" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="4" style="width:30px; height:30px; " />

<img src="star1.gif" onmouseover="highlight(this.id)" onclick="setStar(this.id)" onmouseout="losehighlight(this.id)" id="5" style="width:30px; height:30px; " /><br /><br />

<div id="vote" style="font-family:tahoma; color:red;"></div>
</div>


</div>
<script type="text/javascript">
var imgid = 'image';
var imgdir = 'fullsize';
var imgext = '.jpg';
var thumbid = 'thumbs';
var auto = true;
var autodelay = 5;
</script>
<script type="text/javascript" src="slide.js"></script>

</body>
</html>