// import React, {useState, useEffect} from "react";
// import ProfModal from "./components/ProfileModal";
// import ProfileFormat from "./components/ProfileFormat";
// // import mockData from "./mockData";
// import {ImProfile} from "react-icons/im";
// import prof_pic from './assets/prof_pic.png'
// import axios from 'axios'
// import Async from "react-async";

// //   // load format
//   function getProfileFormat() {
//     {console.log("in")}

//     const url = `${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_report_format`

//     // newUser should be the user logged in
//     const User = JSON.parse(localStorage.getItem("user")) 

//     // newUsers accountId
//     const bodyData = {
//       token: User.token,
//       accountId: {
//           accountIdString: User.accountIdString,
//           email: User.email,
//           businessId: User.businessId 
//       }
//     }

//     return new Promise((resolve, reject) => {
//       axios.post(url, bodyData).then(res => {
//           console.log(res)
//           resolve(res.data)
//       })
//       .catch(err => {reject(err)})
//   })
// }

// function getProfile(){
//   return showProfile
// }

// const showProfile =()=> {
//   <Async promiseFn={getProfileFormat}>
//     { ({data, error, isLoading}) => {
//       console.log('data:',data)
//       console.log('data address:',data.address)
//       if(isLoading) return "Loading..."
//       if(error) return `Something went wrong: ${error.message}`
//       if(data){
//         return prof_pic
//       }
//       return null;
//     }}
//   </Async>
// }

// function setModalListener(){
//   let listeners = [];
//   let client = {
//     signal: (data) => {
//       console.log("signal")
//       listeners.forEach(fn => fn(data))
//     } 
//   }

//   function on(listener){
//     listeners.push(listener);
//   }

//   function off(listener){
//     listeners = listeners.filter(l => l !== listener)
//   }

//   return {on, off, getClient: () => client}
// }

// function useOpenModal(){
//   const [open, setOpen] = useState(null);

//   useEffect(() => {
//     function open(status){
//       setOpen(status);
//     }
//     modalListener.on(open);
//     return () => modalListener.off(open)
//   }, [open, setOpen]);

//   return open;
// }
// const modalListener = setModalListener();

// const Profiles = () => {

//   async function getProfileFormat(e){

//     // newUser should be the user logged in
//     const newUser = JSON.parse(localStorage.getItem("user")) 

//     // newUsers accountId
//     const accountId = {
//       accountIdString: newUser.accountIdString,
//       email: newUser.email,
//       businessId: newUser.businessId
//     }

//     // get profile format (use post instead of get)
//     const form2 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_profile_format`,
//     {
//       token: newUser.token,
//       accountId: accountId,
//     })
//     .then(form2 => {
//       console.log('form2 data: :', form2.data)
//       console.log('res 2', form2.status)
//       localStorage.setItem("profileCheckStates", JSON.stringify(form2.data)) // add states to localStorage
//       const profileFormat = JSON.parse(localStorage.getItem(form2.data)) //retrive states from localStorage
//     }).catch(error =>{
//       console.log(error)
//       console.log("Julie, it's NOT working :(")
//     })
// }

// async function getAllProfiles(e){
//       // newUser should be the user logged in
//       const newUser = JSON.parse(localStorage.getItem("user")) 

//       // newUsers accountId
//       const accountId = {
//         accountIdString: newUser.accountIdString,
//         email: newUser.email,
//         businessId: newUser.businessId
//       }
  
//       const state = {
//         length: null
//       }

//       // get profile format (use post instead of get)
//       const form2 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_profiles`,
//       {
//         token: newUser.token,
//         accountId: accountId,
//       })
//       .then(form2 => {
//         console.log('form2 data: :', form2.data)
//         console.log('res 2', form2.status)
//         console.log("length", form2.data.content.length)       // How to access elements 
//         localStorage.setItem("profileList", JSON.stringify(form2.data)) // add states to localStorage
//         const profileList = JSON.parse(localStorage.getItem(form2.data)) //retrive states from localStorage

//         this.setState({length: form2.data.content.length})
//         console.log(profileList)

