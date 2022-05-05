import React, { useEffect, useState } from "react";
import Register from "./Register";
import ProfileFormat from "./ProfileFormat";
import ReportFormat from "./ReportFormat";
import BidReminder from "./BidReminder"
import axios from "axios";
import { Navigate } from "react-router";
import { MdTitle } from "react-icons/md";


function RegisterForms( {signInTrigger}) {
  const [page, setPage] = useState(0);
  const [user, setUser] = useState({})

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    businessName: "",
    jobTitle: "Admin",
  });

  const [profileData, setProfileData] = useState({
    name: false,
    status: true,
    address: true,
    banDuration: true,
    attributes: true,
    imageName: true,
    involvement: true,
    reports: true,
  });

  const [reportData, setReportData] = useState({
    reportId: false,
    profiles: false,
    date: false,
    time: false,
    author: false,
    type: false,
    box1: false,
    box1Name: "",
    box2: false,
    box2Name: "",
    box3: false,
    box3Name: "",
    box4: false,
    box4Name: "",
    box5: false,
    box5Name: "",
    attachments: false,
    changeLog: false,
  });


  const FormTitles = ["Register Business", "Profile Format", "Report Format", ""];

  const useUser = () => {

    useEffect(() => {
      return () => user;
    }, [user])

    return user;
  }
  const PageDisplay = () => {
    if (page == 0) {
      return <Register formData={formData} setFormData={setFormData} />;
    } else if (page == 1) {
      return <ProfileFormat profileData={profileData} setProfileData={setProfileData} />;
    } else if (page == 2){
      return <ReportFormat reportData={reportData} setReportData={setReportData} />;
    } else {
      return <BidReminder user={user} setUser={setUser} />;
    }
  };



  async function postRegistrationForm() {

    // when posting form to register business, save info in localStorage
    const form1 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/accounts/register_business`, formData)
      .then(form1 => {
        console.log(form1.data)
        console.log("Setting Userdata")
        setUser(form1.data);
        localStorage.setItem("user", JSON.stringify(form1.data))
      }).catch(error =>{
        console.log(error)
      })

    // create newUser from localStorage infor
    const newUser = JSON.parse(localStorage.getItem("user")) // previously newUser
    const accountId = {
      accountIdString: newUser.accountIdString,
      email: newUser.email,
      businessId: newUser.businessId
    }

    // post profile format
    const form2 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/set_profile_format`,
      {
        token: newUser.token,
        accountId: accountId,
        profileFormat: { ...profileData, businessId: newUser.businessId }
      })
      .then(form2 => {
        console.log('res 2', form2.status)
      }).catch(error =>{
        console.log(error)
      })

    // post report format
    const form3 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/set_report_format`,
      {
        token: newUser.token,
        accountId: accountId,
        reportFormat: { ...reportData, businessId: newUser.businessId }
      })
      .then(form3 => {
        console.log('res 3', form3.status)
      }).catch(error =>{
        console.log(error)
      })

  
      
  }

  return (
    <form >
      <div className="form-container">
        <div className="header">
          <h4>{FormTitles[page]}</h4>
        </div>
        <hr className="green"></hr>
        <div className="body">{PageDisplay()}</div>
        <hr className="green"></hr>
        <div className="footer mx-auto">
          <div className="progressbar mx-auto ">
            <div
              style={{
                width: page === 0 ? "25%" : page == 1 ? "50%" : page == 2 ? "75%" : "100%",
              }}
            ></div>
          </div>

          <button
            className="btn btn-dark btn-lg btn-block"
            disabled={page == 0}
            onClick={() => {
              setPage((currPage) => currPage - 1);
            }}
          >
            Prev
          </button>

          <button
            type="submit"
            className="btn btn-dark btn-lg btn-block"
            onClick={(e) => {
              e.preventDefault();
              if (page == FormTitles.length - 2) {
                <input
                  value={formData}
                  onChange={(e) => setFormData(e.target.value)}
                />;
                <input
                  value={profileData}
                  onChange={(e) => setProfileData(e.target.value)}
                />;
                <input
                  value={reportData}
                  onChange={(e) => setReportData(e.target.value)}
                />;
                {postRegistrationForm(e)}
              } 
              if (page == FormTitles.length - 1) {
                {signInTrigger()}
              }else {
                setPage((currPage) => currPage + 1);
              }
            }}
          >
            {page === FormTitles.length - 2 ? "Submit" : page === FormTitles.length - 1 ? "Continue" :"Next"}
          </button>

        </div>
      </div>
    </form>
  );
}

export default RegisterForms;
