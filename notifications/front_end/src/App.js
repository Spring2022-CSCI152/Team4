import AllList from "./components/AllList";
import React, { useState } from "react";
import {FaUserCircle} from 'react-icons/fa';
import logo from './assets/logo.png';
import DashBtns from "./components/DashBtns";


const Header = () => {
 
  return (
    <header className="area-padding">
 <img src={logo} className="txt-align-center"/>  <h1 className="txt-align-center">FR Most Wanted </h1>
      <div className = "txt-align-right">
        <FaUserCircle size="1.5em"/>
        <div>username</div>
      </div>
    </header>
  );
};


const NavBar = () => {
  return (
  <nav-bar className="areaPadding">
  <ul>
    <li><a className="active">Reports</a></li>
    <li><a>Notifications</a></li>
    <li><a>Admin</a></li>   
    <li><a>Profiles</a></li>   
  </ul>
  <button id="btn-signout">Signout</button>
   </nav-bar>);

};


const Main = () => {
  return (
  <main className="area-padding">
    <DashBtns/>
    <AllList /> 
  </main>
  );
};


const Footer = () => {
  return <footer  className="area-padding">footer ret</footer>;
};



export default function App() {
  const [nav, setNav] = useState("reports");

  return (
    <div id = "app">
      <Header />
      <NavBar/>
      <Main  />
      <Footer />
    </div>
  );
}


