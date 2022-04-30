import { Form } from "react-bootstrap";
import React, { useState, useEffect } from "react";

function ReportFormat({reportData,setReportData}) {
  

  useEffect(() => {
    const data = window.localStorage.getItem("reportCheckStates");
    if (data !== null) setReportData(JSON.parse(data));
  }, []);

  useEffect(() => {
    window.localStorage.setItem("reportCheckStates", JSON.stringify(reportData));
  }, [reportData]);

  return (
    <div className=" justify-content-center">
      <h6>Select fields to display on Reports</h6>
    
      <div className="row">
        <div className="col"></div>
        <div className="col">
            <Form.Check
              type="switch"
              label="Name"
              checked={reportData.reportId}
              onChange={(e) => {
                setReportData({ ...reportData, reportId: !reportData.reportId });
              }}
            />
            <Form.Check
              type="switch"
              label="Status"
              checked={reportData.profiles}
              onChange={(e) => {
                setReportData({ ...reportData, profiles: !reportData.profiles });
              }}
            />
            <Form.Check
              type="switch"
              label="date"
              checked={reportData.date}
              onChange={(e) => {
                setReportData({ ...reportData, date: !reportData.date });
              }}
            />
            <Form.Check
              type="switch"
              label="time"
              checked={reportData.time}
              onChange={(e) => {
                setReportData({ ...reportData, time: !reportData.time });
              }}
            />
            <Form.Check
              type="switch"
              label="author"
              checked={reportData.author}
              onChange={(e) => {
                setReportData({ ...reportData, author: !reportData.author });
              }}
            />
            <Form.Check
              type="switch"
              label="type"
              checked={reportData.type}
              onChange={(e) => {
                setReportData({ ...reportData, type: !reportData.type });
              }}
            />
              <Form.Check
              type="switch"
              label="Attachments"
              checked={reportData.attachments}
              onChange={(e) => {
                setReportData({ ...reportData, attachments: !reportData.attachments });
              }}
            />
            <Form.Check
              type="switch"
              label="Change Log"
              checked={reportData.changeLog}
              onChange={(e) => {
                setReportData({ ...reportData, changeLog: !reportData.changeLog });
              }}
            />
          
        </div>

        <div className="col">
          
            <Form.Check
              type="switch"
              label="Box1"
              checked={reportData.box1}
              onChange={(e) => {
                setReportData({ ...reportData, box1: !reportData.box1 }); 
              }}/>
             
              <input
              disabled = {!reportData.box1 }
              className="mb-3"
              type="text"
              placeholder="Box1 Name"
              defaultValue={reportData.box1Name}
              onChange={(e) => {
                setReportData({ ...reportData, box1Name: e.target.value });
              }}
              />

            <Form.Check
              type="switch"
              label="Box2"
              checked={reportData.box2}
              onChange={(e) => {
                setReportData({ ...reportData, box2: !reportData.box2 });
              }}
            />
              <input
              disabled = {!reportData.box2 }
              className="mb-3"
              type="text"
              placeholder="Box1 Name"
              defaultValue={reportData.box2Name}
              onChange={(e) => {
                setReportData({ ...reportData, box2Name: e.target.value });
              }}
              />
            <Form.Check
              type="switch"
              label="Box3"
              checked={reportData.box3}
              onChange={(e) => {
                setReportData({ ...reportData, box3: !reportData.box3 });
              }}
            />
              <input
              disabled = {!reportData.box3 }
              className="mb-3"
              type="text"
              placeholder="Box3 Name"
              defaultValue={reportData.box3Name}
              onChange={(e) => {
                setReportData({ ...reportData, box3Name: e.target.value });
              }}
              />
            <Form.Check
              type="switch"
              label="Box4"
              checked={reportData.box4}
              onChange={(e) => {
                setReportData({ ...reportData, box4: !reportData.box4 });
              }}
            />
            <input
              disabled = {!reportData.box4 }
              className="mb-3"
              type="text"
              placeholder="Box3 Name"
              defaultValue={reportData.box4Name}
              onChange={(e) => {
                setReportData({ ...reportData, box4Name: e.target.value });
              }}
              />
            <Form.Check
              type="switch"
              label="Box5"
              checked={reportData.box5}
              onChange={(e) => {
                setReportData({ ...reportData, box5: !reportData.box5 });
              }}
            />
             <input
              disabled = {!reportData.box5 }
              className="mb-3"
              type="text"
              placeholder="Box5 Name"
              defaultValue={reportData.box4Name}
              onChange={(e) => {
                setReportData({ ...reportData, box5Name: e.target.value });
              }}
              />
          
        </div>
        <div className="col"></div>
      </div>
     
    </div>
  );
}

export default ReportFormat;
