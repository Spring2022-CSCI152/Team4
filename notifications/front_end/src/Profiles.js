import React from "react";
import mockData from "./mockData";
import {ImProfile} from "react-icons/im";


const Admin = () => {
  const [isOpen, setIsOpen] = useState(false);
  const AcctSrcResult = mockData.map((profiles) => {
    return profiles.profile.map((acct) => {
      return (
        <div className="card acct area-padding txt-align-center">
          <img src={acct.url} style={{ borderRadius: "50%" }} /> {acct.name}
        </div>
      );
    });
  });

  return (
    <div>
    <div className="row-4 area-padding">

      {/* Header */}
      <h6> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> {"Most Wanted Profiles"} </h6>

      {/* Search Bar */}
      <div className="container-fluid p-3 card-label">
        <form className="d-flex ">
          <input className="form-control me-2" type="search" placeholder="Search Profile" aria-label="Search" style={{ background: "#eeeeee" }}/>
          <button type="button" className="btn btn-outline-secondary" style={{ background: "#00f200" }}>Search</button>
        </form>
      </div>

      <div className="txt-align-center">{AcctSrcResult}</div>
    </div>
  </div>


  );



  
};

export default Admin;

