<?php


//http://cubiq.org/iscroll

//conexão com o servidor
$conect = mysql_connect("dbmy0042.whservidor.com", "infolove_1", "ged182");

// Caso a conexão seja reprovada, exibe na tela uma mensagem de erro
if (!$conect) die ("<h1>Falha na coneco com o Banco de Dados!</h1>");

// Caso a conexão seja aprovada, então conecta o Banco de Dados.	
$db = mysql_select_db("infolove_1");

echo "Loading...";
echo "<br />";

$query = mysql_query("SELECT * FROM TabEstabelecimento2 ORDER BY Nome") or die(mysql_error());
?>
 

<html>
    <head>
		 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Principal</title>
		 
		 <link rel="stylesheet"  href="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.css" />
		 <link rel="stylesheet" href="docs/_assets/css/jqm-docs.css" />

		 <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.min.js"></script>
         <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.js"></script>
			
         <script type="text/javascript" src="docs/_assets/js/jqm-docs.js"></script>
		 <script type="text/javascript" src="buscageo.js"></script>
	   
         <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAA_ATXoZkh-wHjOkEgsaCqlhQeDqxLG4IyDX1Tz0AFNOKheMcL-xQRI6T-rElHUmpSyfnbtABF9rnWWw"
            type="text/javascript"></script>
		
		
		<style type="text/css">
		.infowindow{
			width: 100%;
			hight: 50%;
			
		}
		 #infotable{
     font-size: 12px;
     font-family:verdana;
     }

		#mapa { height: 600px; width: 100%; margin: 0px; padding: 0px }

		
	
		</style>
	
	
	<script type="text/javascript">
	
			
			var mostrarEstabelecimento = "all";
			var inicial = new GLatLng(-19.918761,-43.938621);
		    var centro="nao def";
			var map;
			
			function testecentro(){
				alert("teste centro");
				
				alert(map.getCenter);
			}
		  
			function enviaForm(){
			
			 //Verifica 
			 document.eventos.submit() ;
			
			}
			function recarrega(estab){
			
			
			 
			 mostrarEstabelecimento = estab;
			
			 initialize();
			}
			
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
			       
                  //var geoXml;
                  if (GBrowserIsCompatible()) {
                       map = new GMap2(document.getElementById("mapa"));
                       //geoXml = new GGeoXml("http://localhost:8084/eventos/teste.kml");
						
						
						
						//alert("Depois da funcao: " + inicial);
						//alert("cade?" + inicial);
						//inicializa mapa
						//new GLatLng(-19.918761,-43.938621)
						//alert(inicial);
						retornalatlng();
						//alert(inicial);
                        map.setCenter(inicial, 14);
						centro = map.getCenter();
						map.setMapType(G_NORMAL_MAP);
						map.addControl(new TextualZoomControl());		
                        //map.addOverlay(geoXml);
						
						
					    //cria icone
						var iconBoate = new GIcon();
						iconBoate.image = "boate.png";
						iconBoate.shadow = "boateb.png";
						iconBoate.iconSize = new GSize(25, 25);
						iconBoate.shadowSize = new GSize(25, 25);
						iconBoate.iconAnchor = new GPoint(25, 25);
						iconBoate.infoWindowAnchor = new GPoint(25, 25);
		
						
						var iconBar = new GIcon();
						iconBar.image = "bar.png";
						iconBar.shadow = "barb.png";
						iconBar.iconSize = new GSize(25,25);
						iconBar.shadowSize = new GSize(25, 25);
						iconBar.iconAnchor = new GPoint(25, 25);
						iconBar.infoWindowAnchor = new GPoint(25, 25);
						
						var iconAcademico = new GIcon();
						iconAcademico.image = "academico.png";
						iconAcademico.shadow = "academicob.png";
						iconAcademico.iconSize = new GSize(25, 25);
						iconAcademico.shadowSize = new GSize(25, 25);
						iconAcademico.iconAnchor = new GPoint(25, 25);
						iconAcademico.infoWindowAnchor = new GPoint(25, 25);
						 
						
					
						
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
							if ($array['Tipo'] == "Boate"){
						?> var icon = iconBoate;<?
						}else if($array['Tipo'] == "Bar"){
						?> var icon = iconBar; <?
						}else if($array['Tipo'] == "Academico"){
						?> var icon = iconAcademico; <?
						}
						
						?>
						
						
						
						
					    
							
						
					    
						var <?echo "marker".$array['CodEstabelecimento']?> = new GMarker(new GLatLng("<?echo $array['Lat']?>","<?echo $array['Lon']?>"),icon);
						
						if(mostrarEstabelecimento == "all"){
						
							map.addOverlay(<?echo "marker".$array['CodEstabelecimento']?>);
						}else if (mostrarEstabelecimento == "<? echo $array['Tipo'] ?>") {
						  
						  map.addOverlay(<?echo "marker".$array['CodEstabelecimento']?>);
						
						}
						
						
						
						
						GEvent.addListener(<?echo "marker".$array['CodEstabelecimento']?>, "click", function() {
								<?echo "marker".$array['CodEstabelecimento']?>.openInfoWindowHtml("<div id='window' class='infowindow'><b>Nome:</b> <?echo $array['Nome']?> <br><input type='button' value='Ver Eventos' OnClick=enviaForm()><br> <b>Nota:</b> <?echo $array['Avaliacao']?></div>");
								document.getElementById('codigo').value= '<?echo $array['CodEstabelecimento']?>';								
								
								
								
								
								});
						
						<?}
						?>

                          



                        
                    }
			 }
	</script>
	</head>		
	
	 <body onLoad=" initialize()">
	 <div data-role="page" id="principal">
		<div data-role="header"  data-position="fixed">
    	      
               <h1>infoEventos v0.1</h1>
         </div>
		  
		<div data-role="content" id='mapa'  ></div>
	
		<form name="eventos" method="GET" action="ExibirEventos.php">
			<input type="hidden" id="codigo" name="codigo" value="0">
		</form>	
		
		  
		
		<div data-role="footer"  data-position="fixed">

              <div data-role="navbar">
                   <ul>

					<li>
			                 <a href="#" id="chat" data-icon="grid" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" data-transition="pop" data-rel="dialog"  OnClick=recarrega("Bar")>
                             <span class="ui-btn-text">Bar</span>
                            </a>
                        </li>
                        <li>
                            <a href="#" id="chat" data-icon="plus" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a"  data-transition="pop" OnClick=recarrega("Boate")>
                             <span class="ui-btn-text">Boate</span>

                            </a>
                        </li>
                        <li>
                            <a href="#" id="chat" data-icon="home" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" OnClick=recarrega("Academico")>
                             <span class="ui-btn-text">Academico</span>
                            </a>
                        </li>
						<li>
                            <a href="#" id="chat" data-icon="home" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" OnClick=testecentro()>
                             <span class="ui-btn-text">Todos</span>
                            </a>
                        </li>
						  <li>
                            <a href="#sobre" id="chat" data-icon="home" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" OnClick=recarrega("Academico")>
                             <span class="ui-btn-text">Sobre</span>
                            </a>
                        </li>
		   </ul>

              </div>

          </div>
		
	 </div>
	 
	 <div data-role="page" id="sobre">
	   <div data-role="header" data-position="inline" data-nobackbtn="true">
        <h1>infoEventos Versão 01</h1>
       </div> 
	  
	   Autor: Giovanne Dias
	   Versão: 0.1
	 </div>
	 
	 
	 </div>
	  
	  
	 
	  
					
						
				
	  
</html>
