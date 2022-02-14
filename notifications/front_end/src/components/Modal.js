function Modal(props) {
    /*
    alt way to call modal functions
    function cancelHandler(){
        props.onCancel();
    */
   
  return (
    <div className="modal">
      <p>Are you sure?</p>
      <button className="btn btn--alt" onClick ={props.onCancel}>Close/Cancel</button>
      <button className="btn" onClick ={props.onConfirm}>Confirm/Delete</button>
    </div>
  );
}

export default Modal;
