import React ,{useState, useEffect}from "react";
import NotificationCard from "./components/NotificationCard";
import { w3cwebsocket as W3CWebSocket } from "websocket";
//const { resolvePath } = require("react-router");

//const client = new W3CWebSocket('ws://localhost:4000/ws');
const Notifications = () => {
    
//   client.onopen =()=>{console.log('yep')}
 
//     return (
//       <div className="container">
      
//           <NotificationCard/>
//           <button> onClick</button>
          

        
//       </div>
//     );
//   };
 
//     //front end



/* let socket = new WebSocket("ws://localhost:8080/ws")
// socket.onopen = function(e) {
//     socket.send(JSON.stringify({"business_id": business_id})
//     )
// }



(async function(){
    const ws = await connectToServer();

    ws.onmessage = (wsMessage) => {
        const msgBody = JSON.parse(wsMessage.data);
        createNotification(msgBody);
    }

    ws.onopen = function(e) {
        ws.send(JSON.stringify({"business_id": business_id}))
    }

    async function connectToServer() {
        const socket = new WebSocket("ws://localhost:8080/ws")
        // socket.onopen = function(e) {
        //     socket.send(JSON.stringify({"business_id": business_id}))
        // }
        return new Promise((resovle, reject) => {
            const timer = setInterval(() => {
                if(socket.readyState === 1){
                    clearInterval(timer);
                    resolve(socket);
                }
            }, 10);
        });
    }

    async function createNotification(msgBody){
        // get image from marc using msg body which contains { "profile_id": "someProfileId", "business_id": "someBusinessId"}
       // image = getImageFromMarc(msgBody)
           // console.log(msgBody);
           

        
        // Reference Ryans API docs to get the profile info from server using this info
        profile = getProfileFromRyan(msgBody)
        console.log("Notification received!")
    }
})


  );*/
 
}; 

export default Notifications;
