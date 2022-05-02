import React, { useState, useEffect } from "react";
import { Form } from "react-bootstrap";

function ProfileFormat() {
  const [isCheck, setCheck] = useState({
    name: false,
    status: true,
    address: true,
    banDuration: true,
    attributes: true,
    imageName: true,
    involvement: true,
    reports: true,
  });

  useEffect(() => {
    const data = window.localStorage.getItem("profileCheckStates");
    if (data !== null) setCheck(JSON.parse(data));
  }, []);

  useEffect(() => {
    window.localStorage.setItem("profileCheckStates", JSON.stringify(isCheck));
  }, [isCheck]);

  return (
    <div className="row justify-content-center">
      <h6>Select fields to display on profiles</h6>
      <div className="col">
        <Form>
          <Form.Check
            type="switch"
            label="Name"
            checked={isCheck.name}
            onChange={(e) => {
              setCheck({ ...isCheck, name: !isCheck.name });
            }}
          />
          <Form.Check
            type="switch"
            label="Status"
            checked={isCheck.status}
            onChange={(e) => {
              setCheck({ ...isCheck, status: !isCheck.status });
            }}
          />
          <Form.Check
            type="switch"
            label="Address"
            checked={isCheck.address}
            onChange={(e) => {
              setCheck({ ...isCheck, address: !isCheck.address });
            }}
          />
          <Form.Check
            type="switch"
            label="Ban Duration"
            checked={isCheck.banDuration}
            onChange={(e) => {
              setCheck({ ...isCheck, banDuration: !isCheck.banDuration });
            }}
          />
        </Form>
      </div>

      <div className="col">
        <Form>
          <Form.Check
            type="switch"
            label="Attributes"
            checked={isCheck.attributes}
            onChange={(e) => {
              setCheck({ ...isCheck, attributes: !isCheck.attributes });
            }}
          />
          <Form.Check
            type="switch"
            label="Image Name"
            checked={isCheck.imageName}
            onChange={(e) => {
              setCheck({ ...isCheck, imageName: !isCheck.imageName });
            }}
          />
          <Form.Check
            type="switch"
            label="Involvement"
            checked={isCheck.involvement}
            onChange={(e) => {
              setCheck({ ...isCheck, involvement: !isCheck.involvement });
            }}
          />
          <Form.Check
            type="switch"
            label="Reports"
            checked={isCheck.reports}
            onChange={(e) => {
              setCheck({ ...isCheck, reports: !isCheck.reports });
            }}
          />
        </Form>
      </div>
    </div>
  );
}

export default ProfileFormat;
