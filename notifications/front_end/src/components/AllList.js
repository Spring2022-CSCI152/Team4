import React, { useState } from "react";
import mockData from "../mockData.js";
import ReactPaginate from "react-paginate";
import { FaPen, FaCaretLeft, FaCaretRight } from "react-icons/fa";
import DashBtns from "./DashBtns";
import ReportModal from "./ReportModal";

function AllList() {

  {/* Cards display and page calculation */}

  const [profiles, setProfiles] = useState(mockData);
  const [pageNum, setPageNum] = useState(0);
  const cardsPerPage = 6;
  const pagesVisited = pageNum * cardsPerPage;
  const AllReports = profiles
    .slice(pagesVisited, pagesVisited + cardsPerPage)
    .map((profiles) => {
      return (
        <div className="card txt-align-center card-margin" key={profiles.r_id} onClick={() => setIsOpen(true)}>
          <div>
            <p>ID: {profiles.r_id}</p>
            <p>{profiles.date}</p>
          </div>

          <div>
            {profiles.profile.map((item, i) => {
              return (
                <div key={i} className="nested txt-align-left" >
                  <img src={item.url} />
                  {item.name}<br/>
                  {item.status}
           
                </div>
              );
            })}
          </div>

          <p>{profiles.type}</p>
          <p>{profiles.location}</p>
          <p>{profiles.attachements}</p>
            <button className="btn sm round">
            {" "}edit <FaPen size="1.5em" />{" "}
          </button>  
        </div>   
      );
    });

  const pageCount = Math.ceil(profiles.length / cardsPerPage);
  const changePage = ({ selected }) => {
    setPageNum(selected);
  };

  const [isOpen, setIsOpen] = useState(false);
  return (
    <main>
      
      <ReportModal open={isOpen} onClose={() => setIsOpen(false)}> </ReportModal>
      { <DashBtns />}
      <div className="searchBar">search</div>
      <div className="card-label txt-align-center card-margin">
        <div>Report Number</div>
        <div className="txt-align-left ">Involved Parties</div>
        <div>Type</div>
        <div>Location</div>
        <div>Attachments</div>
      </div>
     
      {AllReports}

      {/* Pagination functionality */}
      <ReactPaginate
        previousLabel={<FaCaretLeft style={{ fill: "#00f200" }} />}
        nextLabel={<FaCaretRight style={{ fill: "#00f200" }} />}
        pageCount={pageCount}
        onPageChange={changePage}
        containerClassName={"pagination-btn"}
        previousLinkClassName={""}
        nextLinkClassName={""}
        disabledClassName={""}
        activeClassName={""}
      />
    </main>
  );
}

export default AllList;
