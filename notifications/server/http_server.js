'use strict';

let fs = require('fs'); // ??
let express = require('express') //??
let app = express(); //This makes it a server
let bodyParser = require('body-parser'); //This prevents the 405 Error

app.use(bodyParser.json()); //This automatically parsed the incoming request
app.use(bodyParser.urlencoded({ extended: false })); //This prevents the 405 Error

// This is the 'observable' that will notify the WebSocketServer to do stuff when it's state changes
const message_queue = {

    _observers:[],
    _state:null,
    //Add observer to list of observers
    add: function(observer){
        console.log("adding observer")
        this._observers.push(observer)
    },
    //Update the state and signal (notify) the 'observers' that it changed
    setState: function(state){
        this._state = state;
        this._observers.forEach(element => {
            element.signal(this._state)
        })
    }

};

//Post request
app.post('/', function(req, res) {
    // req has already been parsed by bodyParser
    let message = req.body;
    //Update the state
    message_queue.setState(message)
    //Set return status code
    res.statusCode = 200;
    //set headers
    res.setHeader('Content-type', 'application/json');
    //return data?
    res.end('{"Get": "happy"}');
})

//export 'app' which is the server
exports.app = app;
// export the message_queue so other stuff can use it
exports.message_queue = message_queue;