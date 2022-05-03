import React, { useState, useEffect } from 'react';



function Notif(){
  
	const [notif, setNotif] = useState([])

	const client = new WebSocket('ws://localhost:8080/');

	const business_id = UserData.business_id;

	function onClick(){

		//Either Nav to Profile or Close Notif
	}

	useEffect(() => {

        client.onopen = function() {
            console.log('Socket open. \nSending Business Id...');
            socket.send(JSON.stringify({business_id: business_id}));
            console.log('Business Id sent.')
        };

        //When you get something back (typically the profile_id and business_id, which is redundant, but oh well
        client.onmessage = function(message) {
            
            let data = JSON.parse(message.data) // turn it into an object
            //Your Notif Creation Code here==========================================

            newNotif = {
            	//img: //,
            	name: data_name,
            	status: data_status
            	// Whatever else you wanna put
            }

            setNotif({newNotif});
        };

		return () => {} //useEffect returns cleanup - close connection here
	}, []);

    function getDisplayable(){
        if(!notif){
            return (<></>)
        }
        else{
            return ( 
                <div>
                </div>)
        }
    }

	return (
		<div className="Notif" onclick={this.onclick}>
			{this.getDisplayable}
		</div>
	)
}

export default Notif;