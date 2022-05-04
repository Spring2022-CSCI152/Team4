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




ReactDOM.render(
    <BrowserRouter> 
<<<<<<< HEAD
        {/* <NavBarComp/>
        <Notif/>
        <AllList/> */}
        <App/>
    </BrowserRouter>   ,
=======
        <App/>
    </BrowserRouter> ,
>>>>>>> 23509c58a45a904b5b5a91be3f21edfc4c6fd96d
document.getElementById('root') 
);



