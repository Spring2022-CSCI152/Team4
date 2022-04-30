import React, { useState } from "react";
import Register from "./Register";
import ProfileFormat from "./ProfileFormat";
import ReportFormat from "./ReportFormat";
import axios from "axios";

function RegisterForms() {
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
    box1Name: "Source of Activity",
    box2: false,
    box2Name: "Investigation",
    box3: false,
    box3Name: "Resolution",
    box4: false,
    box4Name: "Conclusion",
    box5: false,
    box5Name: "Dispositional Information",
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
    const response = await axios.post("http://172.24.158.171:8080/api/v1/accounts/register_business", formData)
      .then(response => {
        console.log(response)
        localStorage.setItem("newUser", JSON.stringify(response.data))
        console.log(response.status)

      }).catch(error =>
        console.log(error)
      )

      let newUser = JSON.parse(localStorage.getItem("newUser"))
        
        

      console.log('token ',a, a.token, a.accountIdString, a.email, a.businessId)
      console.log( 'test profile format ', profileData.name, profileData.status, profileData.address, profileData.banDuration, profileData.attributes, profileData.imageName, profileData.involvement, profileData.reports)
          //testing git reset head

          ({
            token: newUser.token, newUser.accountIdString, newUser.email
            accountId: {
                accountIdString: newUser.accountIdString,
                email: newUser.email,
                "businessId": newUser.businessId
            },
            profileFormat:{
                profileData
            }
        })
      console.log(profileData)
      console.log(reportData)
  
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

         <div>{page}, {FormTitles.length - 1 }</div>

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
