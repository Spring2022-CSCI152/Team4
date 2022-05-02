import React from "react";
import { Navbar, Nav, Container, Button } from "react-bootstrap";
import logo_white from '../assets/logo_white.png'
import { Link } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import axios from 'axios';

 const NavBarComp = () => {
  const navigate = useNavigate();

  async function handleSignOut(e) {

    const user = JSON.parse(localStorage.getItem("user"))

    const accountId = {
          accountIdString: user.accountIdString,
          email: user.email,
          businessId: user.businessId
    }
   
    const x=
      {
        token: user.token,
        accountId: accountId
      }
    console.log('x = ',x)

    const signOut = await axios.put("http://172.24.158.171:8080/api/v1/accounts/logout",
      {
        token: user.token,
        accountId: accountId
      })
      .then(signOut => {
        console.log(signOut.data)
        console.log('response ', signOut.status)
        
      }).catch(error =>{
        console.log(error)
      }
      )
    
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
              <button type="button" className="btn btn-sm btn-signout" onClick={()=>handleSignOut()}>Sign Out</button>
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
