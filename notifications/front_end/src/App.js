import AllList from "./components/AllList";
import React, { useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import logo from "./assets/logo.png";
import Notifications from "./Notifications";
import Profiles from "./Profiles";
import Admin from "./Admin";
import NavBar from "./NavBar";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';

const Header = () => {
  return (
    <header className="area-padding">
      <img src={logo} className="txt-align-center" />{" "}
      <h1 className="txt-align-center">FR Most Wanted </h1>
      <div className="txt-align-right">
        <FaUserCircle size="1.5em" />
        <div>username</div>
      </div>
    </header>
  );
};

const Main = () => {   
  return ( 
    <main className="area-padding">
      <Router>
        <NavBar />

        <Routes >

          <Route path="/App" element={ <AllList />}></Route>
          <Route path="/Notifications" element={<Notifications />}></Route>
          <Route path="/Profiles" element={<Profiles />}></Route>
          <Route path="/Admin" element={<Admin />}></Route>
        </Routes>

      </Router>
     

    </main>

  );
};

const Footer = () => {
  return <footer className="area-padding">footer ret</footer>;
};

export default function App() {
  const [nav, setNav] = useState("reports");

  return (
    <div id="app">
      <Header />
      <Main />
      <Footer />
    </div>
  );
}
