import React from 'react';
import { createPortal } from 'react-dom';
import '../report_modal.css';

const ReportModal=({open, children, onClose})=>{
    if (!open) return null;

    return createPortal(
        <div className='view-report-overlay'>  
            <div className='view-report-inner'>
                <button className='close-btn' onClick={onClose}>X</button>
                  {children}
            </div>
        </div>,
    document.getElementById('modal')
       
 )
}

export default ReportModal; 