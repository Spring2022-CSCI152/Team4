import React from "react";
import logo from "./assets/logo.png";

const Reg = () => {
  return (
    <div id="login">
      {/* Logo */}
      <div className="txt-align-center">
        <img src={logo} />
        <h1>FR Most Wanted</h1>
      </div>
      {/* Register Header */}
      <div className="var-box login-inner">
        <div>
          <h4>Register</h4>
          <hr className="green"></hr>
          <div className="var-box">First Name</div>
          <div className="var-box">Last Name</div>
          <div className="var-box">Email</div>
          <div className="var-box"> Business Name</div>
          <div className="var-box">Password</div>
        </div>
        <div className="txt-align-center">
          <button className="btn blk">Register</button>
          <p>Already have an account?</p>
          <hr className="green"></hr>
          <li className="b">requirements</li>
          <li className="b">requirements</li>
          <li className="b">requirements</li>
        </div>
      </div>
    </div>
  );
};

export default Reg;
