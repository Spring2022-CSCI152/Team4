import US_states from "./US-states";
import mockData from "../mockData";
import axios from "axios";
import React from "react";
import ReportBoxes from "./ReportBoxes";
import mockDataReport from "../mockDataReport";

function AddReport() {



  return (
    <>
      <form className="row m-2">
        <h3>New Incident</h3>
        <div className="col-3">
          <label htmlFor="inputAuthor" className="form-label">Author</label>
          <input
            disabled={!mockDataReport.author}
            type="text"
            className="form-control"
            id="inputTime"
          />
        </div>
        <div className="col-2 mb-3">
          <label htmlFor="inputDate" className="form-label">Incident Date</label>
          <input
            disabled={!mockDataReport.date}
            type="date"
            className="form-control"
            id="inputDate" />
        </div>
        <div className="col-2">
          <label htmlFor="inputTime" className="form-label">Time</label>
          <input
            disabled={!mockDataReport.time}
            type="time"
            className="form-control"
            id="inputTime" />
        </div>
        <div className="col-2">
          <label htmlFor="inputTime" className="form-label">Incident Type</label>
          <select disabled={!mockDataReport.type} className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
            <option defaultValue={""}>Verbal</option>
            <option value="1">Physical</option>
            <option value="2">Warning</option>
          </select>
        </div>

        <hr className="green"></hr>

        <h6><b>Profiles</b></h6>
        <div className="col-12 mb-3">
          <label htmlFor="inputProfile" className="form-label">Existing Profiles </label>
          <select disabled={!mockDataReport.profiles} className="w-auto form-select-md col-2 dropdown" aria-label="Default select example">
            <option defaultValue={"..."}></option>

            {mockData.map((item) => {
              return (
                <>
                  {item.profile.map((step, i) => {
                    return (
                      <option key={i}>
                        {step.name}
                      </option>
                    );
                  })}
                </>
              );
            })}
          </select>
        </div>

        <div className="col-md-3 mb-3">
          <label htmlFor="inputName" className="form-label">Name</label>
          <input disabled={!mockDataReport.profiles} type="text" className="form-control" id="inputName" />
        </div>

        <div className="col-2">
          <label htmlFor="inputStatus" className="form-label">Status</label>
          <select disabled={!mockDataReport.profiles} className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
            <option defaultValue={""}>Watch</option>
            <option value="1">Warning</option>
            <option value="2">Ban</option>
          </select>
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputBanStart" className="form-label">Ban Start Date</label>
          <input disabled={!mockDataReport.profiles} type="date" className="form-control" id="inputBanStart" />
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputBanEnd" className="form-label">Ban End Date</label>
          <input disabled={!mockDataReport.profiles} type="date" className="form-control" id="inputBanEnd" />
        </div>
        <div className="col-fill"></div>

        {/* Profile Attributes */}


        <div className="col-2 mb-3">
          <label htmlFor="inputSex" className="form-label">Sex</label>
          <input disabled={!mockDataReport.profiles} type="text" className="form-control" id="inputSex" />
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputAgeRange" className="form-label">Age Range</label>
          <input disabled={!mockDataReport.profiles} type="text" className="form-control" id="inputAgeRange" />
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputDob" className="form-label">Date of Birth</label>
          <input disabled={!mockDataReport.profiles} type="date" className="form-control" id="inputDob" />
        </div>

        <div className="col-2">
          <label htmlFor="inputEyeColor" className="form-label">Eye Color</label>
          <select disabled={!mockDataReport.profiles} className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
            <option defaultValue={""}>Brown</option>
            <option value="1">Blue</option>
            <option value="2">Green</option>
          </select>
        </div>

        <div className="col-2">
          <label htmlFor="inputHairColor" className="form-label">Hair Color</label>
          <select disabled={!mockDataReport.profiles} className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
            <option defaultValue={""}>Brown</option>
            <option value="1">Blonde</option>
            <option value="2">Black</option>
            <option value="2">Red</option>
          </select>
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputHeightFt" className="form-label">Height: Ft</label>
          <input disabled={!mockDataReport.profiles} type="number" className="form-control" id="inputHeightFt" />
        </div>
        <div className="col-2 mb-3">
          <label htmlFor="inputHeightIn" className="form-label">In</label>
          <input disabled={!mockDataReport.profiles} type="number" className="form-control" id="inputHeightIn" />
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputWeight" className="form-label">Weight</label>
          <input disabled={!mockDataReport.profiles} type="number" className="form-control" id="inputWeight" />
        </div>

        <div className="col-2 mb-3">
          <label htmlFor="inputRace" className="form-label">Race</label>
          <input disabled={!mockDataReport.profiles} type="text" className="form-control" id="inputRace" />
        </div>


        {
          /* Profile Address */
        }

        <div className="col-md-5 mb-3">
          <label htmlFor="inputAddress2" className="form-label">Address</label>
          <input disabled={!mockDataReport.profiles} type="text" className="form-control" id="inputAddress2" />
        </div>
        <div className="col-md-3">
          <label htmlFor="inputCity" className="form-label">City</label>
          <input disabled={!mockDataReport.profiles} type="text" className="form-control" id="inputCity" />
        </div>
        <div className="col-md-1">
          <label htmlFor="inputState" className="form-label">State</label>
          <select disabled={!mockDataReport.profiles} id="inputState" className="form-select">
            <option>...</option>
            {US_states.map((item, i) => { return (<option key={i}> {item} </option>) })}
          </select>
        </div>
        <div className="col-md-2">
          <label htmlFor="inputZip" className="form-label">Zip</label>
          <input disabled="false" type="text" className="form-control" id="inputZip" />
        </div>


        {
          /* Upload Image*/
        }


        {/* <div className="mt-3 mb-2"> <input disabled="false" type="file"
        onChange={
         // (event) => { uploadImageHandler(event.target.files); }
        
        } /> 
      </div>*/}

        <div className="col-12">
          <button type="submit" className="btn green btn-sm">Add Person</button>
          <hr className="green"></hr>
        </div>
      </form>
      <ReportBoxes />
    </>
  )
}



