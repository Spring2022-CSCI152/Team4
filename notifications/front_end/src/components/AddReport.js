import US_states from "./US-states";
import mockData from "../mockData";
import { Button } from "react-bootstrap";

const AddReport = () => {

    const uploadImage = (files) => {
      const formData = new FormData()
      formData.append("img", files[0])
      console.log(formData)
    }

    return (
      <form className="row m-2">
        <h3>New Incident</h3>
        <div className="col-2 mb-3">
          <label htmlFor="inputDate" className="form-label">Incident Date</label>
          <input type="date" className="form-control" id="inputDate"/>
        </div>
        <div className="col-2">
          <label  htmlFor="inputTime" className="form-label">Time</label>
          <input type="time" className="form-control" id="inputTime"/>
        </div>
      <div className="col-2">
        <label  htmlFor="inputType" className="form-label">Location</label>
        <input type="text" className="form-control" id="inputType"/>
      </div>
      <div className="col-2">    
        <label  htmlFor="inputTime" className="form-label">Incident Type</label>
        <select className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
          <option defaultValue={""}>Verbal</option>
          <option value="1">Physical</option>
          <option value="2">Warning</option>
        </select>
      </div>
      <hr className="green"></hr>
      <h6>Involved Parties</h6>
      <div className="col-7 mb-3">     
          <label  htmlFor="inputProfile" className="form-label">Existing Profiles</label>
          <select className="w-auto form-select-sm col-2 dropdown" aria-label="Default select example">
            <option defaultValue={"..."}></option>

            {mockData.profile.map((item, i) => {
                  return (
                    <div key={i} className="d-flex justify-content-end">
                      <img src={item.url} />
                    </div>
                  );
                })}
          
            

          </select>
        </div>
        <div className="col-md-5 mb-3">
          <label  htmlFor="inputAddress2" className="form-label">Address</label>
          <input type="text" className="form-control" id="inputAddress2"/>
        </div>
        <div className="col-md-3">
          <label  htmlFor="inputCity" className="form-label">City</label>
          <input type="text" className="form-control" id="inputCity"/>
        </div>
        <div className="col-md-1">
          <label  htmlFor="inputState" className="form-label">State</label>
          <select id="inputState" className="form-select">
            <option>...</option>
            {mockData.map( (item,i)=>{return( <option key={i}> {item} </option> )})}
          </select>
        </div>
        <div className="col-md-2">
          <label  htmlFor="inputZip" className="form-label">Zip</label>
          <input type="text" className="form-control" id="inputZip"/>
        </div> 

        <div> <input type ="file" 
          onChange ={
            (event)=>{ uploadImage(event.target.files);}
            }/>
        </div>

     
        <div className="col-12">
  
        </div>
        <div className="col-12">
          <button type="submit" className="btn green btn-sm">Add Person</button>     
          <hr className="green"></hr>
        </div>


   

      </form>
    );
  };
  
  export default AddReport;
  