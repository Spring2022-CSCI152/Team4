import React from "react";
import { Navbar, Nav, Container, Button } from "react-bootstrap";
import logo_white from '../assets/logo_white.png'
import { Link } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

 const NavBarComp = ({authenticate}) => {
  const navigate = useNavigate();
  const signOut = () => {
    authenticate();
    navigate("/");
};
   
  return (
      <div>
        <>
          <Navbar bg="dark" variant="dark">
            <Container>
              <img src={logo_white} className="txt-align-center" />
              <Navbar.Brand>FR MOST WANTED</Navbar.Brand>
              <Nav className="me-auto">
                  <Nav.Link as={Link} to={"/Reports"}>Reports </Nav.Link>
                  <Nav.Link as={Link} to={"/Notifications"}>Notifications</Nav.Link>
                  <Nav.Link as={Link} to={"/Profiles"}>Profiles</Nav.Link>
                  <Nav.Link as={Link} to={"/Admin"}>Admin</Nav.Link>
              </Nav> 
              <button type="button" class="btn btn-sm btn-signout" onClick={()=>signOut()}>Sign Out</button>
              <FaUserCircle size="1.5em"  style={{ fill: "#00f200" }} />
            </Container>
          </Navbar>
        </>
        <div>
          
        </div>
      </div>
 
  ); 
}

export default NavBarComp;
