import React from "react";
import logo from "./assets/logo.png";

const SignIn = () => {
  return (
    <div id="login">
      {/* Logo */}
      <div className="txt-align-center">
        <img src={logo} />
        <h1>FR Most Wanted</h1>
      </div>
      {/* Sign In Header */}
      <div className="var-box login-inner">
        <div>
          <h4>Sign In</h4>
          <hr className="green"></hr>
          <div className="var-box">Email</div>
          <div className="var-box">Business ID</div>
          <div className="var-box">Password</div>
        </div>
        <div className="txt-align-center">
          <button className="btn blk">Sign In</button>
          <p>Forgot Password?</p>
          <hr className="green"></hr>
          <p>New Business</p>
          <button className="btn blk">Register</button>
        </div>
      </div>
    </div>
  );
};

export default SignIn;
