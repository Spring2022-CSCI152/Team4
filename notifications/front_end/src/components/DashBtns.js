import React from "react";
import {FaPlus, FaChevronUp} from 'react-icons/fa';

const DashBtns = ({setTab}) => {
  return (
    <div>
      <button className="btn green round" onClick={() => setTab("")}> <FaPlus/> Add Report</button>
      <button className="btn green round" onClick={() => setTab("")}> <FaChevronUp/> Updated Logs</button>
    </div>
  );
};

export default DashBtns;
