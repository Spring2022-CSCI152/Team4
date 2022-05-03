const { client } = require("websocket")

//thing being observed
let message_queue={
observers:[],
state: null,

    add: function( observer ) {
        this.observers.push(observer)
    },

    getState: function(){
        return this.state
    },
    setState: function (){
        this.state = this.state
        //for loop iter thru each object
        Object.signal(this.state)
    }
}

message_queue.add(message_sender)

let message_sender={
    signal: function(state){
        [clients.keys()].forEach((client)) {
            const meta = clients.get(client)
            if (meta.buessiness_id == state.buessiness_id)
            client.send(state) //send to frontend
        }
        
    }
}












