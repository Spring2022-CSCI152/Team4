// import React from "react";
// import mockData from "./mockData";
// import {ImProfile} from "react-icons/im";


// const Admin = () => {
//   const [isOpen, setIsOpen] = useState(false);
//   const AcctSrcResult = mockData.map((profiles) => {
//     return profiles.profile.map((acct) => {
//       return (
//         <div className="card acct area-padding txt-align-center">
//           <img src={acct.url} style={{ borderRadius: "50%" }} /> {acct.name}
//         </div>
//       );
//     });
//   });

//   return (
//     <div>
//     <div className="row-4 area-padding">

//       {/* Header */}
//       <h6> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> {"Most Wanted Profiles"} </h6>

//       {/* Search Bar */}
//       <div className="container-fluid p-3 card-label">
//         <form className="d-flex ">
//           <input className="form-control me-2" type="search" placeholder="Search Profile" aria-label="Search" style={{ background: "#eeeeee" }}/>
//           <button type="button" className="btn btn-outline-secondary" style={{ background: "#00f200" }}>Search</button>
//         </form>
//       </div>

//       <div className="txt-align-center">{AcctSrcResult}</div>
//     </div>
//   </div>


//   );

import React from "react"
import prof_pic from './assets/prof_pic.png'

const ProfileAccts = () => {
    return(
        <div className="container">
        <div className="row">
            
            <div className="col-3 border border-dark" style={{backgroundColor: "grey"}}> {/*style={{backgroundColor: "light grey"}}*/}

                {/* Profile Image */}
                <div className ="m-2"> <img src={prof_pic} className="img-fluid img-thumbnail rounded-circle" alt="image not loading"/> </div>

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
                <div className=" input-group input-group-lg p-5" >
                    <div className="form-control fw-bold"  style={{width: "auto", textAlign: "center"}}>Name</div>
                </div>
                {/* Name Labels*/}
                {/* <div className="input-group input-group-sm">
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Name</div>
                </div> */}

                {/* Address: street 1 & 2 Fields */}
                <div className=" input-group input-group-sm" >
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
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Street 1</div>
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
    )
}

export default ProfileAccts


  
// };

// export default Admin;

