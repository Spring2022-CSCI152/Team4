import React, { useState } from "react";
import { Button } from "react-bootstrap";
import { Outlet, Link } from "react-router-dom";
import {FaPlus, FaChevronUp} from 'react-icons/fa';


const Reports =()=>{
  return (
    <>
    <div className="d-flex justify-content-center area-padding">
    <Link to="/Reports/AddReport"> <Button className="green text-underline">  <FaPlus/> Add Report</Button></Link>
    <Link to="/Reports/UpdatedLogs"> <Button className="green text-underline">  <FaChevronUp/>Updated Logs</Button></Link>   
   </div>
    <Outlet/>
    </>
    );
};
export default Reports;
