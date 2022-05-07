import React, {useState} from "react";
import ProfModal from "./components/ProfileModal";
import ProfileFormat from "./components/ProfileFormat";
import mockData from "./mockData";
import {ImProfile} from "react-icons/im";
import prof_pic from './assets/prof_pic.png'
import axios from 'axios'

function Profiles(){

    const AcctSrcResult = mockData.map((profiles) => {
        return profiles.profile.map((acct) => {
          return (
            <div className="card acct area-padding txt-align-center">
              <img src={acct.url} style={{ borderRadius: "50%" }} /> {acct.name}
            </div>
          );
        });
    });

  // Individual profile is not initially opened
  const [openProfModal, setOpenProfModal] = useState(false);

  return(
    <div className="profiles"> 
      <div className="row-4 area-padding">

        {/* Header */}
        <h6> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> {"Most Wanted Profiles"} </h6>

        {/* Search Bar */}
        <div className="container-fluid p-3 card-label">
          <form className="d-flex ">
            <input className="form-control me-2" 
            type="search" 
            placeholder="Search Profile" 
            aria-label="Search" 
            style={{ background: "#eeeeee" }}/>
            <button type="button" 
              className="btn btn-outline-secondary" 
              style={{ background: "#00f200" }}
              > Search </button>
          </form>
        </div>

            <div className="txt-align-center"
                onClick={(e) => { setOpenProfModal(true); }} >
                {AcctSrcResult}
            </div>

            {openProfModal && <ProfModal setOpenProfModal={setOpenProfModal} />}








      </div>
    </div>
  );
}

export default Profiles;
