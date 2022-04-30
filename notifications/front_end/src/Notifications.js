import React from "react";
import {IoMdNotifications} from "react-icons/io";
import {MdBubbleChart, MdOutlineBubbleChart} from "react-icons/md";

const Notifications = () => {
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
