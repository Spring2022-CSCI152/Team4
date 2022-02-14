import React from "react";

const FetchAllReports = ({ item }) => {
  return (
    <div className="card">
      <li>IR: {item.id} </li>
      <li><img src = {item.thumbnailUrl}/></li>
      <li>Name: {item.title} </li>
    </div>
  );
};

export default FetchAllReports;
