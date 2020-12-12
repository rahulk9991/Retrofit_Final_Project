var mysql = require("mysql");
var express = require("express");
var bodyParser = require("body-parser");
const http = require('http');

// connection to mysql
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "productscart",
});




var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.listen(3000, () => {
  console.log("Restful running on port 3000");
});

app.post("/user_registration/", (req, res, next) => {
  var sqldata = req.body;
  var email = sqldata.email;
  var phone = sqldata.phone;
  var password = sqldata.password;

  con.query("SELECT * FROM user_register where email=?", [email], function (
    err,
    result,
    fields
  ) {
    con.on("error", (err) => {
      console.log("Error in Mysql", err);
    });

    if (result && result.length) {
      res.json("User already exist");
    } else {
      var sql = "INSERT INTO user_register (email,phone,password) VALUES (?,?,?)";
      var values = [email, phone, password];

      console.log(sql, values);

      con.query(sql, values, function (err, result, fields) {
        con.on("error", (err) => {
          console.log("[MySQL ERROR]", err);
        });
        res.json("Register Success");
        console.log("Registered successfully" + sqldata);
      });
    }
  });
});

app.post("/login/", (req, res, next) => {
  var sqldata = req.body;
  var email = sqldata.email;
  var password = sqldata.password;

  con.query("SELECT * FROM user_register where email = ?", [email], function (
    err,
    result,
    fields
  ) {
    con.on("error", (err) => {
      console.log("[MySQL ERROR]", err);
    });

    if (result && result.length) {
      if (password == result[0].password) {
        res.json("Valid user");
      } else {
        res.json("Invalid user");
      }
    }
  });
});

app.post("/addproduct/", (req, res, next) => {
  var sqldata = req.body;
 // var image = sqldata.image;
  var name = sqldata.name;
  var price = sqldata.price;
  var quantity = sqldata.quantity;
  var description = sqldata.description;
  

      var sql = "INSERT INTO product (name,price,quantity,description) VALUES (?,?,?,?)";
      var values = [name, price, quantity,description];

      console.log(sql, values);

      con.query(sql, values, function (err, result, fields) {
        con.on("error", (err) => {
          console.log("[MySQL ERROR]", err);
        });
        res.json("Product Submited Successfully");
        console.log("Submited" + sqldata);
      });
    
  });


  app.get('/getProducts', (req, res) => {
    con.query("SELECT * FROM product",(err, rows, fields) =>{
      if (!err) 
      res.send(rows)
      else
      console.log(err)
    });
  });