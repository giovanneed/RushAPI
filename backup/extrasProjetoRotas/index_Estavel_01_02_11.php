<?php
require_once('dbinfo.php');
require_once('utils.php');
require('queries.php');
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta author="Cristóferson Bueno" />
<meta contact="cbueno81 at google mail" />
<title>Roteiro da Copa - Prefeitura de Belo Horizonte</title>
<style type="text/css">
@import url(style.css);
</style>
<script src="javascript/jquery-1.4.3.min.js" type="text/javascript"></script>
<script src="javascript/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script>
<script src="javascript/javascript.js" type="text/javascript"></script>
<!--script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=pt-BR"></script--> <!-- Verificar para pegar o idoma pelo parâmetro -->
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">

	/* 
	 * Otimizar código, criar objetos de placemarkers e carregar todas as informações nestes objetos, para diminuir o roundup na api do Google, evitando assim de estourar o limite de requisições.
	 * talvez já trazer essas informações do php.
	 - Ex:	placemarkers.hotels[id].address = geocodedAddress;
			placemarkers.hotels[id].photo = hotel[id].photo;
			
	 - array placemarkers construido apenas na inicialização;
	 
	 * Modularizar operação de reverseGeocode para sanatizar o código;
	*/
	var map;
	var bounds;
	var geocoder;
	var infowindow;
	var markers = [];
	var allHotelsMarkers = [];
	var directionDisplay;
	var directionsService;
	var arrayCoordenadasRota = [];
	var enderecoInicial;
	var enderecoFinal;
	var centroDaRota;
	var streetView = false;
	var botaoPlayRoute = false;
	var panorama;
	var panoramaOptions;
	var idxOrigem = 0;
	var idxDestino = 1;
	var local;
	var links;
	var bearing;
	var menorDeltaHeading;
	
	var distanciaAnterior = Number.MAX_VALUE;
	var mudaVertice = true;
	var primeiraVez = true;
	//var mudaLink = false;
	
		
	function initialize() {
		var latlng = new google.maps.LatLng(-19.815730, -43.954222);
		var myOptions = {
			zoom: 12,
			center: latlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
			
		map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
		
		geocoder = new google.maps.Geocoder();
		
		var rendererOptions = {
			draggable: true
		};
		
		directionsService = new google.maps.DirectionsService();
		directionsRenderer = new google.maps.DirectionsRenderer(rendererOptions);
		bounds = new google.maps.LatLngBounds();

		directionsRenderer.setMap(map);
		directionsRenderer.setPanel(document.getElementById('directionsPanel'));
		
		var currentwindow = null;
		infowindow = new google.maps.InfoWindow();

		google.maps.event.addListener(directionsRenderer, "directions_changed", function() { 
			traceRoute();
		});
			
		var locationStadium = getLatLong('-19.866386;-43.971092');
		stadium = 'images/stadium.gif';
		var imageStadium = new google.maps.MarkerImage(stadium,
			// This marker is 20 pixels wide by 32 pixels tall.
			new google.maps.Size(64, 64),
			new google.maps.Point(0,0),
			new google.maps.Point(32, 32));

		var marker1 = new google.maps.Marker({
			position: locationStadium,
			icon: imageStadium,
			map: map
		});

		var infoname1 = 'Estádio Governador Magalhães Pinto - Mineirão<br />São Luís, Belo Horizonte - MG, Brazil';
		var infoimage1 = 'images/mineirao.jpg"';
		bindInfoWindow(marker1, infoname1, infoimage1);
		
		// Aeroporto Confins
		var airportico = 'images/airport.png';
		var imageAirport = new google.maps.MarkerImage(airportico,
			// This marker is 20 pixels wide by 32 pixels tall.
			new google.maps.Size(32, 34),
			new google.maps.Point(0,0),
			new google.maps.Point(20, 22));

		var locationAirportConfins = getLatLong('-19.63370;-43.96780');
		var marker2 = new google.maps.Marker({
			position: locationAirportConfins,
			icon: imageAirport,
			map: map
		});

		var infoname2 = 'Tancredo Neves International Airport (CNF)<br />Confins - Minas Gerais, Brazil';
		var infoimage2 = 'images/confins.jpg"';
		bindInfoWindow(marker2, infoname2, infoimage2);

		// Aeroporto Pampulha
		var locationAirportPampulha = getLatLong('-19.84947;-43.94887');
		var marker3 = new google.maps.Marker({
			position: locationAirportPampulha,
			icon: imageAirport,
			map: map
		});

		var infoname3 = 'Aeroporto da Pampulha - Carlos Drummond de Andrade Airport (PLU)<br />Aeroporto, Belo Horizonte - Minas Gerais, Brazil';
		var infoimage3 = 'images/pampulha.jpg"';
		bindInfoWindow(marker3, infoname3, infoimage3);
		
		// Estádio Idependência
		var idependenciaIco = 'images/arena2.png';
		var imageArena = new google.maps.MarkerImage(idependenciaIco,
			// This marker is 20 pixels wide by 32 pixels tall.
			new google.maps.Size(32, 34),
			new google.maps.Point(0,0),
			new google.maps.Point(20, 22));

		var locationIdependenciaArena = getLatLong('-19.90896;-43.90871');
		var marker4 = new google.maps.Marker({
			position: locationIdependenciaArena,
			icon: idependenciaIco,
			map: map
		});

		var infoname4 = 'Estádio Independência Esplanada, Belo Horizonte - Minas Gerais, Brazil';
		var infoimage4 = 'images/idependencia.jpg"';
		bindInfoWindow(marker4, infoname4, infoimage4);
		
	
	}
	
	function bindInfoWindow(marker, name, image) {
		var infoContent = '<div style="height:250px;width:250px"><p>' +name+ '</p><div id="fmtAddrss"><img src="' +image+ '" /></div>';
		google.maps.event.addListener(marker, 'click', function() {
			infowindow.setContent(infoContent);
			infowindow.open(map, marker);
		});
	}

	function reverseGeocode() {
		return geocoder.geocode({latLng:map.getCenter()},reverseGeocodeResult);
	}
	
	function reverseGeocodeResult(results, status) {
		if(status == 'OK') {
			if(results.length == 0) {
				return 'Endereço não encontrado';
			} else {
				return results[0].formatted_address;
			}
		} else {
			return 'Erro';
		}
	}

	/* Select event handler */
	var flag = true;
	function resetIndex(selObj) {
		if(flag) selObj.selectedIndex = -1;
		flag = true;
	}
	
	function centerMap(selObj, type) {
		flag = false;
		local = getLatLong(selObj.value);
        map.setCenter(local);
		addMarkerAtCenter(type);
		
		if(markers.lenght == 2) {
			bounds.extend(new google.maps.LatLng(markers[0], markers[1]));
			map.fitBounds(bounds);
		}
		//verifica se o modo streetview está ativado e posiciona a camera do street view com uma angulacao de camera aleatoria
		if(streetView) posicionaStreetView(null,null,Math.floor(Math.random()*360));
		
	}
	
	function getLatLngText() {
		return '(' + map.getCenter().lat() +', '+ map.getCenter().lng() +')';
	}
	
	//var markerOrigem;
	//var markerDestinoParcial;
	function posicionaStreetView(idxOrigem,idxDestino,random){
		if(idxOrigem != null && idxDestino != null){
			
			/*
			markerOrigemParcial = new google.maps.Marker({
				position: arrayCoordenadasRota[idxOrigem],
				map: map,
				title:"OrigemParcial"
			});
			
			markerDestinoParcial = new google.maps.Marker({
				position: arrayCoordenadasRota[idxDestino],
				map: map,
				title:"DestinoParcial"
			});
			*/
			
			bearing = calculaAngulacaoDaCamera(idxOrigem,idxDestino);
			if(bearing == Number.MAX_VALUE){
				alert("Fim da Rota");
				panorama.setPosition(arrayCoordenadasRota[arrayCoordenadasRota.length-1]);
				return;
			}
			
			panoramaOptions = {
						position: arrayCoordenadasRota[idxOrigem],
							pov: {
							heading: bearing,
							pitch: 0,
							zoom: 1
							}
			};
		}else{
			panoramaOptions = {
						position: local,
							pov: {
							heading: random,
							pitch: 0,
							zoom: 1
							}
			};
		}
		panorama = new  google.maps.StreetViewPanorama(document.getElementById("panorama"),panoramaOptions);
		map.setStreetView(panorama);
		
		
		/*Logica para verificar se o peg será arrastado a mais de 
		13 metros do local que deveria estar. Isso deverá ser feito
		porque o google maps sempre posiciona o peg man na posição mais 
        proxima possível do local onde o peg man foi arrastado.
		O google maps faz isso porque alguns trechos nao possuem street view.	
		
		if(mudaLink){
			
			var LatLng = panorama.getPosition();
			var posicaoReal = {
				x: LatLng.ya,
				y: LatLng.wa
			};
			var posicaoOrigemParcialRota = {
				x: arrayCoordenadasRota[idxOrigem].ya,
				y: arrayCoordenadasRota[idxOrigem].wa
			};
			
			console.log(calculaDistanciaEntrePontos(posicaoReal, posicaoOrigemParcialRota));
			if(calculaDistanciaEntrePontos(posicaoReal, posicaoOrigemParcialRota) > 13){
				++idxOrigem;
				++idxDestino;
				if(verificaChegada()) return;
				posicionaStreetView(idxOrigem,idxDestino,null);
			}
		}	
		*/
		
	}
	
	function calculaAngulacaoDaCamera(idxOrigem,idxDestino){
		
		if(idxDestino >= arrayCoordenadasRota.length){
			return Number.MAX_VALUE;
		}
				
		var origem = {
			x: arrayCoordenadasRota[idxOrigem].lng(),
			y: arrayCoordenadasRota[idxOrigem].lat()
		};
		var destino = {
			x: arrayCoordenadasRota[idxDestino].lng(),
			y: arrayCoordenadasRota[idxDestino].lat()
		};
		return calculaBearing(origem,destino);
	}
	
	
	function addMarkerAtCenter(type) {

		if(type == 'fields') {
			ico = 'http://google-maps-icons.googlecode.com/files/soccer.png';
		} else {
			ico = 'http://google-maps-icons.googlecode.com/files/hotel.png';
		}
	
		var image = new google.maps.MarkerImage(ico,
			// This marker is 20 pixels wide by 32 pixels tall.
			new google.maps.Size(41, 41),
			// The origin for this image is 0,0.
			new google.maps.Point(0,0),
			// The anchor for this image is the base of the flagpole at 0,32.
			new google.maps.Point(20, 20));
	
		var latlng = map.getCenter();
		var marker = new google.maps.Marker({
			position: latlng,
			icon: image,
			map: map
		});
	
		var geoCoderAddress = '';
	
		geocoder.geocode({'latLng': latlng}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				if (results[1]) {

					geoCoderAddress = results[0].formatted_address;
					
				} else {
					geoCoderAddress = 'No address found';
				}
			} else {
				geoCoderAddress = 'Geocoder failed due to: ' + status;
			}
			
			var infoContent = '<div style="height:250px;width:250px"><p>' + geoCoderAddress + '</p><div id="fmtAddrss"><img src="http://cbk0.google.com/cbk?output=thumbnail&w=190&h=168&ll=' + map.getCenter().lat() + ',' + map.getCenter().lng() + '" /></div>';
			
			infowindow.setContent(infoContent);
			
			google.maps.event.addListener(marker, 'click', function() {
				infowindow.open(map,marker);
			});
			marker.info = infowindow;
			markers.push(marker);
		});
	}
	
	/* Clear Markers */
	function clearMarkers() {
		// clear all markers
		$(markers).each(function () {
			this.info.close();
			this.setMap(null);
		});
		// reset the eviction array 
		markers = [];
	}
	
	function clearHotelsMarkers() {
		$(allHotelsMarkers).each(function () {
			this.info.close();
			this.setMap(null);
		});
		// reset the eviction array 
		allHotelsMarkers = [];
	}
	
	
	function getLatLong(value, convert) {
		if(convert)
			var coordinates = value.replace(/,/g, ".").split(';');
		else
			var coordinates = value.split(';');
		var latitude = coordinates[0];
		var longitude = coordinates[1];
		return new google.maps.LatLng(latitude, longitude)
	}

	var polyline = new google.maps.Polyline({
		path: [],
		strokeColor: '#0000FF',
		strokeWeight: 5
	});
	
	/* Calc route */
	function calcRoute(way) {
		if(way == 'leftToRight') {
			var start = getLatLong(document.getElementById("hotels_list").value);
			var end = getLatLong(document.getElementById("fields_list").value);
		} else {
			var start = getLatLong(document.getElementById("fields_list").value);
			var end = getLatLong(document.getElementById("hotels_list").value);
		}

		var request = {
			origin: start,
			destination: end,
			optimizeWaypoints: true,
			travelMode: google.maps.DirectionsTravelMode.DRIVING,
			unitSystem: google.maps.DirectionsUnitSystem.METRIC //,
		};

		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {

				clearMarkers();
				if(allHotels)
					toggleAllHotels();
				directionsRenderer.setDirections(response);
				
				centroDaRota = response.routes[0].bounds.getCenter();
				arrayCoordenadasRota = response.routes[0].overview_path;
				enderecoInicial = response.routes[0].legs[0].start_address;
				enderecoFinal = response.routes[0].legs[0].end_address;
				
				
				//verefica se o modo street view está ativado, se ele estiver ativado a camera do streetview é posicionada corretamente.
				if(!streetView){
					$('#summaryPanel').append('<div><button id="playRouteBtn" class="button gray top-right" onclick="showStreetView()">Show Street View &raquo;</button></div>');
					$('#summaryPanel').css('display', 'block');
					streetView = true;
				}else posicionaStreetView(0,1,null);
			} else {
				alert('Error: ' + status);
			}
		});
	}
	
	var polylineLatLngs = [];
	function traceRoute() {
		var path = directionsRenderer.getDirections().routes[0].overview_path;
	}
	
	
	function showStreetView() {
		
		
		//redimensiona os tamanhos das divs do street view e do mapa	
		document.getElementById('map_canvas').style.width = '50%';
		document.getElementById('panorama').style.height = '100%';
		document.getElementById('panorama').style.width = '50%';
    	document.getElementById('map_wrap').style.position = 'relative';
		document.getElementById('map_canvas').style.position = 'absolute';
		document.getElementById('map_canvas').style.top = '0px';
		document.getElementById('map_canvas').style.left = '0px';
		document.getElementById('panorama').style.position = 'absolute';
		document.getElementById('panorama').style.top = '0px';
		document.getElementById('panorama').style.right = '0px';
		
		
		google.maps.event.trigger(map, "resize");
		map.setCenter(centroDaRota);
		
		//calculo da angulação entre os dois primeiros pontos da rota
		posicionaStreetView(idxOrigem,idxDestino,null);
	
		//muda a action function do botao
		document.getElementById("playRouteBtn").attributes[0].nodeValue = 'playRoute()';
		//muda o texto do botao
		$("#playRouteBtn").html("Play Route &raquo;");
		
		//document.getElementById("playRouteBtn").outerHTML = '<BUTTON id=playRouteBtn class="button gray top-right" onclick="PlayRoute()" type="submit">Play Route &raquo;</BUTTON>';
		
		botaoPlayRoute = false;
	}
	
	function playRoute() {
		//mudaLink = false;
				
		
				
				
		if(verificaChegada()) return;
		
		//calculo da angulação entre os dois primeiros pontos da rota e posicona a camera corretamente.
		
		posicionaStreetView(idxOrigem,idxDestino,null);
					
		var LatLng = panorama.getPosition();
		var localizacaoAtual = {
			x: LatLng.lng(),
			y: LatLng.lat()
		};
		var destinoParcial = {
			x: arrayCoordenadasRota[idxDestino].lng(),
			y: arrayCoordenadasRota[idxDestino].lat()
		};
		
		var distanciaAtual = calculaDistanciaEntrePontos(localizacaoAtual, destinoParcial);
		
		//alert("distancia anterior: " + distanciaAnterior);
		//alert("distancia atual: " + distanciaAtual);	
		
		
		if(primeiraVez) {
			var origemParcial = {
				x: arrayCoordenadasRota[idxOrigem].lng(),
				y: arrayCoordenadasRota[idxOrigem].lat()
			};
			var distanciaAtualOrigem = calculaDistanciaEntrePontos(localizacaoAtual, origemParcial);
			
			primeiraVez = false;
			
			if(distanciaAtualOrigem > 5){
				//alert("Posicao inicial da reta incorreta.");
				++idxOrigem;
				++idxDestino;
				if(verificaChegada()) return;
				posicionaStreetView(idxOrigem,idxDestino,null);
				distanciaAtual = 0;
				distanciaAnterior = Number.MAX_VALUE;
				primeiraVez = false;
				return;
			}
		}
				
		
		if((distanciaAtual > distanciaAnterior)){
			//alert("Erro ocasionado pelo calculo incorreto da rota fornecido pelo google maps.\nRecalculando rota...");
			++idxOrigem;
			++idxDestino;
			if(verificaChegada()) return;
			posicionaStreetView(idxOrigem,idxDestino,null);
			distanciaAtual = 0;
			distanciaAnterior = Number.MAX_VALUE;
			return;
		}else mudaVertice = false;	
		
		if(verificaChegada()) return;
		if(distanciaAtual < 10 && verificaProximaAngulacao(panorama)){
			//alert('cheguei..atualizando angulacao');
			++idxOrigem;
			++idxDestino;
			if(verificaChegada()) return;
			bearing = calculaAngulacaoDaCamera(idxOrigem,idxDestino);
			panorama.setPov({
				heading: bearing,
				pitch: 0,
				zoom: 1
			});
			mudaVertice = true;
			distanciaAnterior = Number.MAX_VALUE;
		}
				
		if(botaoPlayRoute){
			botaoPlayRoute = false;
			$("#playRouteBtn").html("Play Route &raquo;");
		}else {
			botaoPlayRoute = true;
			$("#playRouteBtn").html("Stop Route &raquo;");
		}
		/*Caso que nao há street view para esse trecho da rota. 
		Sendo assim nenhum link nos levará para o caminho correto da rota,
		irremos entao pular para o proximo trecho da rota.
		*/
		
		if(verificaChegada()) return;	
		panorama.setPano(calculaDeltaHeading(panorama));
		
		if(menorDeltaHeading > 40){
			//alert('nao ha links do street view');
			idxOrigem += 2;
			idxDestino += 2;
			if(verificaChegada()) return;
			//mudaLink = true;
			posicionaStreetView(idxOrigem,idxDestino,null);
			
			//alert("idxOrigem: " + idxOrigem);
			//alert("idxDestino: " + idxDestino);
			
			distanciaAtual = 0;
			distanciaAnterior = Number.MAX_VALUE;
			return;
	    }
		
		
		
		if(!mudaVertice){
			distanciaAnterior = distanciaAtual;
			mudaVertice = false;
		}
	}
				
			
	function verificaChegada(){
		if(idxDestino >= arrayCoordenadasRota.length){
			alert("Fim da Rota");
			panorama.setPosition(arrayCoordenadasRota[arrayCoordenadasRota.length-1]);
			return true;
		}	
		else return false;
	}
	
	
	function calculaDeltaHeading(panorama){
		var links =  panorama.getLinks();
		menorDeltaHeading = Number.MAX_VALUE;
		var panoId;
		for (var i in links) {
			if(Math.abs(bearing - links[i].heading) < menorDeltaHeading){
				menorDeltaHeading = Math.abs(bearing - links[i].heading);
				panoId = links[i].pano;
			}	
		}
		return panoId;
	}
	
	function verificaProximaAngulacao(panorama){
		var origem = {
			x: arrayCoordenadasRota[idxOrigem+1].lng(),
			y: arrayCoordenadasRota[idxOrigem+1].lat()
		};
		var destino = {
			x: arrayCoordenadasRota[idxDestino+1].lng(),
			y: arrayCoordenadasRota[idxDestino+1].lat()
		};
	    var proximoBearing = calculaBearing(origem,destino);
		var links =  panorama.getLinks();
		var menorDeltaHeading = Number.MAX_VALUE;
		for (var i in links) {
			if(Math.abs(proximoBearing - links[i].heading) < menorDeltaHeading){
				menorDeltaHeading = Math.abs(proximoBearing - links[i].heading);
			}	
		}
		if(menorDeltaHeading < 20) return true;
		else return false;
	}
	
	function pause(milliseconds) {
		var dt = new Date();
		while ((new Date()) - dt <= milliseconds) { /* Do nothing */ }
	}

	/**
    * Converte graus para radianos
    */
    function toRad(degree) {
      return degree * Math.PI / 180;
    }

	/**
	* Converte radianos para graus 
    */
    function toBrng(radian) {
      return (toDeg(radian)+360) % 360;
    }

	/**
    * Converte angulo em radianos para graus(signed)
    */
    function toDeg(radian) {
		return radian * 180 / Math.PI;
    }
	

	function calculaDistanciaEntrePontos(p1, p2){
		//latitude eixo y ; longitude eixo x
		var R = 6371000; // raio aproximado da terra em metros.
		var dLat = toRad(p2.y - p1.y);
		var dLon = toRad(p2.x - p1.x); 
		var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(toRad(p1.y)) * Math.cos(toRad(p2.y)) * 
        Math.sin(dLon/2) * Math.sin(dLon/2); 
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		var d = R * c;
		return d;
	}
	
	/**
    * Calcula o "bearing" entre dois pontos.
	* Seria o angulo da camera para que um observador	
	* no ponto de origem esteja vendo o ponto de destino
	* na sua frente.Em outras palavras seria o angulo formado
 	* entre esses dois pontos com o eixo latitudinal da terra(north-south line).
	* OBS: latitude  eixo y
	*      longitude eixo x
    * @param {ponto} origin       ponto de origem do observador
    * @param {ponto} destination  ponto onde o observador está focando seu olhar.
    * @return {number}
    */
    function calculaBearing(origin, destination) {
      var lat1 = toRad(origin.y);
      var lat2 = toRad(destination.y);
      var dLon = toRad(destination.x - origin.x);
      var y = Math.sin(dLon) * Math.cos(lat2);
      var x = Math.cos(lat1)*Math.sin(lat2) -
              Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
	  return toBrng(Math.atan2(y, x));
    }
	
	/* Calculate Distance */
	function computeTotalDistance(result) {
		var total = 0;
		var myroute = result.routes[0];
		for (i = 0; i < myroute.legs.length; i++) {
			total += myroute.legs[i].distance.value;
		}
		total = total / 1000;
		return total + " km";
	} 
	
	function saveRoute(){
		if(arrayCoordenadasRota.length == 0)
			alert('Escolha uma rota para ser salva.');
		else {
			var kml =  '<kml xmlns=\"http://www.opengis.net/kml/2.2\">'+
						 '<Document>'+
						 //estilo default para rotas no googles maps(linha azul semi-opaca)
						 '<Style id=\"Path\">' +
						 '<LineStyle><color>88FF0000</color><width>5</width></LineStyle>' +
						 '</Style>'+
						 
						 //imagem default para placemarkers no google maps(ponto inicial e final da rota)
						 '<Style id=\"markerstyle\">'+
						 '<IconStyle><Icon><href>http://maps.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png</href>'+
						 '</Icon></IconStyle>'+
						 '</Style>'+
																		 
						 //imagem para placemarkers de aeroportos
						 '<Style id=\"airport\">'+
						 '<IconStyle><Icon><href>http://localhost/rotasdacopa/images/airport.png</href>'+
						 '</Icon></IconStyle>'+
						 '</Style>'+
						
						 //imagem para o pacemarker do mineirao
						 '<Style id=\"mineirao\">'+
						 '<IconStyle><Icon><href>http://localhost/rotasdacopa/images/stadium.gif</href>'+
						 '</Icon></IconStyle>'+
						 '</Style>'+	
												 
						 //imagem para o placemarker do estadio independencia
						 '<Style id=\"independencia\">'+
						 '<IconStyle><Icon><href>http://localhost/rotasdacopa/images/arena2.png</href>'+
						 '</Icon></IconStyle>'+
						 '</Style>'+
						
						 //imagem para placemarkers de hoteis
						 '<Style id=\"hotel\">'+
						 '<IconStyle><Icon><href>http://google-maps-icons.googlecode.com/files/hotel.png</href>'+
						 '</Icon></IconStyle>'+
						 '</Style>'+
						
						//Placemarker que contem o estadio mineirao
						 '<Placemark>'+
						 '<description><![CDATA[<html><body>Estádio Governador Magalhães Pinto - Mineirão<br/>São Luís, Belo Horizonte - MG, Brazil <br/> <img src="http://localhost/rotasdacopa/images/mineirao.jpg" /></body></html>]]></description>'+
						 '<styleUrl>#mineirao</styleUrl>'+
					     '<Point>'+
                         '<coordinates>-43.971092,-19.866386,0'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+
								
						 //Placemarker que contem o estadio independencia
						 '<Placemark>'+
						 '<description><![CDATA[<html><body>Estádio Independência Esplanada, Belo Horizonte - Minas Gerais, Brazil <br/> <img src="http://localhost/rotasdacopa/idependencia.jpg" /></body></html>]]></description>'+
						 '<styleUrl>#independencia</styleUrl>'+
					     '<Point>'+
                         '<coordinates>-43.90871,-19.90896,0'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+

						 //Placemarker que contem o aeroporto de confins
						 '<Placemark>'+
						 '<description><![CDATA[<html><body>Tancredo Neves International Airport (CNF)<br />Confins - Minas Gerais, Brazil<br/> <img src="http://localhost/rotasdacopa/confins.jpg" /></body></html>]]></description>'+
						 '<styleUrl>#airport</styleUrl>'+
					     '<Point>'+
                         '<coordinates>-43.96780,-19.63370,0'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+

						 //Placemarker que contem o aeroporto da pampulha
						 '<Placemark>'+
						 '<description><![CDATA[<html><body>Aeroporto da Pampulha - Carlos Drummond de Andrade Airport (PLU)<br />Aeroporto, Belo Horizonte - Minas Gerais, Brazil<br/> <img src="http://localhost/rotasdacopa/pampulha.jpg" /></body></html>]]></description>'+
						 '<styleUrl>#airport</styleUrl>'+
					     '<Point>'+
                         '<coordinates>-43.94887,-19.84947,0'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+		
						 
						 //Adiciona todos os Placemarkers de hoteis
						 <?php foreach($hotels as $hotel) { ?>
						 '<Placemark>'+
						 '<description><![CDATA[<html><body>' + geocodifica(new google.maps.LatLng(<?php echo $hotel->lat ?>,<?php echo $hotel->lng ?>)) +'</body></html>]]></description>'+
						 '<styleUrl>#hotel</styleUrl>'+
					     '<Point>'+
                         '<coordinates> <?php echo $hotel->lng,",",$hotel->lat ?> ,0'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+
						 
						 <?php } ?>
						
						 //Placemarker que contem todas as coordenadas da rota
				         '<Placemark>'+
						 '<styleUrl>#Path</styleUrl>'+
						 '<LineString>'+
						 '<tessellate>1</tessellate>'+
						 '<altitudeMode>clampToGround</altitudeMode>'+
						 '<coordinates>';
						 
						 for(var i = 0; i < arrayCoordenadasRota.length; i++)
								kml +=  arrayCoordenadasRota[i].lat() + ',' + arrayCoordenadasRota[i].lng() + ',0\n'; 

                kml += '</coordinates>'+
						 '</LineString>'+
						 '</Placemark>'+
						 
						 //Placemarker que contem o ponto inicial da rota
						 '<Placemark>'+
						 '<description>'+enderecoInicial+'</description>'+
						 '<styleUrl>#markerstyle</styleUrl>'+
						 '<Point>'+
						 '<coordinates>' + arrayCoordenadasRota[0].lat() + ',' + arrayCoordenadasRota[0].lng() + ',0\n'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+
						 
						 //Placemarker que contem o ponto final da rota
						 '<Placemark>'+
						 '<description>'+enderecoFinal+'</description>'+
						 '<styleUrl>#markerstyle</styleUrl>'+
					     '<Point>'+
                         '<coordinates>' + arrayCoordenadasRota[arrayCoordenadasRota.length-1].lat() + ',' + arrayCoordenadasRota[arrayCoordenadasRota.length-1].lng() + ',0\n'+
						 '</coordinates>'+
						 '</Point>'+
						 '</Placemark>'+
					     '</Document>'+
						 '</kml>';
						
						document.getElementById("iframe").contentDocument.getElementById("download").value = escape(kml);
					    document.getElementById("iframe").contentDocument.getElementById("MyForm").submit();
					
										   						
		}
	}
	
	
	function geocodifica(latlng) {
		var geoCoderAddress = "";
		geocoder.geocode({'latLng': latlng}, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						if (results[1]) 
							geoCoderAddress = results[0].formatted_address;
						else geoCoderAddress = 'No address found';
					} else geoCoderAddress = 'Geocoder failed due to: ' + status;
		});
	return '<div style="height:250px;width:250px"><p>' + geoCoderAddress + '</p><div id="fmtAddrss"><img src="http://cbk0.google.com/cbk?output=thumbnail&w=190&h=168&ll=' + map.getCenter().lat() + ',' + map.getCenter().lng() + '" /></div>';
	}
	
	


	var allHotels = false;
	function toggleAllHotels() {
		if(!allHotels) {
			allHotels = true;
			$.each($("#hotels_list"), function(index, value) {
				$('option', this).each(function(index, option) {
					var location = getLatLong(option.value);
					
					ico = 'http://google-maps-icons.googlecode.com/files/hotel.png';
					
					var image = new google.maps.MarkerImage(ico,
						// This marker is 20 pixels wide by 32 pixels tall.
						new google.maps.Size(41, 41),
						// The origin for this image is 0,0.
						new google.maps.Point(0,0),
						// The anchor for this image is the base of the flagpole at 0,32.
						new google.maps.Point(20, 20));
					
					var marker = new google.maps.Marker({
						position: location,
						icon: image,
						map: map
					});

					var geoCoderAddress = '';
					var infowindow = new google.maps.InfoWindow();
				
					var latlng = location;
					
					geocoder.geocode({'latLng': latlng}, function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							if (results[1]) {
								geoCoderAddress = results[0].formatted_address;
							} else {
								geoCoderAddress = 'No address found';
							}
						} else {
							geoCoderAddress = 'Geocoder failed due to: ' + status;
						}
						
						var infoContent = '<div style="height:250px;width:250px"><p>' + geoCoderAddress + '</p><div id="fmtAddrss"><img src="http://cbk0.google.com/cbk?output=thumbnail&w=190&h=168&ll=' + location.lat() + ',' + location.lng() + '" /></div>';
						
						infowindow.setContent(infoContent);
						
						google.maps.event.addListener(marker, 'click', function() {
							infowindow.open(map,marker);
						});
						
						marker.info = infowindow;
						allHotelsMarkers.push(marker);
					});

					bounds.extend(location);
				});
			});
			
			$("#allHotelsBtn").html("Hide Hotels &raquo;");
		} else {
			allHotels = false;
			clearHotelsMarkers();
			$("#allHotelsBtn").html("Show Hotels &raquo;");
		}
		if(allHotels)
			map.fitBounds(bounds);
	} 
	
	function printMap() {
		
		var baseUrl = "http://maps.google.com/maps/api/staticmap?";

		var params = [];
		
		var polyParams = "color:0x0000FF80,weight:5|";

		params.push("path=" + polyParams + polylineLatLngs.join("|"));
		params.push("size=600x600");
	
		baseUrl += params.join("&");
		
		var extraParams = [];
		extraParams.push("center=" + map.getCenter().lat().toFixed(6) + "," + map.getCenter().lng().toFixed(6));
		extraParams.push("zoom=" + map.getZoom());
		var url = baseUrl + "&" + extraParams.join("&") + "&sensor=false";
	}
	
	(new Image()).src = "resources/corner-inactive.png";
	(new Image()).src = "resources/corner.png";
	(new Image()).src = "resources/resizer-inactive.png";
	(new Image()).src = "resources/resizer.png";
	(new Image()).src = "resources/vertical-button-active.png";
	(new Image()).src = "resources/vertical-button-background-active.png";
	(new Image()).src = "resources/vertical-button-background-hover.png";
	(new Image()).src = "resources/vertical-button-background-inactive.png";
	(new Image()).src = "resources/vertical-button-background.png";
	(new Image()).src = "resources/vertical-button-hover.png";
	(new Image()).src = "resources/vertical-button-inactive.png";
	(new Image()).src = "resources/vertical-button.png";
	(new Image()).src = "resources/vertical-decrement-arrow.png";
	(new Image()).src = "resources/vertical-increment-arrow.png";
	(new Image()).src = "resources/vertical-thumb-active.png";
	(new Image()).src = "resources/vertical-thumb-hover.png";
	(new Image()).src = "resources/vertical-thumb-inactive.png";
	(new Image()).src = "resources/vertical-thumb.png";
	(new Image()).src = "resources/vertical-track-active.png";
	(new Image()).src = "resources/vertical-track-disabled.png";
	(new Image()).src = "resources/vertical-track-hover.png";
	(new Image()).src = "resources/vertical-track.png";
