import US_states from "./US-states";
import mockData from "../mockData";
import { Button } from "react-bootstrap";
import { ImDrive } from "react-icons/im";
import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import Async from "react-async";
import { form, input } from "react-bootstrap";

function loadFormat() {
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
}

const ReportBoxes = () => (
  <Async promiseFn={loadFormat}>
    {({ data, error, isLoading }) => {
      const boxesNames = [];
      if (isLoading) return "Loading...";
      if (error) return `Something went wrong: ${error.message}`;
      if (data) {
        //filter to get all report box names
        for (const [key, value] of Object.entries(data)) {
          if (typeof value == `string` && value != "") {
            boxesNames.push(value);
          }
        }
        return (
          <div>
            {boxesNames.map((boxes) => (
              <div className="mt-4 m-5">
                <div className="form-group">
                  <label for="comment">{boxes} </label>
                  <textarea className="form-control" rows="5" id="comment"></textarea>
                </div>
              </div>
            ))}
          </div>
        );
      }
      return null;
    }}
  </Async>
);

export default ReportBoxes;
