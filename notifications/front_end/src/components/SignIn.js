import React, { useState } from "react";
import { Form } from "react-bootstrap";
import axios from 'axios'

const SignIn = ({ signInClicked }) => {

  //async function signInTrigger ()  {
   // signInClicked();
  //};

  async function signInTrigger(e) {


      const signIn = await axios.post("http://172.24.158.171:8080/api/v1/accounts/login",
       formData)
        .then(signIn => {
          console.log(signIn.data)
          localStorage.setItem("user", JSON.stringify(signIn.data))
          console.log('first res ', signIn.status)
        }).catch(error =>
          console.log(error)
        )    
  };

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    businessId: "",
  });

  

  return (
    <div className="row justify-content-center">
      <div className="txt-align-center"></div>
      <h4>Sign In</h4>
      <hr className="green"></hr>

      <Form.Group>
        <input
          className="mb-3"
          type="text"
          placeholder="Email"
          defaultValue={formData.email}
          onChange={(e) => {
            setFormData({ ...formData, email: e.target.value });
          }}
        />
        <input
          className="mb-3"
          type="password"
          placeholder="Password"
          defaultValue={formData.password}
          onChange={(e) => {
            setFormData({ ...formData, password: e.target.value });
          }}
        />
        <input
          className="mb-3"
          type="text"
          placeholder="Business Id"
          defaultValue={formData.businessId}
          onChange={(e) => {
            setFormData({ ...formData, businessId: e.target.value });
          }}
        />
      </Form.Group>

      <div className="d-flex justify-content-center">
        <button 
          type="button"
          className="btn btn-dark btn-lg"
          onClick={(e)=>
            { 
            <input
              value={formData}
              onChange={(e) => setFormData(e.target.value)}
            />;

            console.log('signIN w/ ', formData)
              
          
              signInTrigger(e) }
          }
         
        >
          Sign In
        </button>
      </div>
      <hr className="green"></hr>
    </div>
  );
};

export default SignIn;
