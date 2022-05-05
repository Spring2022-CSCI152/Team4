import { reject } from "async";
import axios from "axios";
import React from "react";
import Async from "react-async";

const getUser = () => {
  const url = `${process.env.REACT_APP_JAVA_SERVER}/api/v1/accounts/my_account_info`;

  const User = JSON.parse(localStorage.getItem("user"));

  const bodyData = {
    token: User.token,
    accountId: {
      accountIdString: User.accountIdString,
      email: User.email,
      businessId: User.businessId,
    },
  };

 return
  new Promise((resolve, reject) => {
    axios
      .post(url, bodyData)
      .then((res) => {
        console.log(res);
        resolve(res.data);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

const BidReminder = () => {

  let userData = JSON.parse(localStorage.getItem('user'));
  const displ = () => {
    if (!!userData.businessId){
      return (
        <>
        <h3>Welcome</h3>
        <div className="text-danger">
            <b>Important!</b> Save the following Business ID to sign in again in
            the future
        </div>
        <div>Business ID</div>
        <b>{userData.businessId}</b>
        </>
      );
    }
    else {
      return (<>Loading...</>)
    }
  }

  return (<>{displ()}</>)

}

export default BidReminder;

// <div>
// <>
//     <h3>Welcome</h3>
//     <div className="text-danger"><b>Important!</b> Save the following Business ID to sign in again in the future</div>
//     <div>Business ID</div>
//     <b>{bid}</b>
// </>
// </div>
