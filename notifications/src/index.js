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
import NavBarComp from './components/NavBarComp'
import Profiles from './Profiles'
import Accounts from './Admin'




ReactDOM.render(
    <BrowserRouter> 
        <NavBarComp/>  
        <Accounts/>
         {/* <AllList/> */}
         {/* <App/> */}
    </BrowserRouter>   ,
document.getElementById('root') 
);



