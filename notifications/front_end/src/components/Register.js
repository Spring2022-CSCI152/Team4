import React from "react";
import { Form} from "react-bootstrap";



const Register = () => {
  return (
  
      <div className="row justify-content-center">
        <div>
          <h4>Register</h4>
          <hr className="green"></hr>
          <Form.Group>     
              <Form.Control className ="mb-3" type="name" placeholder="Full Name" />
              <Form.Control className ="mb-3" type="email" placeholder="Email" />
              <Form.Control className ="mb-3" type="password" placeholder="Password" />
              <Form.Control className ="mb-3" type="businessName" placeholder="Business Name" />
          </Form.Group>
         
        </div>
        <div className="txt-align-center">
          <button className="btn btn-dark btn-lg btn-block">Register</button>
          <hr className="green"></hr>
          <li className="b">requirements</li>
          <li className="b">requirements</li>
          <li className="b">requirements</li>
        </div>
      </div>
     
  );

};

export default Register;
