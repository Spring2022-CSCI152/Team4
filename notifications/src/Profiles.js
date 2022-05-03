import React, {useState} from "react";
import ProfModal from "./components/ProfileModal";
import ProfileFormat from "./components/ProfileFormat";
// import mockData from "./mockData";
import {ImProfile} from "react-icons/im";
import prof_pic from './assets/prof_pic.png'
import axios from 'axios'
import Async from "react-async";

//   // load format
  const getProfileFormat = () => {

    const url = "http://172.24.158.171:8080/api/v1/reports/get_report_format"

    // newUser should be the user logged in
    const User = JSON.parse(localStorage.getItem("user")) 

    // newUsers accountId
    const bodyData = {
      token: User.token,
      accountId: {
          accountIdString: User.accountIdString,
          email: User.email,
          businessId: User.businessId 
      }
    }

    return new Promise((resolve, reject) => {
      axios.post(url, bodyData).then(res => {
          console.log(res)
          resolve(res.data)
      })
      .catch(err => {reject(err)})
  })
}

const ShowProfile = () => (

  <Async promiseFn={getProfileFormat}>
  { ({data, error, isLoading}) => {
    console.log('data:',data)
    console.log('data address:',data.address)
    if(isLoading) return "Loading..."
    if(error) return `Something went wrong: ${error.message}`
  if(data){
        return <>{data.address} </>
  
    }
    return null;
  }}
  </Async>
  );


  // --------------------------------- this works
  // const AddReport = () => (

    // <Async promiseFn={getProfileFormat}>
    // { ({data, error, isLoading}) => {
    //   console.log('70 ',data)
    //   if(isLoading) return "Loading..."
    //   if(error) return `Something went wrong: ${error.message}`
    //   if(data){
    //       return <>{data.box2Name}

    //     <div className="card acct area-padding txt-align-center mt-4"
    //       onClick={(e) => { setOpenProfModal(true); }} >

    //       <img src={prof_pic} style={{ borderRadius: "50%" }} /> {data.box2Name}
    //     </div>
        
    //       </>
    //   }
    //   return  <ReportBoxes/>;
    // }}
    // </Async>
    // );
    
    // export default AddReport;
    // --------------------------------- delete


  // export default ShowProfile;


function Profiles(){



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

          {/* open profile modal when click on card */}
          <div className="card acct area-padding txt-align-center mt-4"
          onClick={(e) => { setOpenProfModal(true); }} >

          <img src={prof_pic} style={{ borderRadius: "50%" }} /> {}


          </div>
          {openProfModal && <ProfModal setOpenProfModal={setOpenProfModal} />}

          <button
            type="submit"
            className="btn btn-dark btn-lg btn-block"
            onClick={(e) => {
              { ShowProfile() }            
            }}
          > View Profiles </button>

        </div>
      </div>
    </div>
  );
}

export default Profiles;














