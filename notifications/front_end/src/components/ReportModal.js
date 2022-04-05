import React from "react";
import { createPortal } from "react-dom";
import "../reportModal.css";
import { BsFillExclamationCircleFill, BsFileEarmarkImage, BsFillTrashFill } from "react-icons/bs";
import mockData from "../mockData.js";

const ReportModal = ({ open, onClose }) => {
  if (!open) return null;

  return createPortal(
    <div className="view-report-overlay">
      <div className="view-report-inner">
        <button className="close-btn" fixed="top" onClick={onClose}>
          X
        </button>


        <div className="row">
          <div className="col-1 p-1"> <BsFillExclamationCircleFill size="2rem" style={{ fill: "#00f200" }} /> 
        </div>
          <div className="col-10 p-1"><h3 className="text-align-left ml-3">Security Incident Report</h3>
          </div>
        </div>

        <div className="row">
          <div className="col">
              <b>Report Number:</b><br/>
              {mockData[0].r_id}
          </div>
          <div className="col">
          <b>Date/Time:</b><br/>
              {mockData[0].date}
          </div>

          <div className="col">
          <b>Incident Date/Time:</b><br/>
              {mockData[0].date}
          </div>
          <div className="col">
          <b>Location:</b><br/>
              {mockData[0].location}
          </div>
          <div className="col">
          <b>Incident Type:</b><br/>
              {mockData[0].type}
          </div>
        </div>
 
        <div className= "container">    
        <h6>Involved parties:</h6>
          <div className="row p-1">
              {mockData[0].profile.map((customer) => (
              
              <div className="col">
                    <div className="col-3"><img src={customer.url} /></div>
                    <div className="col"><b>Name:</b> {customer.name}</div>
                    <div className="col"><b>Status:</b> {customer.status}</div>
                    <div className="col"><b>Eyes:</b></div>
                    <div className="col"><b>Ban Start:</b> somedate</div>
                    <div className="col"><b>Ban End:</b> never</div>
                    <div className="co1"><b>Something else:</b> Something</div>
              </div>
            ))}
          </div> 
        </div>  


        <div className="row">
            {mockData[0].boxes.map((boxes,i) => (
            <div key={i}>
                <div><h>{boxes.box}</h></div>
                <div className="var-box">{boxes.txt} </div>
            </div>))}
        </div>
        <div className="row">
          <h>Files</h>
          <div className="var-box">
          <BsFileEarmarkImage size="1.5em"/>
          <BsFillTrashFill size="1.5em"/>
          </div>
        </div>
        <div className="row">
          <h>Updates</h>
          <div className="var-box">
            updates here
          </div>
        </div>
      </div>
    </div>,
    document.getElementById("modal")
  );
};

export default ReportModal;
