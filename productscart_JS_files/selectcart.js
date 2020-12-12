var mysql = require('mysql');
var http = require ('http');

var con = mysql.createConnection({
  host: "localhost",
  user     : 'root',
  password : '',
  database : 'productscart'
});

var json;
con.connect(function(err) {
  if (err) throw err;
 console.log("Connected!");
//create a server object:

            con.query("SELECT * FROM product", function (err, result, fields) {
                if (err) throw err;
                console.log(result);
                var obj = [];
                for (var i = 0; i < result.length; i++){

                    var p = {};           // Object
                    p['pid'] = result[i].pid ;
					p['image']=result[i].image ;
					p['name']=result[i].name ;
				    p['price']=result[i].price ;
          p['quantity']=result[i].quantity ;
          p['description']=result[i].description ;
                    obj.push(p);
                
                }
                
                json= JSON.stringify(obj, undefined, 2); 
                
              })

              let app = http.createServer((req, res) => {
                // Set a response type of plain text for the response
                res.writeHead(200, {'Content-Type': 'text/plain'});
            
                // Send back a response and end the connection
                res.end(json);
            });
            
            // Start the server on port 3000
            app.listen(3000, '192.168.0.104');



});



