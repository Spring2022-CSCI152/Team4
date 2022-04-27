import React, { useState } from "react";
import { Form } from "react-bootstrap";

const SignIn = ({ signInClicked }) => {
  const signInTrigger = () => {
    signInClicked();
  };

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    businessId: null,
  });

  return (
    <div className="row justify-content-center">
      <div className="txt-align-center"></div>
      <h4>Sign In</h4>
      <hr className="green"></hr>

      <Form.Group>
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="Email"
          value={formData.email}
          onChange={(e) => {
            setFormData({ ...formData, email: e.target.value });
          }}
        />
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="Password"
          value={formData.password}
          onChange={(e) => {
            setFormData({ ...formData, password: e.target.password });
          }}
        />
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="Business Id"
          value={formData.businessId}
          onChange={(e) => {
            setFormData({ ...formData, businessId: e.target.businessId });
          }}
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
