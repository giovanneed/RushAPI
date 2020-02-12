var expect  = require('chai').expect;
var request = require('request');
var fs = require('fs');

var requestPromise = require('request-promise');


describe("mockup", function() {



     var newItemID;



    it("mockup Tests Post Request create New Item", function(done) {


         var newItem = { 

            "name":"Item 01 test",
            "description":"fake description",
            "category" : "Drink", 
            "available" : true
        };


        var options = {
            method: 'POST',
            uri: 'http://localhost:3000/items/',
            body: newItem,
            json: true, // Automatically stringifies the body to JSON
            resolveWithFullResponse: true
        };


        requestPromise(options)
        .then(function (response,body) {
            //newPatientEdwardMurphyID = response.body._id;
            console.log("Item was created with id:" + response);
            done();
            expect(response.statusCode).to.equal(200);


        })
        .catch(function (err) {
            console.log(err);

            done();

        });

    });





    


   
	


 });