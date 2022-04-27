import React from "react";
import { Form, } from "react-bootstrap";

const Register = (formData, setFormData) => {
  return (
    <div className="row justify-content-center">
      <div className="txt-align-center"></div>
      <h4>Register</h4>
      <hr className="green"></hr>
      <Form.Group>
        <Form.Control className="mb-3" type="text" placeholder="First Name"  value={formData.firstName} />
        <Form.Control className="mb-3" type="text" placeholder="Last Name"  value={formData.LastName} />
        <Form.Control className="mb-3" type="text" placeholder="Email" value={formData.email}/>
        <Form.Control className="mb-3" type="password" placeholder="Password" value={formData.password} />
        <Form.Control className="mb-3" type="text" placeholder="Business Name" value={formData.businessName} />
        <Form.Control className="mb-3" type="text" placeholder="Job Title" value={formData.jobTitle} />
      </Form.Group>

      <div className="txt-align-center ">
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
