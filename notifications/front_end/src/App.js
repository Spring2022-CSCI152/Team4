import AllList from "./components/AllList";
import NavBar from "./NavBar";
import React, { useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import logo from "./assets/logo.png";


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
      {<AllList />}
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
