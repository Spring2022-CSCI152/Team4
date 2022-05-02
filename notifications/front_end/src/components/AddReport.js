import US_states from "./US-states";
import mockData from "../mockData";
import { Button } from "react-bootstrap";
import { ImDrive } from "react-icons/im";
import {useState, useEffect} from "react";
import axios from 'axios';
import React from "react";
import Test from  './test';
import usePromise from "react-promise";
import Async from "react-async";


// const AddReport = () => {

//   const url = "http://172.24.158.171:8080/api/v1/reports/get_report_format"
//   const User = JSON.parse(localStorage.getItem("User"))
//   const bodyData = {
//       token: User.token,
//       accountId: {
//           accountIdString: User.accountIdString,
//           email: User.email,
//           businessId: User.businessId 
//       }
//   }
//     console.log("outside!")
    
    
//     const obj = {data: ''}

//     const response = await(axios.post(url, bodyData))

//     obj.data = response.data

//     console.log('40',obj)
//     return (<>{obj.data.accountIdString}</>)
    
// }

const loadFormat = ()=>{

  const url = "http://172.24.158.171:8080/api/v1/reports/get_report_format"
  const User = JSON.parse(localStorage.getItem("User"))
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
    .catch(err => reject(err))
})
}

const AddReport = () => (

<Async promiseFn={loadFormat}>
{ ({data, error, isLoading}) => {
  console.log('70 ',data)
  if(isLoading) return "Loading..."
  if(error) return `Something went wrong: ${error.message}`
  if(data){
      return <>{data.box2Name}</>
  }
  return null;
}}
</Async>
);

export default AddReport;



// const AddReport = () => {
//   const [formats, setFormats] = useState()
  


//   const User = JSON.parse(localStorage.getItem("User"))
//   const bodyData = {
//     token: User.token,
//     accountId :{
//       accountIdString: User.accountIdString,
//       email: User.email,
//       businessId: User.businessId
//     }
//   }
//   const url = "http://172.24.158.171:8080/api/v1/reports/get_report_format"


    // const form = axios.post(url, bodyData)
    // .then(form => {
    //   console.log(form.data)

    //   if (!formats){
    //     setFormats(form.data)
    //   }
    //   else{
    //     let needsUpdate = false
    //     Object.keys(form.data).forEach(key=>{
    //       if (form.data[key] != formats[key]){
    //         needsUpdate = true
    //       }
    //     })
    //     if (needsUpdate) {
    //       setFormats(form.data)
    //     } 
    //   }


// let needsUpdate = false

// function getFormats(){
//   if(!formats){
//     needsUpdate = true
// }
// if(needsUpdate)
// {
//    axios.post(url, bodyData).then(form => {
//         if(!!formats){
//             console.log(formats)
//             Object.key(form.data).forEach(key => {
//                     if(form.data[key] !== formats[key]){
//                         needsUpdate = true;
//                     }
//                 })
//         }
//         else if(needsUpdate || !formats){
//           console.log("!formats")
//             needsUpdate = false;
//             setFormats(form.data);
//         }
//     })
//   }
// return formats;
// }

// const {form, loading} = usePromise(Promise.resolve(
//   getFormats()
// ))

// if (loading || !form){
//   return null;
// }

// return <>{form.accountIdString}</>
  // function some(){
    
  //   if(!formats){
  //     return <></>
  //   }else{
  //     console.log(formats)
  //     return <>{formats.accountIdString}</>
  //   }
  // }
  
 

  //  // Object.values(formats).forEach(var => {return var};)
  
  
  // );
//};

    

//     <form className="row m-2">
//       <h3>New Incident</h3>
//       <div className="col-3">
//         <label htmlFor="inputAuthor" className="form-label">Author</label>
//         <input type="text" className="form-control" id="inputTime" />
//       </div>
//       <div className="col-2 mb-3">
//         <label htmlFor="inputDate" className="form-label">Incident Date</label>
//         <input type="date" className="form-control" id="inputDate" />
//       </div>

    
     
    


