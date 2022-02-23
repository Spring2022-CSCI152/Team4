import react from "react";
import FetchAllReports from "./FetchAllReports";
import React, { useState } from "react";
import mockData from "../mockData.js";
import ReactPaginate from "react-paginate";
import { FaPen, FaCaretLeft, FaCaretRight } from "react-icons/fa";
import DashBtns from "./DashBtns";

function AllList() {

  {
    /* Cards display and page calculation */
  }
  const [profiles, setProfiles] = useState(mockData);
  const [pageNum, setPageNum] = useState(0);
  const cardsPerPage = 6;
  const pagesVisited = pageNum * cardsPerPage;

  const AllReports = profiles
    .slice(pagesVisited, pagesVisited + cardsPerPage)
    .map((profiles) => {
      return (
        <div key={profiles.r_id} className="card txt-align-center ">
          <div>
            <p>ID: {profiles.r_id}</p>
            <p>{profiles.date}</p>
          </div>

          <div>
            {profiles.profile.map((item, i) => {
              return (
                <div key={i}>
                  <img src={item.url} />
                </div>
              );
            })}
          </div>

          <div className="nested">
            {profiles.profile.map((item, i) => {
              return (
                <div className="nested" key={i}>
                  {item.name} <br></br>
                  {item.status}
                </div>
              );
            })}
          </div>

          <p>{profiles.type}</p>
          <p>{profiles.location}</p>
          <p>{profiles.attachements}</p>
          <button className="btn sm round">
            {" "}
            edit <FaPen size="1.5em" />{" "}
          </button>
        </div>
      );
    });

  const pageCount = Math.ceil(profiles.length / cardsPerPage);
  const changePage = ({ selected }) => {
    setPageNum(selected);
  };

  return (
    <main>
      { <DashBtns />}
      <div className="searchBar">search</div>
      <div className="card-label">
        <div>Report Number</div>
        <div>Involved Parties</div>
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