import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './reportDashboard.css';
import './utility.css';
import Form from './components/testMultiRegister';


ReactDOM.render(
    <BrowserRouter> 
        <Form/>
    </BrowserRouter>   ,
document.getElementById('root') 
);



