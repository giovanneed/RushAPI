<!DOCTYPE html>
<html>
<head>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="javascript/jquery-1.4.3.min.js" type="text/javascript"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <style type="text/css">
    #slider { margin: 10px; }
  </style>
  <script>
  $(document).ready(function() {
    $("#slider").slider({
	min: 5,
	max: 10,
	value: 7,
		change: function() {
			alert($("#slider").slider( "option", "value" )); 
		}
	});
  });
  </script>
</head>
<body style="font-size:62.5%;">
  
<div id="slider"></div>

</body>
</html>
