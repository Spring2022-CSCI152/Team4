import { Form } from "react-bootstrap";
import React, { useState, useEffect } from "react";

function ReportFormat({ reportData, setReportData }) {
  async function clear_box1(e) {
    document.getElementById('box1').value = ""
  };
  async function clear_box2(e) {
    document.getElementById('box2').value = ""
  };
  async function clear_box3(e) {
    document.getElementById('box3').value = ""
  };
  async function clear_box4(e) {
    document.getElementById('box4').value = ""
  };
  async function clear_box5(e) {
    document.getElementById('box5').value = ""
  };

  useEffect(() => {
    const data = window.localStorage.getItem("reportCheckStates");
    if (data !== null) setReportData(JSON.parse(data));
  }, []);

  useEffect(() => {
    window.localStorage.setItem(
      "reportCheckStates",
      JSON.stringify(reportData)
    );
  }, [reportData]);

  return (
    <div className=" justify-content-center">
      <h6>Select fields to display on Reports</h6>

      <div className="row">
        <div className="col">
          <Form.Check
            className="pt-2"
            type="switch"
            label="Name"
            checked={reportData.reportId}
            onChange={(e) => {
              setReportData({ ...reportData, reportId: !reportData.reportId });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Status"
            checked={reportData.profiles}
            onChange={(e) => {
              setReportData({ ...reportData, profiles: !reportData.profiles });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Date"
            checked={reportData.date}
            onChange={(e) => {
              setReportData({ ...reportData, date: !reportData.date });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Time"
            checked={reportData.time}
            onChange={(e) => {
              setReportData({ ...reportData, time: !reportData.time });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Author"
            checked={reportData.author}
            onChange={(e) => {
              setReportData({ ...reportData, author: !reportData.author });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Type"
            checked={reportData.type}
            onChange={(e) => {
              setReportData({ ...reportData, type: !reportData.type });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Attachments"
            checked={reportData.attachments}
            onChange={(e) => {
              setReportData({
                ...reportData,
                attachments: !reportData.attachments,
              });
            }}
          />
          <Form.Check
            className="pt-2"
            type="switch"
            label="Change Log"
            checked={reportData.changeLog}
            onChange={(e) => {
              setReportData({
                ...reportData,
                changeLog: !reportData.changeLog,
              });
            }}
          />
        </div>

        {/* Boxes */}
        <div className="col-2">
          <Form.Check
            className="pt-2"
            type="switch"
            label="Box1"
            checked={reportData.box1}
            onChange={(e) => {
              clear_box1(e)
              setReportData({ ...reportData, box1: !reportData.box1 });
            }}
          />

          <Form.Check
            className="pt-2"
            type="switch"
            label="Box2"
            checked={reportData.box2}
            onChange={(e) => {
              clear_box2(e)
              setReportData({ ...reportData, box2: !reportData.box2 });
            }}
          />

          <Form.Check
            className="pt-2"
            type="switch"
            label="Box3"
            checked={reportData.box3}
            onChange={(e) => {
              clear_box3(e)
              setReportData({ ...reportData, box3: !reportData.box3 });
            }}
          />

          <Form.Check
            className="pt-2"
            type="switch"
            label="Box4"
            checked={reportData.box4}
            onChange={(e) => {
              clear_box4(e)
              setReportData({ ...reportData, box4: !reportData.box4 });
            }}
          />

          <Form.Check
            className="pt-2"
            type="switch"
            label="Box5"
            checked={reportData.box5}
            onChange={(e) => {
              clear_box5(e)
              setReportData({ ...reportData, box5: !reportData.box5 });
            }}
          />
        </div>

        {/* Box Entries */}
        <div className="col">
          <div>
            <input
              id = "box1"
              disabled={!reportData.box1}
              className="mt-3"
              type="text"
              placeholder="Enter report field"
              defaultValue={reportData.box1Name}
              onChange={(e) => {
                console.log(!reportData.box1);
                setReportData({ ...reportData, box1Name: e.target.value });
              }}
            />
          </div>

          <div>
            <input
              id = "box2"
              disabled={!reportData.box2}
              className="mt-3"
              type="text"
              placeholder="Enter report field"
              defaultValue={reportData.box2Name}
              onChange={(e) => {
                setReportData({ ...reportData, box2Name: e.target.value });
              }}
            />
          </div>

          <div>
            <input
              id = "box3"
              disabled={!reportData.box3}
              className="mt-3"
              type="text"
              placeholder="Enter report field"
              defaultValue={reportData.box3Name}
              onChange={(e) => {
                setReportData({ ...reportData, box3Name: e.target.value });
              }}
            />
          </div>

          <div>
            <input
              id = "box4"
              disabled={!reportData.box4}
              className="mt-3"
              type="text"
              placeholder="Enter report field"
              defaultValue={reportData.box4Name}
              onChange={(e) => {
                setReportData({ ...reportData, box4Name: e.target.value });
              }}
            />
          </div>

          <div>
            <input
              id = "box5"
              disabled={!reportData.box5}
              className="mt-3"
              type="text"
              placeholder="Enter report field"
              defaultValue={reportData.box5Name}
              onChange={(e) => {
                setReportData({ ...reportData, box5Name: e.target.value });
              }}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReportFormat;