// function loadFormat() {
//   const url = `${process.env.REACT_APP_JAVA_SERVER}/api/v1/reports/get_report_format`;
//   const User = JSON.parse(localStorage.getItem("user"));
//   const bodyData = {
//     token: User.token,
//     accountId: {
//       accountIdString: User.accountIdString,
//       email: User.email,
//       businessId: User.businessId,
//     },
//   };

//   return new Promise((resolve, reject) => {
//     axios
//       .post(url, bodyData)
//       .then((res) => {
//         console.log(res);
//         resolve(res.data);
//       })
//       .catch((err) => {
//         reject(err);
//       });
//   });
// }

// const AddReport = () => (
//   <Async promiseFn={loadFormat}>
//     {({ data, error, isLoading }) => {
//       const boxesNames = [];
//       let bid;
//       let profile;
//       let changeLog;
//       let type;
//       if (isLoading) return "Loading...";
//       if (error) return `Something went wrong: ${error.message}`;
//       if (data) {
//         console.log(data);
//         //filters
//         for (const [key, value] of Object.entries(data)) {
//           if (
//             !key.includes("box") &&
//             key != "businessId" &&
//             key != "profiles" &&
//             key != "changeLog" &&
//             key != "type"
//           ) {
//             boxesNames.push(key);
//           }
//           if (key == "businessId") {
//             bid = value;
//           }
//           if (key == "profiles" && value == true) {
//             profile = key;
//           }
//           if (key == "changeLog" && value == true) {
//             changeLog = key;
//           }
//           if (key == "type" && value == true) {
//             type = key;
//           }
//         }

//         console.log(" ", data);
//         return (
//           <div className="m-4">
//             <label>Business ID:</label> {bid}
//             {boxesNames.map((boxes, i) => (
//               <div key={i} className="mt-4 m-5">
//                 <div className="form-group">
//                   <label>{boxes} </label>
//                   <input disabled="false" type={boxes} className="form col-12"/>
//                 </div>
//               </div>
//             ))}

//         <div>Incident {type}</div>
//         <select className="w-auto form-select-sm col-2 dropdown " aria-label="Default select example">
//           <option defaultValue={""}>Verbal</option>
//           <option value="1">Physical</option>
//           <option value="2">Warning</option>
//         </select>

//             <div>{profile}</div>
//             <div>{changeLog}</div>
//             <ReportBoxes/>
//           </div>
//         );
//       }
//       return null;
//     }}
//   </Async>
// );

export default AddReport;
