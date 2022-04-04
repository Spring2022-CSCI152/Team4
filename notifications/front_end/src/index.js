import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import NavBarComp from './NavBarComp';
import './reportDashboard.css';
import './signIn.css';
import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(
 <NavBarComp/>,
document.getElementById('nav') //render App div root in /public/index.html
);

ReactDOM.render(
    <App />,
  document.getElementById('app') //render App div root in /public/index.html
);


