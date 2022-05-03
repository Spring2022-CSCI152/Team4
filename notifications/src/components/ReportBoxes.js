import US_states from "./US-states";
import mockData from "../mockData";
import { Button } from "react-bootstrap";
import { ImDrive } from "react-icons/im";
import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
<<<<<<< HEAD
import Test from "./test";
import usePromise from "react-promise";
import Async from "react-async";

const loadFormat = () => {
=======
import Async from "react-async";

function loadFormat () {
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e
  const url = "http://172.24.158.171:8080/api/v1/reports/get_report_format";
  const User = JSON.parse(localStorage.getItem("user"));
  const bodyData = {
    token: User.token,
    accountId: {
      accountIdString: User.accountIdString,
      email: User.email,
      businessId: User.businessId,
    },
  };

  return new Promise((resolve, reject) => {
    axios
      .post(url, bodyData)
      .then((res) => {
        console.log(res);
        resolve(res.data);
      })
      .catch((err) => {reject(err)});
  });
};

const ReportBoxes = () => (
  <Async promiseFn={loadFormat}>
    {({ data, error, isLoading }) => {
      console.log("70 ", data);

      const boxesTrue = [];
      const boxesNames =[];
      if (isLoading) return "Loading...";
      if (error) return `Something went wrong: ${error.message}`;
      if (data) {
        for (const [key, value] of Object.entries(data)) {
          if ( value == true && key.includes("box")) {
            boxesTrue.push(key);
          }
        }
        for (const [key, value] of Object.entries(data)) {
            if ( key.includes("Name")) {
              boxesNames.push(value);
            }
          }

<<<<<<< HEAD
       
        return (
            <div>
                  
                  {boxesNames.map((boxes) => (
                    <div>
                       <div>{boxes} </div>
                    
=======
        return (
            <div>
                  {boxesNames.map((boxes) => (
                    <div>
                       <div>{boxes} </div>
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e
                    </div>
                    )) 
                } 


<<<<<<< HEAD


=======
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e
                {boxesTrue.map((boxes) => (
                    <div>
                       <div>{boxes} </div>
                    <input
                        type="text"
                        placeholder={boxes}
                        defaultValue=""
<<<<<<< HEAD
                        onChange={(e) => {
                        e.target.value;
                        }}
                    />
=======
                       
                      />
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e
                    </div>
                    )) 
                } 
 
            </div>
           
           
        )

    }
    return null;
    }}
  </Async>
);

export default ReportBoxes;
