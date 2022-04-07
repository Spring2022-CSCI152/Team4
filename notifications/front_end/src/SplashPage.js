import logo from "./assets/logo.png";
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Form, FormText } from "react-bootstrap";
import SignIn from "./components/SignIn";
import Register from "./components/Register";
import { useState } from "react";

const SplashPage = () => {


  const [form, setForm] = useState(true);
  const [text, setText] = useState(true);

  function handleClick() {setForm(!form);}  
  function formText(){setText(!text)}
  
  

  return (
    <div className="rows-2 justify-content-center">
      <div className="txt-align-center mt-5 mb-5">
        <img src={logo} />
        <br />
        <h1 className="fw-bolder">FR Most Wanted</h1>
      </div>

      {/* Toggle sign in and register forms*/}
      <div className="row txt-align-center ">
        <div className="col"></div>
        <div className="col">
          {" "}
          {form ? <SignIn /> : <Register />}
          <button onClick={()=>{handleClick(); formText();}} type="button" className="btn btn-link">
          {text ? "Register new business" : "Already a member ?"}
          </button>
        </div>

        <div className="col"></div>
      </div>
    </div>
  );
};

export default SplashPage;
