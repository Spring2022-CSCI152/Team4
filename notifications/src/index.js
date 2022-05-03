import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './reportDashboard.css';
import './utility.css';
import ReportBoxes from './components/ReportBoxes'
import AllList from './components/AllList'



ReactDOM.render(
    <BrowserRouter> 
        <App/>
    </BrowserRouter>   ,
document.getElementById('root') 
);