</script>
</head>
<body onload="initialize()">
  <hr id="header_stripe"/>
  <div id="page_container">
  <a class="pbh" href="http://www.pbh.gov.br"><img src="images/pbh.png" alt="Prefeitura de Belo Horizonte" /></a>
  
  <button id="routeBtn" class="button gray top-right" onclick="saveRoute()">Save Route &raquo;</button>
  <button id="allHotelsBtn" class="button gray top-right" onclick="toggleAllHotels()">Show Hotels &raquo;</button>
  
  <div id="toppanel">
    <div id="panel">
		<div id="panel_contents"></div>
		<h1>World Cup 2014 in Belo Horizonte</h1>
		<label class="lblHoteis" for="hotels_list">Hotels</label>
		<div class="border_hotels">
			<select name="hotels" id="hotels_list" multiple="multiple" onchange="centerMap(this, 'hotel')" onclick="resetIndex(this)">
				<?php foreach($hotels as $hotel) { ?>
				<option name="fields_latlong" value="<?php echo $hotel->getLatLng(); ?>" ><?php echo $hotel->name; ?></option>
				<?php } unset($hotel); ?>
			</select>
		</div>
		<div class="directions">
			<img class="leftToRight" src="images/leftToRight.png" alt="Rota do Hotel para Centro de Treinamento" onclick="calcRoute('leftToRight')" />
			<img class="rightToLeft" src="images/rightToLeft.png" alt="Rota do Centro de Treinamento para o Hotel" onclick="calcRoute('rightToLeft')" />
		</div>
		<label class="lblCts" for="fields_list">Trainning Centers</label>
		<div class="border_fields" id="fields">
			<select name="fields" id="fields_list" multiple="multiple" onchange="centerMap(this, 'fields')" onclick="resetIndex(this)">
				<?php foreach($cts as $ct) { ?>
				<option name="fields_latlong" value="<?php echo $ct->getLatLng(); ?>" ><?php echo $ct->name; ?></option>
				<?php } ?>
			</select>
		</div>
    </div>
    <div class="panel_button" style="display: visible;"><img src="images/expand.png"  alt="expand"/><a href="#">Routes</a></div>
    <div class="panel_button" id="hide_button" style="display: none;"><img src="images/collapse.png" alt="collapse" /><a href="#">Hide</a></div>
  </div>
  <div id="content">
  <div id="map_wrap">
  	<div id="map_canvas"></div>
	<div id="panorama"></div>
	<div id="summaryPanel">
		<div class="transparency movePanel"></div>
		<div id="directionsPanel"></div>
	</div>  
  </div>
  </div>
  <div id="footer">
	<p>Desenvolvido por Geoprocessamento Coorporativo - Prodabel, Prefeitura de Belo Horizonte &copy; 2010</p>
  </div>
</div>

<div id="panorama2"></div>

<iframe name="iframe" id="iframe" src ="iframe.html" style="display:none;"></iframe> 
</body>
</html>