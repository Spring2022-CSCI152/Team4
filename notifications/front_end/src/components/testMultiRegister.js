import React, { useState } from "react";
import Register from "./Register";
import ProfileFormat from "./ProfileFormat";
import ReportFormat from "./ReportFormat";

function Form() {
  const [page, setPage] = useState(0);
  const [formData, setFormData] = useState(
  {
      email: "",
      businessName: "",
      firstName: "",
      lastName: "",
      password: "",
      jobTitle: ""
  });

  

  const FormTitles = ["Register", "Profile Format", "Report Format"];

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
      <div className="progressbar">
        <div
          style={{ width: page === 0 ? "33.3%" : page == 1 ? "66.6%" : "100%" }}
        ></div>
      </div>
      <div className="form-container">
        <div className="header">
          <h1>{FormTitles[page]}</h1>
        </div>
        <div className="body">{PageDisplay()}</div>
        <div className="footer">
          <button
            disabled={page == 0}
            onClick={() => {
              setPage((currPage) => currPage - 1);
            }}
          >
            Prev
          </button>
          <button
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

export default Form;