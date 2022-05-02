import React from "react";
import { Form } from "react-bootstrap";

const Register = ({ formData, setFormData }) => {
  return (
    <div className="row justify-content-center">
      <Form.Group>
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="First Name"
          value={formData.firstName}
          onChange={(e) => {
            setFormData({ ...formData, firstName: e.target.value });
          }}
        />
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={(e) => {
            setFormData({ ...formData, lastName: e.target.value });
          }}
        />
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
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={(e) => {
            setFormData({ ...formData, password: e.target.value });
          }}
        />
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="Business Name"
          value={formData.businessName}
          onChange={(e) => {
            setFormData({ ...formData, businessName: e.target.value });
          }}
        />
        <Form.Control
          className="mb-3"
          type="text"
          placeholder="Job Title"
          value={formData.jobTitle}
          onChange={(e) => {
            setFormData({ ...formData, jobTitle: e.target.value });
          }}
        />
      </Form.Group>
      <div className="txt-align-center ">
        <li className="b">requirements</li>
        <li className="b">requirements</li>
        <li className="b">requirements</li>
      </div>
    </div>
  );
};

export default Register;
