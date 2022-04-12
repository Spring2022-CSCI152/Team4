import { InputGroup, FormControl, DropdownButton, Dropdown } from "react-bootstrap";

const AddReport = () => {
    return (
      <form className="row align-bottom">
        <h3>New Incident</h3>
        <div className="col-2">
          <label htmlFor="inputDate" className="form-label">Incident Date</label>
          <input type="date" className="form-control" id="inputDate"/>
        </div>
        <div className="col-2">
          <label  htmlFor="inputTime" className="form-label">Time</label>
          <input type="time" className="form-control" id="inputTime"/>
        </div>


        <select className="w-auto form-select-sm col-2" aria-label="Default select example">
          <option defaultValue={"..."}>Incident Type</option>
          <option value="1">Assault</option>
          <option value="2">Verbal</option>
          <option value="3">something else</option>
        </select>



        <div className="col-2">
          <label  htmlFor="inputType" className="form-label">Type</label>
          <input type="text" className="form-control" id="inputType"/>
        </div>
        <div className="col-12">
          <label  htmlFor="inputAddress2" className="form-label">Address 2</label>
          <input type="text" className="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor"/>
        </div>
        <div className="col-md-6">
          <label  htmlFor="inputCity" className="form-label">City</label>
          <input type="text" className="form-control" id="inputCity"/>
        </div>
        <div className="col-md-4">
          <label  htmlFor="inputState" className="form-label">State</label>
          <select id="inputState" className="form-select">
            <option selected>Choose...</option>
            <option>...</option>
          </select>
        </div>
        <div className="col-md-2">
          <label  htmlFor="inputZip" className="form-label">Zip</label>
          <input type="text" className="form-control" id="inputZip"/>
        </div>
        <div className="col-12">
          <div className="form-check">
            <input className="form-check-input" type="checkbox" id="gridCheck"/>
            <label className="form-check-label" for="gridCheck">
              Check me out
            </label>
          </div>
        </div>
        <div className="col-12">
          <button type="submit" className="btn green">Submit</button>
        </div>
      </form>
    );
  };
  
  export default AddReport;
  