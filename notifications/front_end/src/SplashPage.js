import logo from "./assets/logo.png";
import React from "react";
import SignIn from "./components/SignIn";
import Register from "./components/Register";
import { useState } from "react";

const SplashPage = ({ authenticate }) => {

  const [form, setForm] = useState(true);
  const [text, setText] = useState(true);

  function handleFormToggleClick() {  
    setForm(!form);
    setText(!text);
  }
  
  return (
    <div className="rows-2 justify-content-center">
      <div className="txt-align-center">
        <img src={logo} />
        <br />
        <h1 className="fw-bolder">FR Most Wanted</h1>
      </div>

      {/* Toggle sign in and register forms*/}
      <div className="row txt-align-center ">
        <div className="col"></div>
        <div className="col">
          {" "}
          {form ? <SignIn signInClicked={()=>authenticate()} /> : <Register />}
          <button
            onClick={() => {
              handleFormToggleClick();
            }}
            type="button"
            className="btn btn-link"
          >
            {text ? "Register new business" : "Already a member ?"}
          </button>
        </div>

        <div className="col"></div>
      </div>
    </div>
  );
};

export default SplashPage;
