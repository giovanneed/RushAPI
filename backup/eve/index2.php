
<html>
    <head>
		 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Principal</title>
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAA_ATXoZkh-wHjOkEgsaCqlhQeDqxLG4IyDX1Tz0AFNOKheMcL-xQRI6T-rElHUmpSyfnbtABF9rnWWw"
            type="text/javascript"></script>
	
	
	<script type="text/javascript">
			//Customizar zoom
			function TextualZoomControl() {
				}
			TextualZoomControl.prototype = new GControl();
			
			TextualZoomControl.prototype.initialize = function(map) {
				var container = document.createElement("div");
 
				var zoomInDiv = document.createElement("div");
				this.setButtonStyle_(zoomInDiv);
				container.appendChild(zoomInDiv);
				zoomInDiv.appendChild(document.createTextNode("Zoom In"));
				
				GEvent.addDomListener(zoomInDiv, "click", function() {
				map.zoomIn();
				});
 
				var zoomOutDiv = document.createElement("div");
				this.setButtonStyle_(zoomOutDiv);
				container.appendChild(zoomOutDiv);
				zoomOutDiv.appendChild(document.createTextNode("Zoom Out"));
				
				GEvent.addDomListener(zoomOutDiv, "click", function() {
				map.zoomOut();
				});
 
				map.getContainer().appendChild(container);
				return container;
			}
			
			 TextualZoomControl.prototype.getDefaultPosition = function() {
					return new GControlPosition(G_ANCHOR_TOP_LEFT, new GSize(7, 7));
			}
			
			TextualZoomControl.prototype.setButtonStyle_ = function(button) {
				button.style.textDecoration = "underline";
				button.style.color = "#0000cc";
				button.style.backgroundColor = "white";
				button.style.font = "small Arial";
				button.style.border = "1px solid black";
				button.style.padding = "2px";
				button.style.marginBottom = "3px";
				button.style.textAlign = "center";
				button.style.width = "6em";
				button.style.cursor = "pointer";
			}
	
	
	
	
	
	
	
	
	
              function initialize() {
			    
                  var geoXml;
                  if (GBrowserIsCompatible()) {
                        var map = new GMap2(document.getElementById("map_canvas"));
                       geoXml = new GGeoXml("http://localhost:8084/eventos/teste.kml");
                       map.setCenter(new GLatLng(-19.918761,-43.938621), 13);
					
						
						map.setMapType(G_NORMAL_MAP);
					
						map.addControl(new TextualZoomControl());						
                        map.addOverlay(geoXml);
				
				
		
				
						
				
				
                        // Add 10 markers to the map at random locations
						var icon = new GIcon();
						icon.image = "a.png";
						icon.shadow = "b.png";
						icon.iconSize = new GSize(18, 18);
						icon.shadowSize = new GSize(18, 18);
						icon.iconAnchor = new GPoint(18, 18);
						icon.infoWindowAnchor = new GPoint(18, 18);
						 
							var marker = new GMarker(new GLatLng("-19.933227","-43.938546"),icon);
							
							map.addOverlay(marker);
							
							GEvent.addListener(marker, "click", function() {
								marker.openInfoWindowHtml(" <iframe src='http://www.facebook.com/plugins/like.php?href=YOUR_URL' scrolling='no' frameborder='0' style='border:none; width:450px; height:80px'></iframe>");
    							
								});
					
                        
                    }
			 }
	</script>
	</head>		
	
	 <body onLoad=" initialize()">
	 	 <div id="map_canvas" style="width: 100%; height: 70%"></div>
</html>

