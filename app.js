const Express = require("express");
const Mongoose = require("mongoose");
const BodyParser = require("body-parser");
const multer = require('multer');
var fs = require('fs');
let jwt = require('jsonwebtoken');
let config = require('./config');
let middleware = require('./middleware');

var requestAPI = require('request');

const https = require('https');

const util = require('util')





class HandlerGenerator {
    login (req, res) {
      let username = req.body.username;
      let password = req.body.password;
      // For the given username fetch user from DB
      let mockedUsername = 'admin';
      let mockedPassword = 'password';

   

      UserModel.findOne({ username: username}, function(err, user) {
        //if (err) throw err;
        console.log("findOne");
        

        if (username && password) {

          console.log("username && password");
          if ( password === user.password) {
            let token = jwt.sign({username: username},
              config.secret,
              { expiresIn: '24h' // expires in 24 hours
              }
            );
            // return the JWT token for the future API calls
            res.json({
              success: true,
              message: 'Authentication successful!',
              token: token
            });
          } else {
            console.log("error");

            res.json({
              success: false,
              message: 'Authentication error!'
            });

            /*res.send(403).json({
              success: false,
              message: 'Incorrect username or password'
            });*/
          }
        } else {
          console.log("error2");

          res.send(400).json({
            success: false,
            message: 'Authentication failed! Please check the request'
          });
        }
    
        /*// test a matching password
        user.comparePassword('Password123', function(err, isMatch) {
            if (err) throw err;
            console.log('Password123:', isMatch); // -> Password123: true
        });
    
        // test a failing password
        user.comparePassword('123Password', function(err, isMatch) {
            if (err) throw err;
            console.log('123Password:', isMatch); // -> 123Password: false
        });*/
    });

      
      
    }
    index (req, res) {
      console.log("error3");

      res.json({
        success: true,
        message: 'Index page'
      });
    }
  }


let handlers = new HandlerGenerator();

var app = Express();
var Schema = Mongoose.Schema

app.use(BodyParser.json({limit: '50mb'}));
app.use(BodyParser.urlencoded({limit: '50mb', extended: true }));

app.post('/login', handlers.login);
app.get('/', middleware.checkToken, handlers.index);


app.use("/pictures", Express.static(__dirname + '/pictures'));



app.listen(3000, () => {
    console.log("Listening at :3000...");
});


//m2
Mongoose.connect("mongodb://localhost/rush03");

const UserModel = Mongoose.model("user", {
  name: String,
  username: String,
  password: String,

});

const RestaurantModel = Mongoose.model("restaurant", {
    name: String,
    address: String,
    latitude: Number,
    longitude: Number,
    items: [{ type: Schema.Types.ObjectId, ref: 'MenuItemModel'}]

});


const MenuItemModel = Mongoose.model("item", {
    name: String,
    image: String,
    description: String,
    category: String,
    available: Boolean,
    size: Boolean,
    price: [{ type: Schema.Types.ObjectId, ref: 'MenuPriceModel'}]

});

const MenuPriceModel = Mongoose.model("price", {
    size: String,
    price: Number,
    cal: String

});



const ImageModel = Mongoose.model("image", {
    src: String

});

const ProductModel = Mongoose.model("product", {
    title: String,
    body_html: String,
    images: [{ type: Schema.Types.ObjectId, ref: 'ImageModel'}]

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

  console.log("CORS configuration");
   res.header("Access-Control-Allow-Origin", "*");
   res.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
   res.header("Access-Control-Allow-Headers", "Origin, Access-Control-Allow-Headers, X-Requested-With, Content-Type, Accept, Authorization");

  next();
 });
 


app.post("/shopify/products",async (request, response) => {
  try {


    console.log("post /shopify/products with request");


     var product = request.body; 
    
    console.log(util.inspect(product, {showHidden: false, depth: null}))



    var URL = "https://45e07faf7bc41c6e83182f3af6a62a17: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@centennial-capstone-store.myshopify.com/admin/api/2020-04/products.json"

    requestAPI.post(URL,
    { json: product,

    },
      function (errorShopify, responseShopify, bodyShopify) {

          console.log(bodyShopify)

          if (!errorShopify && responseShopify.statusCode == 200) {
              console.log(body);
          }

          response.send(responseShopify);

          console.log("wrong " + responseShopify)
    }
    );


    //response.send("post /shopify/products ");

  } catch (error) {
      console.log("super wrong " + error)

      response.status(500).send(error);
  }
});




app.get("/shopify/products",async (request, response) => {
  try {


    console.log("/shopify/products");

    https.get('https://45e07faf7bc41c6e83182f3af6a62a17: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@centennial-capstone-store.myshopify.com/admin/api/2020-04/products.json', (resp) => {
        let data = '';

        // A chunk of data has been recieved.
        resp.on('data', (chunk) => {
            data += chunk;
        });

        // The whole response has been received. Print out the result.
        resp.on('end', () => {
              console.log(JSON.parse(data).explanation);
              response.send(JSON.parse(data));

        });

     }).on("error", (err) => {
          console.log("Error: " + err.message);
     });

   

  } catch (error) {
      response.status(500).send(error);
  }
});

app.get("/shopify/orders",async (request, response) => {
  try {


    console.log("/shopify/order");

    https.get('https://45e07faf7bc41c6e83182f3af6a62a17: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@centennial-capstone-store.myshopify.com/admin/api/2020-04/orders.json', (resp) => {
        let data = '';

        // A chunk of data has been recieved.
        resp.on('data', (chunk) => {
            data += chunk;
        });

        // The whole response has been received. Print out the result.
        resp.on('end', () => {
              console.log(JSON.parse(data).explanation);
              response.send(JSON.parse(data));

        });

     }).on("error", (err) => {
          console.log("Error: " + err.message);
     });

   

  } catch (error) {
      response.status(500).send(error);
  }
});

app.post("/shopify/orders/:id",async (request, response) => {
  try {


    console.log("post /shopify/orders/:id with request");
    console.log("update order: " + request.params.id);


    var orderID = request.params.id
    var orderUpdate = request.body; 
    
    console.log(util.inspect(orderUpdate, {showHidden: false, depth: null}))



    var URL = "https://45e07faf7bc41c6e83182f3af6a62a17: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@centennial-capstone-store.myshopify.com/admin/api/2020-04/orders/"+ orderID +".json"

    requestAPI.put(URL,
    { json: orderUpdate,

    },
      function (errorShopify, responseShopify, bodyShopify) {

          console.log(bodyShopify)

          if (!errorShopify && responseShopify.statusCode == 200) {
              console.log("success");
          }

          response.send(responseShopify);

          console.log("wrong " + responseShopify)
          console.log(util.inspect(responseShopify.statusCode, {showHidden: false, depth: null}))

    }
    );


   // response.send("post /shopify/orders/:id with request");

  } catch (error) {
      console.log("super wrong " + error)

      response.status(500).send(error);
  }
});













