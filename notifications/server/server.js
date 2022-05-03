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
const clients = new Map();

//'ws://localhost:8080/ws' from front end
//connection to back end ws
wss.on('connection', (ws) => {
  const id = uuidv4(); 
  const business_id = null
  const metadata = { id, business_id };
  clients.set(ws, metadata);
  console.log('connected: ' + id);
  


  //message from frontend
  ws.on('message', (message) => {
    const msg = JSON.parse(message);
    const metadata = clients.get(ws);

    //message.sender = metadata.id;
    metadata.business_id = msg.business_id
    clients.put(ws, metadata)
  })
})


wss.on("close", () => {
  clients.delete(ws)
})
  
function uuidv4() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}



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

