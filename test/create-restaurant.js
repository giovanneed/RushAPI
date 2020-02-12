var expect  = require('chai').expect;
var request = require('request');
var fs = require('fs');

var requestPromise = require('request-promise');


describe("mockup", function() {



     var newRestaurantID;




    it("mockup Tests Post Request create New Restaurant", function(done) {


         var newRestaurant = { 

            "name":"Restaurant 01 test",
            "address":"fake address",
            "latitude" : 43.785309, 
            "longitude" :  -79.226384
        };


        var options = {
            method: 'POST',
            uri: 'http://localhost:3000/restaurants/',
            body: newRestaurant,
            json: true, // Automatically stringifies the body to JSON
            resolveWithFullResponse: true
        };





        requestPromise(options)
        .then(function (response,body) {
            //newPatientEdwardMurphyID = response.body._id;
            console.log("Restaurant was created with id:" + response);
            done();
            expect(response.statusCode).to.equal(200);


        })
        .catch(function (err) {
            console.log(err);

            done();

        });

    });





    


   
	


 });