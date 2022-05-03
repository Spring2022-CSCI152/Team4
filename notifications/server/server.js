<<<<<<< HEAD
const http = require('http');
const hostname = '0.0.0.0';
const port = 4000;

let message_queue = {
  _observers: [],
  _state: null,
  add: function(observer){
    this._observers.push(observer)
  },
  getState: function(){
    return this._state
  },
  setState: function(state){
    this._state = state
    this._observers.forEach((ob) => {
      ob.signal(state)
    })
  }
};

let message_sender = {
  signal: function(state){
   clients.forEach((client) => {
      const meta = clients.get(client)
      if(meta.business_id == state.business_id){
        client.send(state)
      }
    });
  }
};


// let message_sender = {
//   signal: function(state){
//     [...clients, keys].forEach((client) => {
//       const meta = clients.get(client)
//       if(meta.business_id == state.business_id){
//         client.send(state)
//       }
//     });
//   }
// };


// let test_signal = {
//   signal: function(state){
//     [...clients().keys()].forEach((client) => {
//       const meta = clients.get(client)
//       if(meta.business_id == state.business_id){
//         console.log("signal out")
//       }
//     });
//   }
// };
// message_queue.add(test_signal);

//triggers notification for all ws
message_queue.add(message_sender); 

const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8080 });

//map of all clients in backend
=======
'use strict';

let WSServer = require('ws').Server; 
let server = require('http').createServer();
let app = require('./http_server') // START HERE!!! Get Exported Goods From Server File
let httpServer = app.app; // Store the 'server' part of the Server File into httpServer
let messageQueue = app.message_queue // Create a reference to the 'observable message queue'

// SET PORT AND CREATE A 'MAP' TO STORE WebSocket Connections and User Data INTO
const port = 8080;
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e
const clients = new Map();

// CREATE A NEW WebSocket Server setting the internal 'server' to the server defined above
let wss = new WSServer({
    server: server
});

// When the WebSocket server gets a 'Request' aka an HTTP Request, forward it to the HttpServer we defined above
// This allows them both to work on the same port
server.on('request', httpServer);

// Define the 'Observer' that will get signaled on message_queue updates
let message_sender = {
    signal: function(state){
        console.log("Forwarding Message...", state);
        //For each client, if their business_id matches the business_id in the 'state'
        [ ...clients.keys() ].forEach(ws => {
            if(clients.get(ws).business_id == state.business_id){
                //Stringify the State and send it to the client (user)
                ws.send(JSON.stringify(state));
                console.log("Message Forwarded!")
            }
        })
    }
  };

console.log("Adding Message Sender to Message Queue...")
//Register the 'Observer' with the 'Observable'
messageQueue.add(message_sender);

// When WebSocketServer gets connected to do the following => 
wss.on('connection', function connection(ws){

    //Create Unique ID
    const id = uuidv4();
    //Create basic connection data 
    const metadata = { "id": id, "business_id": null};
    //Save Connection and Metadata
    clients.set(ws, metadata);
    //Print out Connection Statement
    console.log('connected: ' + id);   

    //When Front End Messages backed
    ws.on('message', (message) => {
        console.log("Receiving Business Id...")
        //Get Message data, this should be the business_id json as a string
        const msg = JSON.parse(message);
        //Using the connection from ws, get the stored metadata
        let metadata = clients.get(ws);
        console.log("Setting Business Id...")
        //update the metadata with the appropriate business_id
        metadata.business_id = msg.business_id;
        console.log("Saving Business Id...")
        //save the updated data
        clients.set( ws, metadata)
    });

    //When the front end refreshes or logs out, remove the connection from the stored clients
    ws.on("close", (ws) => {
        console.log("Closing Connection...")
        clients.delete(ws)
    })
});

//On server close, erase all clients, or else memory leak
wss.on("close", () => {
    console.log("WSS Closing Connection...")
    clients.clear();
});

// Unique Id function, don't ask me, it's magic
function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

<<<<<<< HEAD


const server = http.createServer((req, res) => {
  if (req.method === "GET") {
    res.writeHead(200, { "Content-Type": "text/html" });
    fs.createReadStream("./public/form.html", "UTF-8").pipe(res);
  } 
  else if (req.method === "POST") {
     var body = "";
    req.on("data", function (chunk) {
      body += chunk;
    });
    message_queue.setState(body)
    req.on("end", function (chunk) {
      
      console.log(JSON.parse(body))
    });
    res.statusCode = 200;
    res.setHeader('Content-type', 'application/json');
    res.end('{"Get": "happy"}');
  }
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});

=======
//Start the server by calling listen function with the defined port
server.listen(port, function(){ console.log(`http/ws server listening on ${port}`);});
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e
