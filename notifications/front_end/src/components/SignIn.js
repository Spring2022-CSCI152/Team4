import React from "react";
import { Form } from "react-bootstrap";

const SignIn = ({ signInClicked }) => {

  const signInTrigger = () => {
    signInClicked();
  };

  return (
    <div className="row justify-content-center">
      <div className="txt-align-center"></div>
      <h4>Sign In</h4>
      <hr className="green"></hr>

      <Form.Group>
        <Form.Control className="mb-3" type="email" placeholder="Enter email" />
        <Form.Control className="mb-3" type="password" placeholder="Password" />
        <Form.Control
          className="mb-3"
          type="businessID"
          placeholder="Business ID"
        />
      </Form.Group>

      <div className="d-flex justify-content-center">
        <button
          onClick={signInTrigger}
          type="button"
          className="btn btn-dark btn-lg"
        >
          Sign In
        </button>
      </div>
      <hr className="green"></hr>
    </div>
  );
};

export default SignIn;
