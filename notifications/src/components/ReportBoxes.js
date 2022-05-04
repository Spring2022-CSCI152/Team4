import US_states from "./US-states";
import mockData from "../mockData";
import { Button } from "react-bootstrap";
import { ImDrive } from "react-icons/im";
import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import Async from "react-async";

function loadFormat () {
  const url = `${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_report_format`;
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

        return (
            <div>
                  {boxesNames.map((boxes) => (
                    <div>
                       <div>{boxes} </div>
                    </div>
                    )) 
                } 


                {boxesTrue.map((boxes) => (
                    <div>
                       <div>{boxes} </div>
                    <input
                        type="text"
                        placeholder={boxes}
                        defaultValue=""
                       
                      />
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