//       }).catch(error =>{
//         console.log(error)
//         console.log("Julie, it's NOT working :(")
//       })

// }
//   const open = useOpenModal();

//   // Individual profile is not initially opened
//   const [openProfModal, setOpenProfModal] = useState(false);
//   const [profModal, setProfModal] = useState({prof_pic : null})

//   useEffect(() => {
//     setOpenProfModal(open);
//     onClick();
//   }, [open])

//   function onClick(){
//     if(openProfModal){
//       setProfModal({prof_pic : showProfile})
//     }
//   }

//   const getDisplayable = () => {
//     if(profModal.prof_pic !== null && !!profModal.prof_pic){
//       return (
//           <div className="card acct area-padding txt-align-center mt-4">
//             <img src={prof_pic} style={{ borderRadius: "50%" }} /> {}
//           </div>
//       )
//     }
//     else{
//       return (
//         <></>
//       )
//     }
//   }
//   return(
//     <div className="profiles"> 
//       <div className="row-4 area-padding">

//         {/* Header */}
//         <h6> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> {"Most Wanted Profiles"} </h6>

//         {/* Search Bar */}
//         <div className="container-fluid p-3 card-label">
//           <form className="d-flex ">
//             <input className="form-control me-2" 
//             type="search" 
//             placeholder="Search Profile" 
//             aria-label="Search" 
//             style={{ background: "#eeeeee" }}/>

//             <button type="button" 
//               className="btn btn-outline-secondary" 
//               style={{ background: "#00f200" }}
//             > Search 
//             </button>
//           </form>
//           <div className="card acct area-padding txt-align-center mt-4"
//             onClick={(e) => { setOpenProfModal(true); }} >
//             <img src={prof_pic} style={{ borderRadius: "50%" }} /> {}
//           </div>

//           {openProfModal && <ProfModal setOpenProfModal={setOpenProfModal} />}

//           <button
//             type="submit"
//             className="btn btn-dark btn-lg btn-block"
//             onClick={modalListener.getClient().signal(true)}
//           > View Profiles 
//           </button>

//           <>{getDisplayable}</>
//         </div>
//       </div>
//     </div>
//   );
// }

// export default Profiles;

import React, {useEffect, useState} from "react";
import ProfModal from "./components/ProfileModal";
// import mockData from "./mockData";
import {ImProfile} from "react-icons/im";
import prof_pic from './assets/prof_pic.png'
import axios from 'axios'
import Async from "react-async";

