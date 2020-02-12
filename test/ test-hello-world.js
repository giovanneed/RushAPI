var expect  = require('chai').expect;
var request = require('request');

var requestPromise = require('request-promise');


describe("Test Rush  API", function() {

	it("Tests Hello World", function(done) {
			var options = {
           					method: 'GET',
            				uri: 'http://localhost:3000/helloworld',
            				json: true, // Automatically stringifies the body to JSON
            				resolveWithFullResponse: true
        	};

        	requestPromise(options)
        		.then(function (response,body) {
            			expect(response.body).to.equal('Hello World');
	           			done();

        		})
        		.catch(function (err) {
            			done();

        	});


	});
 
 });