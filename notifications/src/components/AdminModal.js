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
                            <div className ="m-4"> <img src="https://cdn.discordapp.com/attachments/827666575489368097/961056360995381338/ryan.png" className="img-fluid img-thumbnail rounded-circle" alt="image not loading" /> </div>

                            {/* Name Field*/}
                            <div className=" input-group input-group-lg text-center" >
                                <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey"}}>Ryan Anderson</div> 
                            </div>

                            {/* Email Field*/}
                            <div className="input-group input-group-md text-center">
                                <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey", color: "lightgrey"}}>anderson94@gmail.com</div> 
                            </div>

                            {/* Job Title Field*/}
                            <div className="input-group input-group-md text-center mb-4">
                                <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey", color: "lightgrey"}}>Security</div> 
                            </div>

                        </div>

                    {/* Permissions */}
                    <div className="col border border-dark ">
                       
                        {/* Per1 Field*/}
                        <div className=" input-group input-group-sm mt-4" >
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>

                        </div>
                        {/* per1 Label*/}
                        <div className="input-group input-group-sm">
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Create Reports</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Edit Reports</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Delete Reports</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Set Report Format</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Set Profile Format</div>
                        </div>                        
                        
                        {/* Per2 Field*/}
                        <div className=" input-group input-group-sm mt-4" >
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>

                        </div>
                        {/* per2 Label*/}
                        <div className="input-group input-group-sm">
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Create Updates</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Edit Updates</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Delete Updates</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Create Attachments</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Delete Attachments</div>
                        </div>                        
                        
                        {/* Per3 Field*/}
                        <div className=" input-group input-group-sm mt-4" >
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">X</div>
                        <div className="form-control">X</div>

                        </div>
                        {/* per3 Label*/}
                        <div className="input-group input-group-sm">
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Create Images</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Edit Images</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Create Profiles</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Edit Profiles</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Delete Profiles</div>
                        </div>

                        {/* Per4 Field*/}
                        <div className=" input-group input-group-sm mt-4" >
                        <div className="form-control">✓</div>
                        <div className="form-control">X</div>
                        <div className="form-control">X</div>
                        <div className="form-control">X</div>
                        <div className="form-control">✓</div>
                        <div className="form-control">X</div>

                        </div>
                        {/* per4 Label*/}
                        <div className="input-group input-group-sm">
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Enable/Disable Notifications</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Register other accounts</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Promote other accounts</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Demote other accounts</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>View other accounts info</div>
                        <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Update other accounts info</div>
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