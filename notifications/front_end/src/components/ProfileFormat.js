import React from "react";
import { Form, } from "react-bootstrap";

function ProfileFormat({ formData, setFormData }) {
    return (
        <>
            <h6>Select fields to display on your profiles</h6>
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
                            label="Name"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Status"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Address"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Ban Duration"
                        />
                    </Form>
                </div>


                <div class="col">
                    <Form>
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Attributes"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Image Name"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Involvement"
                        />
                        <Form.Check
                            type="switch"
                            id="custom-switch"
                            label="Reports"
                        />
                    </Form>
                </div>
            </div>
        </>
        
    );
}

export default ProfileFormat;