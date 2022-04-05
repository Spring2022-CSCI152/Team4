import React from "react";
import logo from "./assets/logo.png";
import { useNavigate } from "react-router-dom";
import { Form, FormGroup, Card} from "react-bootstrap";
import CardHeader from "react-bootstrap/esm/CardHeader";


const SignIn = ({authenticate}) => {
  const navigate = useNavigate();

  const signIn = () => {  
      authenticate();
      navigate("Reports");
  };
  return (
    <div className="col-2 m-5 justify-content-center">
 
      {/* Logo */}

      <div>
        <img src={logo} className="justify-content-center"/><br/>  
        <h2>FR Most Wanted</h2>
      </div>  
   
    
        <h4>Sign In</h4>
        <hr className="green"></hr>
        <Form.Group className="col p-3" >
      
          <Form.Control className ="m-3" type="email" placeholder="Enter email" />
          <Form.Control className ="m-3"type="email" placeholder="Password" />
          <Form.Control className ="m-3"type="email" placeholder=" Business Number" />
   
        </Form.Group>    

        <div className="txt-align-center">      
        <button onClick={signIn} type="button" className="btn btn-dark btn-lg">Sign In</button>   
                <hr className="green"></hr>
        <button type="button" className="btn btn-light">Register new business</button>
      
        </div>
  
    </div>
  );
};

export default SignIn;
