import React from "react";
import logo from "./assets/logo.png";
import { useNavigate } from "react-router-dom";


const SignIn = ({authenticate}) => {
  const navigate = useNavigate();

  const signIn = () => {  
      authenticate();
      navigate("Reports");
  };
  return (
    <div id="login">
      {/* Logo */}
      <div className="txt-align-center">
        <img src={logo} />
        <h1>FR Most Wanted</h1>
      </div>
      {/* Register Header */}
      <div className="">
        <div>
          <h4>Sign In</h4>
          <hr className="green"></hr>
          <div className="var-box">Email</div> 
          <div className="var-box">Password</div>
          <div className="var-box"> Business Number</div>
        </div>    

        <div className="txt-align-center">      
        <button onClick={signIn} type="button" className="btn btn-dark btn-lg">Sign In</button>   
                <hr className="green"></hr>
        <button type="button" className="btn btn-light">Register new business</button>
      
        </div>
      </div>
    </div>
  );
};

export default SignIn;
