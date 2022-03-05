import React, { useState } from "react";
import AllList from "./components/AllList";
import { FaUserCircle } from "react-icons/fa";
import logo from "./assets/logo.png";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
//pages
import Notifications from "./Notifications";
import Profiles from "./Profiles";
import Admin from "./Admin";
import NavBar from "./NavBar";
import SignIn from "./SignIn";
import Reg from "./Reg";

const Header = () => {
  return (
    <header className="area-padding">
      <img src={logo} className="txt-align-center" />{" "}
      <h1 className="txt-align-left">FR Most Wanted </h1>
      <div className="txt-align-right">
        <FaUserCircle size="1.5em" />
        <div>username</div>
      </div>
    </header>
  );
};

const Main = () => {   
  return ( 
    <main>
      <Router>
        <NavBar/>
        <Routes >
          <Route path="/App" element={<AllList />}/>
          <Route path="/Notifications" element={<Notifications />}/>
          <Route path="/Profiles" element={<Profiles />}></Route>
          <Route path="/Admin" element={<Admin />}/>
        </Routes>
      </Router>
    </main>
  );
};

const Footer = () => {
  return <footer className="area-padding">footer ret</footer>;
};

export default function App() {
  return (  
  <div id="app">
        <Header />   
        <Main />
        <Footer />
  </div>
  );
}
