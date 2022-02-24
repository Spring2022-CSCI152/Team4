import React from 'react';
import { createPortal } from 'react-dom';
import '../report_modal.css';
import { BsFillExclamationCircleFill } from "react-icons/bs";
import mockData from "../mockData.js";

const ReportModal=({open, children, onClose})=>{
    if (!open) return null;

const reports =
    <div> 
        <div>Business ID:</div>
        <div>Report Number:{mockData[0].r_id}</div>
        <div>Incident Date/Time:{mockData[0].date}</div>
        <div>Location: {mockData[0].location}</div>
        <div>Incident Type:{mockData[0].type}</div> 
    </div>
        
       
           

    

    return createPortal(
        <div className='view-report-overlay'>  
            <div className='view-report-inner'>
                <button className='close-btn' onClick={onClose}>X</button>

                

                <div className='row'><BsFillExclamationCircleFill size="1.5em" style={{ fill: "#00f200" }} />
                <h>Security Incident Report</h></div>
                <div className='row-header'>{reports}</div>
                <div className='row'></div>
                <div className='row'></div>
                <div className='row'></div>
                <div className='row'></div>
                
           

            </div>
        </div>,
    document.getElementById('modal')
 )
}

export default ReportModal; 