import React from 'react';
import '../report_modal.css';

function ReportModal(props){

    return (props.trigger) ? (
        <div className='view-report-overlay model-open'>  
            <div className='view-report-inner'>
                <button className='close-btn' onClick={() => props.setTrigger(false)}>close</button>
                  {props.children}
            </div>
        </div>
    ): "";
}

export default ReportModal;