import React from "react";
import {IoMdNotifications} from "react-icons/io";
import {MdBubbleChart, MdOutlineBubbleChart} from "react-icons/md";

// import React ,{useState, useEffect, Component}from "react";
// import NotificationCard from "./components/NotificationCard";
// import { w3cwebsocket as W3CWebSocket } from "websocket";
//const { resolvePath } = require("react-router");


//const client = new W3CWebSocket('ws://localhost:4000/ws');
const Notifications = () => {

// onButtonClicked = (value) => {
//     client.send(JSON.stringify({
//         type:"message",
//         msg: value
//     }))
// }

//   client.onopen =()=>{console.log('socket open')}
//   client.onmessage = (message) =>{
//     const dataFromServer = JSON.parse(message.data);
//     console.log('message seen', dataFromServer);      
// }


  const NoNewNotif = 0;

  // No New Notifications
  if(NoNewNotif){
    return(
      <div className ="row">
        <h6> <IoMdNotifications size="1.5em" style={{ fill: "#00f200" }}/> {"Notifications"} </h6>

        <div className="col p-3 m-5 rounded-start" style={{ backgroundColor: "#eeeeee", height: "auto" }}>
          No New Notifications 
        </div>

      </div>
    );
  }

  // New Notificatoins
  else{
    return (
      <div>

        <div className="row">
          <h6> <IoMdNotifications size="1.5em" style={{ fill: "#00f200" }}/> {"Notifications"} </h6>
        </div>

        <div className="col p-3 m-3 rounded-start" style={{ backgroundColor: "#eeeeee", height: "auto" }}>
          
          {/* Unopened Notifications */}
          <div className="row">
            <div className="card acct area-padding" style={{ width: "90rem"}}> <MdBubbleChart size="1.5em" style={{ fill: "#00f200"}}/> 
              {"Notification description .."} 
            </div>      
          </div>

          {/* Opened Notifications */}
          <div className="row">
            <div  className="card acct area-padding" style={{ width: "90rem", backgroundColor: "#eeeeee"}}> 
            {"Notification description .."} 
            </div>      
          </div>

        </div>
        
      </div>
    );
  }
};

export default Notifications;




 
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
  ); */
 
