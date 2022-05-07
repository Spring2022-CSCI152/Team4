
import React from "react";
import "../profModal.css";
import {ImProfile} from "react-icons/im";
import Accordion from 'react-bootstrap/Accordion'
import {
  BsFillExclamationCircleFill,
  BsFileEarmarkImage,
  BsFillTrashFill,
} from "react-icons/bs";
import mockData from "../mockData.js";


function ProfModal({setOpenProfModal}){
    return (
        <div className="view-modal-overlay">
        <div className="view-modal-inner">

        {/* Header */}
        <h3 className="text-align-left "> <ImProfile size="1.5em" style={{ fill: "#00f200" }}/> Most Wanted Profile</h3>
        <hr className="green"></hr>
        <button 
            className="close-btn" fixed="top"
            onClick={() => {setOpenProfModal(false);}}
        > X </button>

        <div className="row">
            
            <div className="col-3 border border-dark" style={{backgroundColor: "grey"}}> {/*style={{backgroundColor: "light grey"}}*/}

                {/* Profile Image */}
                <div className ="p-5"> <img src="https://cdn.discordapp.com/attachments/827666575489368097/946650135289528370/unknown.png" className="img-fluid img-thumbnail rounded-circle" alt="image not loading" /> </div>

                {/* Status Fields*/}
                <div className=" input-group input-group-sm" >
                    <div className="form-control">Banned</div>
                </div>
                {/* Status Label*/}
                <div className="input-group input-group-sm pr-3">
                    <div className="form-control fw-bold" style={{border: "0px solid #ced4da", backgroundColor: "grey"}}>Status</div> 

                </div>

                {/* Ban Duration Fields*/}
                <div className=" input-group input-group-sm mt-3" >
                    <div className="form-control">Some Date</div>
                    <div className="form-control">Never</div>
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
                    <div className="form-control">Julieta Mendez</div>
                </div>
                {/* Name Labels*/}
                <div className="input-group input-group-sm">
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Name</div>
                </div>

                {/* Address: street 1 & 2 Fields */}
                <div className=" input-group input-group-sm mt-4 " >
                    <div className="form-control">5241 N Maple Ave</div>
                    <div className="form-control"></div>
                </div>
                {/* Address: street 1 & 2 Labels */}
                <div className="input-group input-group-sm">
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Street 1</div>
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Street 2</div>
                </div>

                {/* Address: city, state, zip Fields */}
                <div className=" input-group input-group-sm mt-1" >
                    <div className="form-control">Fresno</div>
                    <div className="form-control">CA</div>
                    <div className="form-control">93740</div>
                </div>
                {/* Address: city, state, zip Labels */}
                <div className="input-group input-group-sm">
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>City</div>
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>State</div>
                    <div className="form-control fw-bold" style={{  border: "0px solid #ced4da"}}>Zip Code</div>
                </div>

                {/* Attributes Fields */}
                <div className=" input-group input-group-sm mt-4" >
                    <div className="form-control" style={{marginRight: "1rem"}}>F</div>
                    <div className="form-control" style={{marginRight: "1rem"}}>White</div>
                    <div className="form-control" style={{marginRight: "1rem"}}>Brown</div>
                </div>
                {/* Attributes Labels */}
                <div className="input-group input-group-sm">   
                    <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Sex</div>   
                    <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Race</div>   
                    <div className="form-control fw-bold" style={{marginRight: "1rem", border: "0px solid #ced4da"}}>Eye Color</div>   
                </div>

                {/* Attributes Fields*/}
                <div className=" input-group input-group-sm mt-1" >
                    <div className="form-control" style={{marginRight: "1rem"}}>Brown</div>
                    <div className="form-control" style={{marginRight: "1rem"}}>Adult child</div>
                    <div className="form-control" style={{marginRight: "1rem"}}>120</div>
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
        {/* <h6>Reports:</h6> */}
        <Accordion>
            <Accordion.Item eventKey="0">
                <Accordion.Header>2015-12-29 Assault</Accordion.Header>
                <Accordion.Body>
            
                <div className="border border-dark">
                    <div className="row p-3">
                    <div className="col-1">
                        <BsFillExclamationCircleFill
                        size="2rem"
                        style={{ fill: "#00f200" }}
                        />
                    </div>
                    <div className="col-10">
                        <h3 className="text-align-left ml-3">Security Incident Report</h3>
                        <hr className="green"></hr>
                    </div>
                    </div>
                    <div className="row">
                    <div className="col">
                        <b>Report Number:</b>
                        <br />
                        {mockData[0].r_id}
                    </div>
                    <div className="col">
                        <b>Date/Time:</b>
                        <br />
                        {mockData[0].date}
                    </div>
                    <div className="col">
                        <b>Incident Date/Time:</b>
                        <br />
                        {mockData[0].date}
                    </div>
                    <div className="col">
                        <b>Location:</b>
                        <br />
                        {mockData[0].location}
                    </div>
                    <div className="col">
                        <b>Incident Type:</b>
                        <br />
                        {mockData[0].type}
                    </div>
                    </div>

                    <div className="container">
                    <h6>Involved parties:</h6>
                    <div className="row p-1">
                        {mockData[0].profile.map((customer) => (
                        <div className="col">
                            <div className="col-3">
                            <img src={customer.url} className="report-img" />
                            </div>
                            <div className="col">
                            <b>Name:</b> {customer.name}
                            </div>
                            <div className="col">
                            <b>Status:</b> {customer.status}
                            </div>
                            <div className="col">
                            <b>Eyes:</b>
                            </div>
                            <div className="col">
                            <b>Ban Start:</b> somedate
                            </div>
                            <div className="col">
                            <b>Ban End:</b> never
                            </div>
                            <div className="co1">
                            <b>Something else:</b> Something
                            </div>
                        </div>
                        ))}
                    </div>
                    </div>

                    <div className="row">
                    {mockData[0].boxes.map((boxes, i) => (
                        <div key={i}>
                        <div className="mt-2">
                            <h>{boxes.box}</h>
                        </div>
                        <div className="var-box">{boxes.txt} </div>
                        </div>
                    ))}
                    </div>
                    <div className="row">
                    <h>Files</h>
                    <div className="var-box">
                        <BsFileEarmarkImage size="1.5em" />
                        <BsFillTrashFill size="1.5em" />
                    </div>
                    </div>
                    <div className="row">
                    <h>Updates</h>
                    <div className="var-box">updates here</div>
                    </div>
                </div>
                </Accordion.Body>
            </Accordion.Item>
        </Accordion>
        </div>
    </div>
    </div>
    );
}

export default ProfModal