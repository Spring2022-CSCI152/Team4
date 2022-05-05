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

  return new Promise((resolve, reject) => {
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

    <Async promiseFn={getUser}>
    {({ data, error, isLoading }) => {
        if (isLoading) return <>"Loading..."</>;
        if (error) return <>`Something went wrong: ${error.message}`</>;
        if (data) {
        return (
            <>
            <h3>Welcome</h3>
            <div className="text-danger">
                <b>Important!</b> Save the following Business ID to sign in again in
                the future
            </div>
            <div>Business ID</div>
            <b>{data}</b>
            </>
        );
        }
        return null;
    }}
    </Async>;
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
