import React from "react";

const ReportTabs = ({setTab}) => {
  return (
  
    <div>
      <button className="btn" onClick={() => setTab("AllList")}>All</button>
      <button className="btn" onClick={() => setTab("WatchList")}>Watch</button>
      <button className="btn" onClick={() => setTab("BannedList")}>Banned</button>
      <button className="btn" onClick={() => setTab("UpdatedLogs")}>Updated Logs</button>
    </div>
  );
};

export default ReportTabs;
