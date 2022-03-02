import React from "react";
import logo from './assets/logo.png'

const Login = () => {
  return (
    <div id="login">
    {/* Logo */}
      <div className="txt-align-center">
        <img src={logo}/>
        <h1>FR Most Wanted</h1>
      </div>

    {/* Login Header */}
      <div className="var-box login-inner">
        <div>
            Sign In 
            <hr className="green"></hr>
        </div>
    {/* Input fields */}
        <div>
              Email: <div className="var-box"></div>
              Business ID: <div className="var-box"></div>
              Password: <div className="var-box"></div>
              <div className="txt-align-center">
                <button className="btn blk">Sign In</button>
                <p>Forgot Password?</p>
              </div>
        </div>
      {/* Register */}
        <div className="txt-align-center">
          <hr className="green"></hr>
          <p>New Business</p>
          <button className="btn blk">Register</button>
        </div>

      </div>  
    </div>

  );
};

export default Login;
