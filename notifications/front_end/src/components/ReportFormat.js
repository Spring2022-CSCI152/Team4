import { Form } from "react-bootstrap";
import React, { useState, useEffect } from "react";

function ReportFormat() {
  const [isCheck, setCheck] = useState({
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

  useEffect(() => {
    const data = window.localStorage.getItem("reportCheckStates");
    if (data !== null) setCheck(JSON.parse(data));
  }, []);

  useEffect(() => {
    window.localStorage.setItem("reportCheckStates", JSON.stringify(isCheck));
  }, [isCheck]);

  return (
    <div className=" justify-content-center">
      <h6>Select fields to display on Reports</h6>

      <div className="row">
        <div className="col">
          <Form>
            <Form.Check
              type="switch"
              label="Name"
              checked={isCheck.reportId}
              onChange={(e) => {
                setCheck({ ...isCheck, reportId: !isCheck.reportId });
              }}
            />
            <Form.Check
              type="switch"
              label="Status"
              checked={isCheck.profiles}
              onChange={(e) => {
                setCheck({ ...isCheck, profiles: !isCheck.profiles });
              }}
            />
            <Form.Check
              type="switch"
              label="date"
              checked={isCheck.date}
              onChange={(e) => {
                setCheck({ ...isCheck, date: !isCheck.date });
              }}
            />
            <Form.Check
              type="switch"
              label="time"
              checked={isCheck.time}
              onChange={(e) => {
                setCheck({ ...isCheck, time: !isCheck.time });
              }}
            />
            <Form.Check
              type="switch"
              label="author"
              checked={isCheck.author}
              onChange={(e) => {
                setCheck({ ...isCheck, author: !isCheck.author });
              }}
            />
            <Form.Check
              type="switch"
              label="type"
              checked={isCheck.type}
              onChange={(e) => {
                setCheck({ ...isCheck, type: !isCheck.type });
              }}
            />
          </Form>
        </div>

        <div className="col">
          <Form>
            <Form.Check
              type="switch"
              label="box1"
              checked={isCheck.box1}
              onChange={(e) => {
                setCheck({ ...isCheck, box1: !isCheck.box1 });
              }}
            />
            <Form.Check
              type="switch"
              label="box2"
              checked={isCheck.box1}
              onChange={(e) => {
                setCheck({ ...isCheck, box2: !isCheck.box2 });
              }}
            />
            <Form.Check
              type="switch"
              label="box3"
              checked={isCheck.box3}
              onChange={(e) => {
                setCheck({ ...isCheck, box3: !isCheck.box3 });
              }}
            />
            <Form.Check
              type="switch"
              label="box4"
              checked={isCheck.box4}
              onChange={(e) => {
                setCheck({ ...isCheck, box4: !isCheck.box4 });
              }}
            />
            <Form.Check
              type="switch"
              label="box5"
              checked={isCheck.box5}
              onChange={(e) => {
                setCheck({ ...isCheck, box5: !isCheck.box5 });
              }}
            />
            <Form.Check
              type="switch"
              label="attachments"
              checked={isCheck.attachments}
              onChange={(e) => {
                setCheck({ ...isCheck, attachments: !isCheck.attachments });
              }}
            />
            <Form.Check
              type="switch"
              label="changeLog"
              checked={isCheck.changeLog}
              onChange={(e) => {
                setCheck({ ...isCheck, changeLog: !isCheck.changeLog });
              }}
            />
          </Form>
        </div>
      </div>
    </div>
  );
}

export default ReportFormat;
