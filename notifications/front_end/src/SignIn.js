import React from "react";
import logo from "./assets/logo.png";
import { Link, useNavigate } from "react-router-dom";
import { Form} from "react-bootstrap";
import Register from "./Register";


const SignIn = ({authenticate}, {regToggle}) => {

  const navigate = useNavigate();

  const signInTrigger = () => {  
      authenticate();
      navigate("Reports");
  };

  const regToggleTrigger = () => {  
    console.log("regToggle triggered")
    navigate("Register")
  };

  return (
    <div className="row justify-content-center">
      <div className="col"></div>
      <div className="col">
        <div className="txt-align-center mt-5 mb-5">
          <img src={logo} /><br/>
          <h2>FR Most Wanted</h2>  
        </div>    
        
            <h4>Sign In</h4>
            <hr className="green"></hr>
        
            <Form.Group >     
              <Form.Control className ="mb-3" type="email" placeholder="Enter email" />
              <Form.Control className ="mb-3" type="email" placeholder="Password" />
              <Form.Control className ="mb-3" type="email" placeholder="Business Number" />
            </Form.Group>
            
            <div className=" d-flex justify-content-center">
              <button onClick={signInTrigger} type="button" className="btn btn-dark btn-lg btn-block ">Sign In</button>   
            </div>

            <hr className="green"></hr>
            <Link as={Link} to={<Register/>}>Register </Link>
            <div className=" d-flex justify-content-center">
              <button type="button" className="btn btn-light">Register new business</button>
            </div>
           
      </div>
      <div className="col"></div>
      </div>
  );
};

export default SignIn;
