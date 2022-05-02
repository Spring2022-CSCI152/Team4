import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './reportDashboard.css';
import './utility.css';
import ReportBoxes from './components/ReportBoxes'
import AddReport from './components/AddReport'



ReactDOM.render(
    <BrowserRouter> 
        <AddReport/>
    </BrowserRouter>   ,
document.getElementById('root') 
);



