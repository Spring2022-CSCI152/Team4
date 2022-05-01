import React, { useState } from "react";
import Register from "./Register";
import ProfileFormat from "./ProfileFormat";
import ReportFormat from "./ReportFormat";
import axios from "axios";

function RegisterForms( {signInTrigger}) {
  const [page, setPage] = useState(0);

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


  const FormTitles = ["Register Business", "Profile Format", "Report Format"];

  const PageDisplay = () => {
    if (page === 0) {
      return <Register formData={formData} setFormData={setFormData} />;
    } else if (page === 1) {
      return <ProfileFormat profileData={profileData} setProfileData={setProfileData} />;
    } else {
      return <ReportFormat reportData={reportData} setReportData={setReportData} />;
    }
  };

  async function postRegistrationForm() {
    const form1 = await axios.post("http://172.24.158.171:8080/api/v1/accounts/register_business", formData)
      .then(form1 => {
        console.log(form1.data)
        localStorage.setItem("newUser", JSON.stringify(form1.data))
        console.log('first res ', form1.status)
      }).catch(error =>
        console.log(error)
      )

    const newUser = JSON.parse(localStorage.getItem("newUser"))
    const accountId = {
      accountIdString: newUser.accountIdString,
      email: newUser.email,
      businessId: newUser.businessId
    }

    const form2 = await axios.post("http://172.24.158.171:8080/api/v1/reports/set_profile_format",
      {
        token: newUser.token,
        accountId: accountId,
        profileFormat: { ...profileData, businessId: newUser.businessId }
      })
      .then(form2 => {
        console.log('form2 data: :', form2.data)
        console.log('res 2', form2.status)
      }).catch(error =>
        console.log(error)
      )

    const form3 = await axios.post("http://172.24.158.171:8080/api/v1/reports/set_report_format",
      {
        token: newUser.token,
        accountId: accountId,
        reportFormat: { ...reportData, businessId: newUser.businessId }
      })
      .then(form3 => {
        console.log(form3)
        console.log('res 3', form3.status)
        signInTrigger();
      }).catch(error =>
        console.log(error)
      )
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
                width: page === 0 ? "33.3%" : page == 1 ? "66.6%" : "100%",
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
              if (page == FormTitles.length - 1) {
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
                { postRegistrationForm(e) }
              } else {
                setPage((currPage) => currPage + 1);
              }
            }}
          >
            {page === FormTitles.length - 1 ? "Submit" : "Next"}
          </button>
        </div>
      </div>
    </form>
  );
}

export default RegisterForms;
