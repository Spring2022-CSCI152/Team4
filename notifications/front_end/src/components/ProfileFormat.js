import React, { useState, useEffect } from "react";
import { Form } from "react-bootstrap";

function ProfileFormat({profileData, setProfileData}) {
  
  useEffect(() => {
    const data = window.localStorage.getItem("profileCheckStates");
    if (data !== null) setProfileData(JSON.parse(data));
  }, []);

  useEffect(() => {
    window.localStorage.setItem("profileCheckStates", JSON.stringify(profileData));
  }, [profileData]);

  return (
    <div className="row justify-content-center">
      <h6>Select fields to display on profiles</h6>
      <div className="col"></div>
      <div className="col">
          <Form.Check
            type="switch"
            label="Name"
            checked={profileData.name}
            onChange={(e) => {
              setProfileData({ ...profileData, name: !profileData.name });
            }}
          />
          <Form.Check
            type="switch"
            label="Status"
            checked={profileData.status}
            onChange={(e) => {
              setProfileData({ ...profileData, status: !profileData.status });
            }}
          />
          <Form.Check
            type="switch"
            label="Address"
            checked={profileData.address}
            onChange={(e) => {
              setProfileData({ ...profileData, address: !profileData.address });
            }}
          />
          <Form.Check
            type="switch"
            label="Ban Duration"
            checked={profileData.banDuration}
            onChange={(e) => {
              setProfileData({ ...profileData, banDuration: !profileData.banDuration });
            }}
          />
      </div>

      <div className="col">
          <Form.Check
            type="switch"
            label="Attributes"
            checked={profileData.attributes}
            onChange={(e) => {
              setProfileData({ ...profileData, attributes: !profileData.attributes });
            }}
          />
          <Form.Check
            type="switch"
            label="Image Name"
            checked={profileData.imageName}
            onChange={(e) => {
              setProfileData({ ...profileData, imageName: !profileData.imageName });
            }}
          />
          <Form.Check
            type="switch"
            label="Involvement"
            checked={profileData.involvement}
            onChange={(e) => {
              setProfileData({ ...profileData, involvement: !profileData.involvement });
            }}
          />
          <Form.Check
            type="switch"
            label="Reports"
            checked={profileData.reports}
            onChange={(e) => {
              setProfileData({ ...profileData, reports: !profileData.reports });
            }}
          />
      </div>
      <div className="col"></div>
    </div>
  );
}

export default ProfileFormat;
