import React from "react";
import {FaPlus, FaChevronUp} from 'react-icons/fa';
import { useNavigate } from "react-router-dom";
import AddReport from "./AddReport";


const DashBtns = () => {
  return (
    <div className="d-flex justify-content-center">
      <button className="btn green round" onClick={ ()=>useNavigate("/AddReport")}> <FaPlus/> Add Report</button>
      <button className="btn green round"> <FaChevronUp/> Updated Logs</button>
    </div>
  );
};

export default DashBtns;
