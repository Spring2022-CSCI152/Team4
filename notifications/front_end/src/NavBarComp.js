import React, { Component } from "react";
import { Navbar, Nav, Container, Button } from "react-bootstrap";
import logo_white from ".//assets/logo_white.png";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
//pages
import App from "./App";
import Notifications from "./Notifications";
import Profiles from "./Profiles";
import Admin from "./Admin";

function NavBarComp() {
  return (
    <BrowserRouter>
      <div>
        <>   
          <Navbar bg="dark" variant="dark">
            <Container>
              <img src={logo_white} className="txt-align-center" />
              <Navbar.Brand>FR MOST WANTED</Navbar.Brand>
              <Nav className="me-auto">
                  <Nav.Link as={Link} to="/"> Reports </Nav.Link>
                  <Nav.Link as={Link} to={"/Notifications"}>
                    Notifications
                  </Nav.Link>
                  <Nav.Link as={Link} to={"/Profiles"}>
                    Profiles
                  </Nav.Link>
                  <Nav.Link as={Link} to={"/Admin"}>
                    Admin
                  </Nav.Link>
              </Nav> 
              <Button variant="secondary" size="sm">Sign Out</Button>
              <FaUserCircle size="1.5em"  style={{ fill: "#00f200" }} />
        
            </Container>
          </Navbar>
        </>

        <div>
          <Routes>
            <Route path="/" element={<App/>}></Route>
            <Route path="/Notifications" element={<Notifications />}></Route>
            <Route path="/Profiles" element={<Profiles />}></Route>
            <Route path="/Admin" element={<Admin />}></Route>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default NavBarComp;
