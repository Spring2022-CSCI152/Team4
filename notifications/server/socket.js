const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8080 });

//clients in backend
const clients = new Map();

// 'ws://localhost:8080/ws' from front end
//connection to back end ws
wss.on('connection', (ws) => {
  const id = uuidv4(); //replace later
  const business_id = null
  const metadata = { id, business_id };
  clients.set(ws, metadata);
})
  //key,data


//message from frontend
ws.on('message', (message) => {
    const message = JSON.parse(message);
    const metadata = clients.get(ws);

   //message.sender = metadata.id;
    message.business_id = metadata.business_id;
    clients.put(ws, metadata)
})


  
