import AllList from "./components/AllList";
import React, { useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import logo from "./assets/logo.png";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
//pages
import Notifications from "./Notifications";
import Profiles from "./Profiles";
import Admin from "./Admin";
import NavBar from "./NavBar";
import SignIn from "./SignIn";

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

const signedIn={x:true};
signedIn.x = true
export default function App({signedIn}) {
  return ( signedIn ?  <div className="txt-justify-center">{<SignIn/>}</div>
  :<div id="app">
        <Header />   
        <Main />
        <Footer />
  </div>
  
  );
}
