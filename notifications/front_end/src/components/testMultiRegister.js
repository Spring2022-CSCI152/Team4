import React, { useState } from "react";
import Register from "./Register";
import ProfileFormat from "./ProfileFormat";
import ReportFormat from "./ReportFormat";

function RegisterForms() {
  const [page, setPage] = useState(0);
  const [formData, setFormData] = useState(
  {
      firstName: "",
      lastName: "",      
      email: "",
      password: "", 
      businessName: "",
      jobTitle: "",
//move to report
      reportBusinessId:false,
      reportId:false,
      profiles:false,
      date:false,
      time:false,
      author:false,
      type:false
  });


  const FormTitles = ["Register Business", "Profile Format", "Report Format"];

  const PageDisplay = () => {
    if (page === 0) {
      return <Register formData={formData} setFormData={setFormData} />;
    } else if (page === 1) {
      return <ProfileFormat formData={formData} setFormData={setFormData} />;
    } else {
      return <ReportFormat formData={formData} setFormData={setFormData} />;
    }
  };

  return (
  


    <div className="form">
      
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
            style={{ width: page === 0 ? "33.3%" : page == 1 ? "66.6%" : "100%" }}
          ></div>
          </div>

          <button className="btn btn-dark btn-lg btn-block"
            disabled={page == 0}
            onClick={() => {
              setPage((currPage) => currPage - 1);
            }}
          >
            Prev
          </button>
          <button className="btn btn-dark btn-lg btn-block"
            onClick={() => {
              if (page === FormTitles.length - 1) {
                alert("FORM SUBMITTED");
                console.log(formData);
              } else {
                setPage((currPage) => currPage + 1);
              }
            }}
          >
            {page === FormTitles.length - 1 ? "Submit" : "Next"}
          </button>
        </div>
      </div>
    </div>
  
  );
}

export default RegisterForms;