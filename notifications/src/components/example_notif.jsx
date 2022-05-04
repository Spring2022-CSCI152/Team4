import React, { useState, useEffect } from 'react';
import mockData from '../mockData'

const url = 'ws://localhost:8081/';

function reconnectingSocket(url){
    let client;
    let isConnected = false;
    let reconnectOnClose = true;
    let messageListeners = [];
    let stateChangeListeners = [];

    function on(fn){
        messageListeners.push(fn);
    }

    function off(fn){
        messageListeners = messageListeners.filter(l => l!== fn);
    }

    function onStateChange(fn){
        stateChangeListeners.push(fn);
        return () => {
            stateChangeListeners = stateChangeListeners.filter(l => l !== fn);
        }
    }

    function start(){
        client = new WebSocket(url);

        client.onopen = () => {
            isConnected = true;
            stateChangeListeners.forEach(fn => fn(true));
        }

        const close = client.close;

        client.close = () =>{
            reconnectOnClose = false;
            close.call(client);
        }

        client.onmessage = (message) => {
            let data = JSON.parse(message.data);
            console.log("Getting Data:" , data.profile_id);
            messageListeners.forEach(fn => fn(data));
        }

        client.onerror = (e) => console.log(e);

        client.ononclose = () =>{
            isConnected = false;
            stateChangeListeners.forEach(fn => fn(false));

            if(!reconnectOnClose){
                console.log('WS closed by app');
                return ;
            }

            setTimeout(start, 3000);
        }

    }

    start();

    return {
        on, off, onStateChange,
        close: () => client.close(),
        getClient: () => client,
        isConnected: () => isConnected
    };
}

const client = reconnectingSocket(url);

function useMessages(){
    const [messages, setMessages] = useState({});

    useEffect(() => {
        console.log("Setting Messages");
        //'message' is expected to be a JSON
        function handleMessage(message){
            setMessages(message);
        }
        client.on(handleMessage); //This sets the above function as the 'message listener'
        return () => client.off(handleMessage);
    }, [messages, setMessages]);

    return messages;
}

const Notif = ({loggedIn}) => {
    const messages = useMessages();
    const [isConnected, setIsConnected] = useState(client.isConnected());
    const [isUser, setUser] = useState(loggedIn);
    const [notif, setNotif] = useState({
        id: null,
        status: null
    });

    useEffect(() => {
        console.log("Is Connected");
        return client.onStateChange(setIsConnected);
    }, [setIsConnected]);

    // If user is logged in, send business_id to web socket
    useEffect(() => {
        console.log(isUser)
        if(isConnected && isUser && !!localStorage.getItem('user')){
            let data = JSON.parse(localStorage.getItem('user'));
            console.log(data);
            client.getClient().send(JSON.stringify({business_id: data.businessId}));
        }
    }, [isConnected, isUser]);


    //When the messages dependecies is changed, call this function
    useEffect(() =>{
        console.log("Messages Updated")
        //'messages' is expected to be a JSON
        console.log(messages.profile_id)
        let status = null;
        mockData.forEach(report => {
            report.profile.forEach(profile => {
                if(profile.name == messages.profile_id){
                    status = profile.status;
                }
            });
        });
        setNotif({
            id: messages.profile_id,
            status: status
        })
        console.log(notif)
        console.log(messages.profile_id);

    }, [messages])

    function onclick(){
        console.log("Clicked")
        setNotif({
            id: null, 
            status: null
        })
    }

    const getDisplayable = () => {
        if(notif.id !== null){
            return (
                
                <button onClick={onclick}>
                    <div>Detected!</div>
                    <div>Name: {notif.id}</div>
                    <div>Status: {notif.status}</div>
                </button>
                
            )
        }
        else{
            return (<></>)
        }
    }

    return (
           <>{getDisplayable()}</>
        
    )
}

export default Notif;