//       <div className="col-2">
//         <label htmlFor="inputTime" className="form-label">Time</label>
//         <input type="time" className="form-control" id="inputTime" />
//       </div>
//       <div className="col-2">
//         <label htmlFor="inputType" className="form-label">Location</label>
//         <input type="text" className="form-control" id="inputType" />
//       </div>
//       <div className="col-2">
//         <label htmlFor="inputTime" className="form-label">Incident Type</label>
//         <select className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
//           <option defaultValue={""}>Verbal</option>
//           <option value="1">Physical</option>
//           <option value="2">Warning</option>
//         </select>
//       </div>
//       <hr className="green"></hr>
//       <h6>Involved Parties</h6>
//       <div className="col-12 mb-3">
//         <label htmlFor="inputProfile" className="form-label">Existing Profiles </label>
//         <select className="w-auto form-select-md col-2 dropdown" aria-label="Default select example">
//           <option defaultValue={"..."}></option>

//           {mockData.map((item) => {
//             return (
//               <>
//                 {item.profile.map((step, i) => {
//                   return (
//                     <option key={i}>
//                       {step.name}
//                     </option>
//                   );
//                 })}
//               </>
//             );
//           })}
//         </select>
//       </div>

//       <div className="col-md-3 mb-3">
//         <label htmlFor="inputName" className="form-label">Name</label>
//         <input type="text" className="form-control" id="inputName" />
//       </div>

//       <div className="col-2">
//         <label htmlFor="inputStatus" className="form-label">Status</label>
//         <select className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
//           <option defaultValue={""}>Watch</option>
//           <option value="1">Warning</option>
//           <option value="2">Ban</option>
//         </select>
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputBanStart" className="form-label">Ban Start Date</label>
//         <input type="date" className="form-control" id="inputBanStart" />
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputBanEnd" className="form-label">Ban End Date</label>
//         <input type="date" className="form-control" id="inputBanEnd" />
//       </div>
//       <div className="col-fill"></div>

//       {/* Profile Attributes */}

//       <div className="col-2 mb-3">
//         <label htmlFor="inputSex" className="form-label">Sex</label>
//         <input type="text" className="form-control" id="inputSex" />
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputAgeRange" className="form-label">Age Range</label>
//         <input type="text" className="form-control" id="inputAgeRange" />
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputDob" className="form-label">Date of Birth</label>
//         <input type="date" className="form-control" id="inputDob" />
//       </div>

//       <div className="col-2">
//         <label htmlFor="inputEyeColor" className="form-label">Eye Color</label>
//         <select className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
//           <option defaultValue={""}>Brown</option>
//           <option value="1">Blue</option>
//           <option value="2">Green</option>
//         </select>
//       </div>

//       <div className="col-2">
//         <label htmlFor="inputHairColor" className="form-label">Hair Color</label>
//         <select className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
//           <option defaultValue={""}>Brown</option>
//           <option value="1">Blonde</option>
//           <option value="2">Black</option>
//           <option value="2">Red</option>
//         </select>
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputHeightFt" className="form-label">Height: Ft</label>
//         <input type="number" className="form-control" id="inputHeightFt" />
//       </div>
//       <div className="col-2 mb-3">
//         <label htmlFor="inputHeightIn" className="form-label">In</label>
//         <input type="number" className="form-control" id="inputHeightIn" />
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputWeight" className="form-label">Weight</label>
//         <input type="number" className="form-control" id="inputWeight" />
//       </div>

//       <div className="col-2 mb-3">
//         <label htmlFor="inputRace" className="form-label">Race</label>
//         <input type="text" className="form-control" id="inputRace" />
//       </div>

// {/* Profile Address */}
//       <div className="col-md-5 mb-3">
//         <label htmlFor="inputAddress2" className="form-label">Address</label>
//         <input type="text" className="form-control" id="inputAddress2" />
//       </div>
//       <div className="col-md-3">
//         <label htmlFor="inputCity" className="form-label">City</label>
//         <input type="text" className="form-control" id="inputCity" />
//       </div>
//       <div className="col-md-1">
//         <label htmlFor="inputState" className="form-label">State</label>
//         <select id="inputState" className="form-select">
//           <option>...</option>
//           {US_states.map((item, i) => { return (<option key={i}> {item} </option>) })}
//         </select>
//       </div>
//       <div className="col-md-2">
//         <label htmlFor="inputZip" className="form-label">Zip</label>
//         <input type="text" className="form-control" id="inputZip" />
//       </div>

//       {/* Upload Image*/}

//       <div className="mt-3 mb-2"> <input type="file"
//         onChange={
//           (event) => { uploadImageHandler(event.target.files); }
//         } />
//       </div>
      
//       <div className="col-12">
//         <button type="submit" className="btn green btn-sm">Add Person</button>
//         <hr className="green"></hr>
//       </div>

//     </form>
 

