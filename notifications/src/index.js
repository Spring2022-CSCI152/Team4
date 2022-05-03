import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './reportDashboard.css';
import './utility.css';
import ReportBoxes from './components/ReportBoxes'
import reports from './Reports'



ReactDOM.render(
    <BrowserRouter> 
        <ReportBoxes/>
    </BrowserRouter>   ,
document.getElementById('root') 
);



