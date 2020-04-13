<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://maps.google.com/maps?file=api&v=2&key=ABQIAAAA_ATXoZkh-wHjOkEgsaCqlhQeDqxLG4IyDX1Tz0AFNOKheMcL-xQRI6T-rElHUmpSyfnbtABF9rnWWw" type="text/javascript"></script>

    <link rel="stylesheet"  href="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.css" />
    <link rel="stylesheet" href="docs/_assets/css/jqm-docs.css" />

    <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.js"></script>
    
    <script type="text/javascript" src="docs/_assets/js/jqm-docs.js"></script>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAA_ATXoZkh-wHjOkEgsaCqlhQeDqxLG4IyDX1Tz0AFNOKheMcL-xQRI6T-rElHUmpSyfnbtABF9rnWWw"
            type="text/javascript"></script>
		<style type="text/css">
		.infowindow{
			width: 100%;
			hight: 50%;
			border-style:solid; 
		}
		
     </style>
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
            //fim customizar zoom
			
			
			function initialize() {
			    
                  var geoXml;
                  if (GBrowserIsCompatible()) {
                       var map = new GMap2(document.getElementById("map_canvas"));
                       geoXml = new GGeoXml("http://localhost:8084/eventos/teste.kml");

						//inicializa mapa
                        map.setCenter(new GLatLng(-19.918761,-43.938621), 14);
						map.setMapType(G_NORMAL_MAP);
						map.addControl(new TextualZoomControl());		
                        map.addOverlay(geoXml);
						
						
						//cria icone
						var icon = new GIcon();
						icon.image = "a.png";
						icon.shadow = "b.png";
						icon.iconSize = new GSize(18, 18);
						icon.shadowSize = new GSize(18, 18);
						icon.iconAnchor = new GPoint(18, 18);
						icon.infoWindowAnchor = new GPoint(18, 18);
						 
						
						
						
						//Marker apartir do banco de dados					    
						/*var marker = new GMarker(new GLatLng("<?echo $lat?>","<?echo $lon?>"),icon);
                        
                        map.addOverlay(marker);
						
						GEvent.addListener(marker, "click", function() {
								marker.openInfoWindowHtml("<b>Nome:</b> <?echo $nome?> <br><br> <?echo $descricao?> <br> <b>Nota:</b> <?echo $avaliacao?> ");
								});
								*/
						//Marker apartir do banco de dados	
						
						<?
						while($array = mysql_fetch_array($query)){
					
						?>
						
					    
						var <?echo "marker".$array['cod']?> = new GMarker(new GLatLng("<?echo $array['lat']?>","<?echo $array['lon']?>"),icon);
						map.addOverlay(<?echo "marker".$array['cod']?>);
						
						alert("<?echo "marker".$array['cod']?>");
						GEvent.addListener(<?echo "marker".$array['cod']?>, "click", function() {
								<?echo "marker".$array['cod']?>.openInfoWindowHtml("<div id='window' class='infowindow'><b>Nome:</b> <?echo $array['nome']?> <br><br> <?echo $array['descricao']?> <br> <b>Nota:</b> <?echo $array['avaliacao']?></div>");
								});
						
						<?}
						?>

						
					
						
                        
						
						
							
						
							
							
							
                            var point = new GLatLng(" -19.925078","-43.921895");
                            map.addOverlay(new GMarker(point));
                            var point = new GLatLng("-19.921689","-43.952193");
                            map.addOverlay(new GMarker(point));
                            var point = new GLatLng("-19.915729","-43.931844");
                            map.addOverlay(new GMarker(point));
                   



                        
                    }
			 }
	</script>





</script>

  </head>

  <body onLoad="initialize()">
      <div data-role="page" id="principal01">

         <div data-role="header">
    	       <a href="index01.html"  data-icon="back"  data-transition="slide">Back</a>
               <h1>index01</h1>
         </div>

          <div data-role="content">

             <div id="map_canvas" style="width: 85%; height: 85%"></div>
              <input type="button" onClick="localizar()" value="Localizar">

          </div>

          <div data-role="footer">

              <div data-role="navbar">
                   <ul>

			<li>
                            <a href="listar01.html" id="chat" data-icon="grid" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" data-transition="pop">
                             <span class="ui-btn-text">LISTAR</span>
                            </a>
                        </li>
                        <li>
                            <a href="criar.html" id="chat" data-icon="plus" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a"  data-transition="pop">
                             <span class="ui-btn-text">CRIAR</span>

                            </a>
                        </li>
                        <li>
                            <a href="#" id="chat" data-icon="home" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a">
                             <span class="ui-btn-text">MEUS EVE</span>
                            </a>
                        </li>
		   </ul>

              </div>

          </div>

      </div>




      
    TODO write content
  </body>
</html>
