const Express = require("express");
const Mongoose = require("mongoose");
const BodyParser = require("body-parser");
const multer = require('multer');
var fs = require('fs');

var request = require('request');


var app = Express();
var Schema = Mongoose.Schema

app.use(BodyParser.json({limit: '50mb'}));
app.use(BodyParser.urlencoded({limit: '50mb', extended: true }));


app.use("/pictures", Express.static(__dirname + '/pictures'));



app.listen(3000, () => {
    console.log("Listening at :3000...");
});


//m2
Mongoose.connect("mongodb://localhost/rush02");



const RestaurantModel = Mongoose.model("restaurant", {
    name: String,
    address: String,
    latitude: Number,
    longitude: Number,
    items: [{ type: Schema.Types.ObjectId, ref: 'MenuItemModel'}]

});


const MenuItemModel = Mongoose.model("item", {
    name: String,
    description: String,
    category: String,
    available: Boolean

});




// SET STORAGE
var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'pictures')
  },
  filename: function (req, file, cb) {
    cb(null, req.params.id + '.png')
  }
})
 
var upload = multer({ storage: storage })


app.use(function(req, res, next) {
   res.header("Access-Control-Allow-Origin", "*");
   res.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
   res.header("Access-Control-Allow-Headers", "Origin, Access-Control-Allow-Headers, X-Requested-With, Content-Type, Accept, Authorization");

  next();
 });


app.get("/helloworld", async (request, response) => {
    try {

        console.log("request received");
        response.send('Hello World');
    } catch (error) {
        response.status(500).send(error);
    }
});


/*Create new restaurant */

app.post("/restaurants", async (request, response) => {


    try {

        console.log("Create new restaurant");

        var restaurant = new RestaurantModel(request.body);
        var result = await restaurant.save();
        response.send(result);
    } catch (error) {
        response.status(500).send(error);
    }
});


app.get("/restaurants", async (request, response) => {
    try {

        console.log("Get all restaurants");

        var restaurants = await RestaurantModel.find().exec();
        
        response.send(restaurants);
    } catch (error) {
        response.status(500).send(error);
    }
});


/*Create new report for patient with nurse ID */


app.post("/restaurants/:id/items", async (request, response) => {

    try {

        console.log("/restaurants/:id/items");

       
        var item = new MenuItemModel(request.body);


        console.log(request.body);


        var restaurant = await RestaurantModel.findById(request.params.id).exec();



        
        restaurant.items.push(item);
       

        var result = await item.save();
        result = await restaurant.save();

      

        response.send(item);

    } catch (error) {

        console.log("Error");
        console.log(error);


        response.status(500).send(error);
    }
});


/*Create new item */


app.post("/items", async (request, response) => {


    try {

        console.log("Create new item");

        var item = new MenuItemModel(request.body);
        var result = await item.save();
        response.send(result);
    } catch (error) {
        response.status(500).send(error);
    }
});


app.get("/items/:id", async (request, response) => {


    try {

        console.log("Create new item");

        var item = new MenuItemModel(request.body);
        var result = await item.save();
        response.send(result);
    } catch (error) {
        response.status(500).send(error);
    }
});



app.get("/items", async (request, response) => {
    try {

        console.log("/items/")


        var items = await MenuItemModel.find().exec();
        
        response.send(items);
    } catch (error) {
        response.status(500).send(error);
    }
});








