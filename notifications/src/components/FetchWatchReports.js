import React from "react";

const FetchWatchReports = ({ item }) => {
  return (
    <div className="card">
      <li>IR:{item.id} </li>
      <li>Name: {item.name} </li> 
      <li>Address street: {item.address.street}, city: {item.address.city}</li>
      <li>status: Watch</li>
    </div>
  );
};

export default FetchWatchReports;
