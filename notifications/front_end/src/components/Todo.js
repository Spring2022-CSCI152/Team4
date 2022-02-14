import { useState } from "react";
import Backdrop from "./Backdrop";
import Modal from "./Modal";

//props are object args
//{ } signals to exe as js, not default plain txt or html
//useState is array of 2
//modal && conditional rendering. If both true, return 2nd (2nd always T)

function Perps(props) {
  const [modalOpen, setModalOpen] = useState(false); //initial modal state

  function deleteHandler() {
    setModalOpen(true)
  }

  function closeModalHandler() {
    setModalOpen(false)
  }

  return (
    <div className="card">
      <h2>{props.name}</h2>
      <div className="actions">
        <button className="btn" onClick={deleteHandler}>
          Delete
        </button>
      </div>

      {modalOpen && <Modal onCancel={closeModalHandler} onConfirm={closeModalHandler}/>}
      {modalOpen && <Backdrop onCancel={deleteHandler} />}
    </div>
  );
}

export default Perps;
