import React, {useState, useEffect} from "react";
import ProfModal from "./components/ProfileModal";
import ProfileFormat from "./components/ProfileFormat";
// import mockData from "./mockData";
import {ImProfile} from "react-icons/im";
import prof_pic from './assets/prof_pic.png'
import axios from 'axios'
import Async from "react-async";

//   // load format
  const getProfileFormat = () => {
    {console.log("in")}


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

const ShowProfile = () => {

  <Async promiseFn={getProfileFormat()}>
    {console.log("clicked")}
    { ({data, error, isLoading}) => {
      console.log('data:',data)
      console.log('data address:',data.address)
      if(isLoading) return "Loading..."
      if(error) return `Something went wrong: ${error.message}`
      if(data){
          return <>
            <div> Hello </div>
            <div className="card acct area-padding txt-align-center mt-4"
              onClick={(e) => { modalListener.getClient().signal(true); }} > //setOpenProfModal is inside a different object, you will need middle man class to open it
              <img src={prof_pic} style={{ borderRadius: "50%" }} /> {}
            </div>
          </>
      }
      return null;
    }}
  </Async>
}

function setModalListener(){
  let listeners = [];
  let client = {
    signal: (data) => {
      listeners.forEach(fn => fn(data))
    } 
  }

  function on(listener){
    listeners.push(listener);
  }

  function off(listener){
    listeners = listeners.filter(l => l !== listener)
  }

  return {on, off, getClient: () => client}
}

function useOpenModal(){
  const [open, setOpen] = useState(null);

  useEffect(() => {
    function open(status){
      setOpen(status);
    }
    modalListener.on(open);
    return () => modalListener.off(open)
  }, [open, setOpen]);

  return open;
}
const modalListener = setModalListener();

function Profiles(){

  async function getProfileFormat(e){

    // newUser should be the user logged in
    const newUser = JSON.parse(localStorage.getItem("user")) 

    // newUsers accountId
    const accountId = {
      accountIdString: newUser.accountIdString,
      email: newUser.email,
      businessId: newUser.businessId
    }

    // get profile format (use post instead of get)
    const form2 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_profile_format`,
    {
      token: newUser.token,
      accountId: accountId,
    })
    .then(form2 => {
      console.log('form2 data: :', form2.data)
      console.log('res 2', form2.status)
      localStorage.setItem("profileCheckStates", JSON.stringify(form2.data)) // add states to localStorage
      const profileFormat = JSON.parse(localStorage.getItem(form2.data)) //retrive states from localStorage
    }).catch(error =>{
      console.log(error)
      console.log("Julie, it's NOT working :(")
    })
}

async function getAllProfiles(e){
      // newUser should be the user logged in
      const newUser = JSON.parse(localStorage.getItem("user")) 

      // newUsers accountId
      const accountId = {
        accountIdString: newUser.accountIdString,
        email: newUser.email,
        businessId: newUser.businessId
      }
  
      const state = {
        length: null
      }

      // get profile format (use post instead of get)
      const form2 = await axios.post(`${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_profiles`,
      {
        token: newUser.token,
        accountId: accountId,
      })
      .then(form2 => {
        console.log('form2 data: :', form2.data)
        console.log('res 2', form2.status)
        console.log("length", form2.data.content.length)       // How to access elements 
        localStorage.setItem("profileList", JSON.stringify(form2.data)) // add states to localStorage
        const profileList = JSON.parse(localStorage.getItem(form2.data)) //retrive states from localStorage

        this.setState({length: form2.data.content.length})
        console.log(profileList)

      }).catch(error =>{
        console.log(error)
        console.log("Julie, it's NOT working :(")
      })

}
  const open = useOpenModal();
  // Individual profile is not initially opened
  const [openProfModal, setOpenProfModal] = useState(false);

  useEffect(() => {
    setOpenProfModal(open);
  }, [open])
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
            > Search 
            </button>
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
            onClick={(e) => {{ ShowProfile() }}}
          > View Profiles 
          </button>
        </div>
      </div>
    </div>
  );
}

export default Profiles;