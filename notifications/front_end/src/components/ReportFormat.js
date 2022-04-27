import { Form, } from "react-bootstrap";
import React from "react";

function ReportFormat({ formData, setFormData }) {
    return (
        <>
            <h6>Select fields to display on your Reports</h6>
            <div class="row">
                <div class="col-3">
                    <Form>
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Business Id"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="reportId"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="profiles"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="date"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="time"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="author"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="type"
                        />
                    </Form>

                </div>

                <div class="col">
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








        </>
    );
}

export default ReportFormat;