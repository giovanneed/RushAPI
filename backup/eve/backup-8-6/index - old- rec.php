<?php

//conexão com o servidor
$conect = mysql_connect("dbmy0042.whservidor.com", "infolove_1", "ged182");

// Caso a conexão seja reprovada, exibe na tela uma mensagem de erro
if (!$conect) die ("<h1>Falha na coneco com o Banco de Dados!</h1>");

// Caso a conexão seja aprovada, então conecta o Banco de Dados.	
$db = mysql_select_db("infolove_1");

echo "conectado!";
echo "<br />";

$query = mysql_query("SELECT * FROM TabEstabelecimento ORDER BY nome") or die(mysql_error());
 
//faz um looping e cria um array com os campos da consulta
while($array = mysql_fetch_array($query))
{
  //mostra na tela o nome e a data de nascimento
  echo $array['cod']." ".$array['nome']." ".$array['endereco']." ".$array['telefone']." ".$array['site']." ".$array['foto']." ".$array['descricao']." ".$array['avalicao']." ".$array['lat']." ".$array['lon']. "<br />";
  
  $nome = $array['nome'];
  $endereco = $array['endereco'];
  $telefone = $array['telefone'];
  $site = $array['site'];
  $foto = $array['foto'];
  $descricao = $array['descricao'];
  $avaliacao = $array['avaliacao'];
  $lat = $array['lat'];
  $lon = $array['lon'];
  $descricao = $array['descricao'];	
}
?>
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
						var marker = new GMarker(new GLatLng("<?echo $lat?>","<?echo $lon?>"),icon);
                        
                        map.addOverlay(marker);
						
						GEvent.addListener(marker, "click", function() {
								marker.openInfoWindowHtml("<b>Nome:</b> <?echo $nome?> <br><br> <?echo $descricao?> <br> <b>Nota:</b> <?echo $avaliacao?> ");
								});
								
						//Marker apartir do banco de dados	
						
						<?
						while($array = mysql_fetch_array($query)){
					
						?>
						
						alert("<?echo $array['lat']?>");
						alert("<?echo $array['lon']?>");
						var marker1 = new GMarker(new GLatLng("<?echo $array['lat']?>","<?echo $array['lon']?>"),icon);
						map.addOverlay(marker1);
						
						<?}
						?>

						
						
						
                        
						
						GEvent.addListener(marker1, "click", function() {
								marker.openInfoWindowHtml("<b>Nome:</b> <?echo $nome?> <br><br> <?echo $descricao?> <br> <b>Nota:</b> <?echo $avaliacao?> ");
								});
							
						
							
							
							var point = new GLatLng("-19.933227","-43.938546");
                            map.addOverlay(new GMarker(point));
                            var point = new GLatLng(" -19.925078","-43.921895");
                            map.addOverlay(new GMarker(point));
                            var point = new GLatLng("-19.921689","-43.952193");
                            map.addOverlay(new GMarker(point));
                            var point = new GLatLng("-19.915729","-43.931844");
                            map.addOverlay(new GMarker(point));
                   



                        
                    }
			 }
	</script>
	</head>		
	
	 <body onLoad=" initialize()">
	  <div id="map_canvas" style="width: 85%; height: 85%"></div>
	  <br> <img src="http://www.w3schools.com/images/compatible_chrome.gif" />
	  <?
						$count = count($array);
						echo $count;
						echo "<br><b>teste</b><br>";
for ($i = 1; $i < $count; $i++) {
   
    echo $array['$nome'];
	echo $i;
}					
						?>
	  
</html>

