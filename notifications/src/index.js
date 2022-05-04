import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './reportDashboard.css';
import './utility.css';
import ReportBoxes from './components/ReportBoxes'
import AllList from './components/AllList'
import Notif from './components/example_notif'



ReactDOM.render(
    <BrowserRouter> 
        <Notif/>
        <AllList/>
    </BrowserRouter>   ,
document.getElementById('root') 
);



