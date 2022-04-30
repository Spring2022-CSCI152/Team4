import React from "react";
import mockData from "./mockData";
import {RiKeyFill, RiUserAddFill, RiProfileFill, RiFileCopy2Fill} from "react-icons/ri";

const Admin = () => {
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
    <div className="row-4 login-inner area-padding">
      <h6>
        <RiKeyFill size="1.5em" style={{ fill: "#00f200" }}/> {"Admin Dashboard"}
      </h6>
      <div className="txt-align-center">
        <button className="btn green round"><RiUserAddFill /> Create Account</button>
        <button className="btn green round"><RiFileCopy2Fill /> Report Template</button>
        <button className="btn green round"><RiProfileFill /> Profile Template</button>
        <hr className="green txt-align-center"></hr>
      </div>        

      {/* Search Bar */}
      <div class="container-fluid p-3 card-label">
        <form class="d-flex ">
          <input class="form-control me-2" type="search" placeholder="Search Account" aria-label="Search" style={{ background: "#eeeeee" }}/>
          <button type="button" className="btn btn-outline-secondary" style={{ background: "#00f200" }}>Search</button>
        </form>
      </div>

      <div className="txt-align-center">{AcctSrcResult}</div>
    </div>
  );
};

export default Admin;
