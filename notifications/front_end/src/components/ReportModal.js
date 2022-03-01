import React from "react";
import { createPortal } from "react-dom";
import "../report_modal.css";
import { BsFillExclamationCircleFill } from "react-icons/bs";
import mockData from "../mockData.js";
import { FaThinkPeaks } from "react-icons/fa";

const ReportModal = ({ open, children, onClose }) => {
  if (!open) return null;

  return createPortal(
    <div className="view-report-overlay">
      <div className="view-report-inner">
        <button className="close-btn" onClick={onClose}>
          X
        </button>

        <h className= "modal-header">
          <BsFillExclamationCircleFill
            size="1.5em"
            style={{ fill: "#00f200" }}
          />
          Security Incident Report
        </h>

        <div className="col-5">
          <p>
            Report Number:
            <br />
            {mockData[0].r_id}
          </p>
          <p>
            Date/Time:
            <br />
            {mockData[0].date}
          </p>
          <p>
            Incident Date/Time:
            <br />
            {mockData[0].date}
          </p>
          <p>
            Location:
            <br />
            {mockData[0].location}
          </p>
          <p>
            Incident Type:
            <br />
            {mockData[0].type}
          </p>
        </div>
        <div className="row">
          <div>
            <h>Involved parties</h>
            {mockData[0].profile.map((customer) => (
              <div className="col-4">
                <img src={customer.url} />
                <div>
                  <b>Name:</b> {customer.name}
                  <br/>
                  <b>Status:</b> {customer.status}
                  <br/>
                  <b>Eyes:</b>
                </div>
                <div>
                  <b>Ban Start:</b> somedate
                </div>
                <div>
                  <b>Ban End:</b> never
                </div>
                <div>
                  <b>Something else:</b> Something
                </div>
              </div>
            ))}
          </div>
        </div>
        <div className="row">
            {mockData[0].boxes.map((boxes,i) => (
            <div key={i}>
                <div>
                    <h>{boxes.box}</h>
                </div>
                <div  className="var-box">
                    {boxes.txt} 
                </div>
    
            </div>))}
        </div>
        <div className="row">
        <   h>Files</h>
        </div>
        <div className="row">
        <h>Update logs</h>
        </div>
      </div>
    </div>,
    document.getElementById("modal")
  );
};

export default ReportModal;
