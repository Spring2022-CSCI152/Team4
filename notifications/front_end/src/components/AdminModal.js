
import React from "react";
import "../profModal.css";
import prof_pic from '../assets/prof_pic.png'
import {ImProfile} from "react-icons/im";
import {RiKeyFill, RiUserAddFill, RiProfileFill, RiFileCopy2Fill} from "react-icons/ri";

function AcctModal({setOpenAcctModal}){
    return (
        <div className="view-modal-overlay">
            <div className="view-modal-inner">

                {/* Header */}
                <h3 className="text-align-left "> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> Most Wanted Account</h3>
                <hr className="green"></hr>
                <button 
                    className="close-btn" fixed="top"
                    onClick={() => {setOpenAcctModal(false);}}
                > X </button>

                {/* <div className="container"> */}
                    <div className="row">
            
                        <div className="col-3 border border-dark" style={{backgroundColor: "grey"}}> {/*style={{backgroundColor: "light grey"}}*/}

                            {/* Profile Image */}
                            <div className ="m-4"> <img src={prof_pic} className="img-fluid img-thumbnail rounded-circle" alt="image not loading" /> </div>

                            {/* Name Field*/}
                            <div className=" input-group input-group-lg text-center" >
                                <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey"}}>Employee Name</div> 
                            </div>

                            {/* Job Title Field*/}
                            <div className="input-group input-group-md text-center mb-4">
                                <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey", color: "lightgrey"}}>Job Title</div> 

                            </div>

                        </div>

                    {/* Account Information */}
                    <div className="col border border-dark ">

                        {/* Email Field*/}
                        <div className=" input-group input-group-sm mt-4" >
                            <div className="form-control">Email</div>
                        </div>
                        {/* Email Label*/}
                        <div className="input-group input-group-sm">
                            <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Email</div>
                        </div>

                        {/* Permissions Fields*/}
                        <div className=" input-group input-group-sm mt-4" >
                            <div className="form-control">Permissions</div>
                        </div>
                        {/* Permissions Labels*/}
                        <div className="input-group input-group-sm">
                            <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Permissions</div>
                        </div>              

                        <div className="txt-align-right" >
                            <button className="btn green round" style={{ alignContent: "flex-end"}}><RiUserAddFill /> Edit Account</button>
                        </div>        

                    </div>
                </div>

            </div>
        </div>
    );
}

export default AcctModal