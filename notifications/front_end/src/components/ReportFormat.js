import { Form, } from "react-bootstrap";
import React from "react";

function ReportFormat({ formData, setFormData }) {
    return (
       <div className=" justify-content-center">
     
            <h6>Select fields to display on Reports</h6>
          
            <div className="row">  
                <div className="col">
                    <Form>
                        <Form.Check
                            type="switch"
                            label="Business Id"
                            value={formData.reportBusinessId.value}
                        />
                        <Form.Check
                            type="switch"
                            label="reportId"
                            value={formData.reportId}
                        />
                        <Form.Check
                            type="switch"
                            label="profiles"
                            value={formData.profiles}
                        />
                        <Form.Check
                            type="switch"
                            label="date"
                            value={formData.date}
                        />
                        <Form.Check
                            type="switch"
                            label="time"
                            value={formData.time}
                        />
                        <Form.Check
                            type="switch"
                            label="author"
                            value={formData.author}
                        />
                        <Form.Check
                            type="switch"
                            label="type"
                            value={formData.type}
                        />
                    </Form>
                </div>
                <div className="col"> 
                    <Form>  
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Source of Activity"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Investigation"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Resolution"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Conclusion"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Dispositional Information"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="attachments"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="changeLog"
                        />
                    </Form>
                </div>
            </div>
        </div>
    );
}

export default ReportFormat;