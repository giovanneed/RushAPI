
var initialLocation;
var browserSupportFlag =  new Boolean();



function verificageo(){

	inicial = "mudou";

}


function retornalatlng(){
 // Try W3C Geolocation (Preferred)
  if(navigator.geolocation) {
    browserSupportFlag = true;
    navigator.geolocation.getCurrentPosition(function(position) {
      initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
	 //alert("Dentro da funcao: " + inicial );
      inicial = initialLocation;
	  //map.setCenter(initialLocation);
    }, function() {
      handleNoGeolocation(browserSupportFlag);
    });
  // Try Google Gears Geolocation
  }else {
    browserSupportFlag = false;
    handleNoGeolocation(browserSupportFlag);
  }
  
  
  return initialLocation;
}

function handleNoGeolocation(errorFlag) {
    if (errorFlag == true) {
     
    } else {
     
    }
    
	
}





