import logo from "./assets/logo.png";
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Form} from "react-bootstrap";
import SignIn from "./components/SignIn";
import Register from "./components/Register";
import { useState } from "react";



const SplashPage = () => {
    
    let btnText ="Register new business";
    const [form, setForm] = useState(true);
    function handleClick (){
        setForm (!form )
        btnText = "Already a member?";

        
    }

  return (
    <div className="rows-2 justify-content-center">
 
            <div className="txt-align-center mt-5 mb-5">
                <img src={logo} /><br/>
                <h2>FR Most Wanted</h2>  
            </div>    
       
        
        {/* Toggle sign in and register forms*/}
        <div className="row">
           <div className="col"></div>
           <div className="col"> { form ?  <SignIn/> : <Register/> } 
           <button onClick={handleClick} type="button" className="btn btn-link">{btnText}</button>   
           <button type="button" class="btn btn-link">Link</button>
          
        </div>

           <div className="col"></div>
        </div>      
        
    </div>

  );
};

export default SplashPage;