//   // load format
const getProfileFormat = () => {

    const url = `${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_report_format`

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

const Profiles = () => {

    const [modal, setModal] = useState({
        dispName: null,
        status: null,
        banStart: null,
        banEnd: null,
        street1: null,
        street2: null,
        city: null,
        state: null,
        zip: null,
        sex: null,
        race: null,
        eyeColor: null,
        hairColor: null,
        ageRange: null,
        weight: null
    });

    const [profileList, setProfileList] = useState([]);

    function displayModal(yeetedInfo){
        setModal({
            //yeeted info goes here
        })
    }

    function addProfile(profileInfo){
        setProfileList(profileList, profileInfo);
    }

    const getDisplayable = () => {
        if(!!modal && modal.someVal !== null){
            // return (<div>Name: {modal.dispName}</div>)
            return (
              <div className="view-modal-overlay">
              <div className="view-modal-inner">
  
                  {/* Header */}
                  <h3 className="text-align-left "> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> Most Wanted Profile</h3>
                  <hr className="green"></hr>
                  <button 
                      className="close-btn" fixed="top"
                      
                  > X </button>
  
                  {/* <div className="container"> */}
                      <div className="row">
              
                          <div className="col-3 border border-dark" style={{backgroundColor: "grey"}}> {/*style={{backgroundColor: "light grey"}}*/}
  
                              {/* Profile Image */}
                              <div className ="mt-3"> <img src={prof_pic} className="img-fluid img-thumbnail rounded-circle" alt="image not loading" /> </div>
  
                              {/* Status Fields*/}
                              <div className=" input-group input-group-sm mt-3" >
                                  <div className="form-control">Status</div>
                              </div>
                              {/* Status Label*/}
                              <div className="input-group input-group-sm pr-3">
                                  <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey"}}>Status</div> 
  
                              </div>
  
                              {/* Ban Duration Fields*/}
                              <div className=" input-group input-group-sm mt-3" >
                                  <div className="form-control">Ban Start</div>
                                  <div className="form-control">Ban End</div>
                              </div>
                              {/* Ban Duration Labels*/}
                              <div className="input-group input-group-sm pr-3">
                                  <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey"}}>Ban Start</div>   
                                  <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey"}}>Ban End</div>   
                              </div>
                          </div>
  
                      {/* Profile Information */}
                      <div className="col border border-dark ">
  
                          {/* Name Fields*/}
                          <div className=" input-group input-group-sm mt-4" >
                              <div className="form-control">Name</div>
                          </div>
                          {/* Name Labels*/}
                          <div className="input-group input-group-sm">
                              <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Name</div>
                          </div>
  
                          {/* Address: street 1 & 2 Fields */}
                          <div className=" input-group input-group-sm mt-4 " >
                              <div className="form-control">Street 1</div>
                              <div className="form-control">Street 2</div>
                          </div>
                          {/* Address: street 1 & 2 Labels */}
                          <div className="input-group input-group-sm">
                              <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Street 1</div>
                              <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Street 2</div>
                          </div>
  
                          {/* Address: city, state, zip Fields */}
                          <div className=" input-group input-group-sm mt-1" >
                              <div className="form-control">City</div>
                              <div className="form-control">State</div>
                              <div className="form-control">Zip Code</div>
                          </div>
                          {/* Address: city, state, zip Labels */}
                          <div className="input-group input-group-sm">
                              <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>City</div>
                              <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>State</div>
                              <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Zip Code</div>
                          </div>
  
                          {/* Attributes Fields */}
                          <div className=" input-group input-group-sm mt-4" >
                              <div className="form-control" style={{marginRight: "1rem"}}>Sex</div>
                              <div className="form-control" style={{marginRight: "1rem"}}>Race</div>
                              <div className="form-control" style={{marginRight: "1rem"}}>Eye Color</div>
                          </div>
                          {/* Attributes Labels */}
                          <div className="input-group input-group-sm">   
                              <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Sex</div>   
                              <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Race</div>   
                              <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Eye Color</div>   
                          </div>
  
                          {/* Attributes Fields*/}
                          <div className=" input-group input-group-sm mt-1" >
                              <div className="form-control" style={{marginRight: "1rem"}}>Hair Color</div>
                              <div className="form-control" style={{marginRight: "1rem"}}>Age Range</div>
                              <div className="form-control" style={{marginRight: "1rem"}}>Weight</div>
                          </div>
                          {/* Attributes Labels */}
                          <div className="input-group input-group-sm mb-2">     
                              <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Hair Color</div>  
                              <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Age Range</div> 
                              <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Weight</div> 
                          </div>
                      </div>
                  </div>
  
                  {/* List of Reports*/}
                  <div className="row">
                      <div className="border border-dark">Reports Listed Here ..</div>
                  </div>
              </div>
          </div> )
        }else{
            return (<></>)
        }
    }

    const displayableList = () => {
        //somehow return the profile list turned into buttons
    }

    <Async promiseFn={getProfileFormat}>
        { ({data, error, isLoading}) => {
            console.log('data:',data)
            console.log('data address:',data.address)
        if(isLoading) return (<>"Loading..."</>)
        if(error) return (<>`Something went wrong: ${error.message}`</>)
        if(data){
            // {getDisplayable}
            //Build Profile Lists
            /**
             * Each Profile Thingy Works like a button with onClick
             * 
             * onClick -> launch profile modal
             *  let buttons = []
             * data.profiles.forEach(profile => {
             *  addProfile(profile) 
             * })
             */
            return (
            <>  
                 {getDisplayable()}
                 {displayableList()}
            </>
            )
    
    }
    return null;
  }}
  </Async>
};


export default Profiles;