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
          
        </div>

        <div className="col">
          
            <Form.Check
              type="switch"
              label="Box1 Source of Activity"
              checked={reportData.box1}
              onChange={(e) => {
                setReportData({ ...reportData, box1: !reportData.box1 });
              }}
            />
            <Form.Check
              type="switch"
              label="Box2 Investigation"
              checked={reportData.box2}
              onChange={(e) => {
                setReportData({ ...reportData, box2: !reportData.box2 });
              }}
            />
            <Form.Check
              type="switch"
              label="Box3 Resolution"
              checked={reportData.box3}
              onChange={(e) => {
                setReportData({ ...reportData, box3: !reportData.box3 });
              }}
            />
            <Form.Check
              type="switch"
              label="Box4 Conclusion"
              checked={reportData.box4}
              onChange={(e) => {
                setReportData({ ...reportData, box4: !reportData.box4 });
              }}
            />
            <Form.Check
              type="switch"
              label="Box5 Dispositional Information"
              checked={reportData.box5}
              onChange={(e) => {
                setReportData({ ...reportData, box5: !reportData.box5 });
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
        <div className="col"></div>
      </div>
     
    </div>
  );
}

export default ReportFormat;
