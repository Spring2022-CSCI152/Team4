import react from "react";
import FetchAllReports from "./FetchAllReports";
import React, { useState } from "react";
import mockData from "../mockData.js";
import ReactPaginate from "react-paginate";
import { FaPen, FaCaretLeft, FaCaretRight  } from 'react-icons/fa';
import ReportTabs from "./DashBtns";

function AllList() {

  {/* Cards display and page calculation */ }
  const [profile, setProfile] = useState(mockData);
  const [pageNum, setPageNum] = useState(0);
  const cardsPerPage = 8;
  const pagesVisited = pageNum * cardsPerPage;
  const AllReports = profile
    .slice(pagesVisited, pagesVisited + cardsPerPage)
    .map((profile) => {
      return (
        <div className="card txt-align-center">
          <div className="nested">
            <div>ID: {profile.id}</div>
            <div>mm dd yy</div>
          </div>
          <img src={profile.thumbnailUrl} />
          <div className="nested">
            <div>name:{profile.title} </div>
            <div>status: n/a</div>
          </div>
          <div>Type</div>
          <div>location: n/a</div>
          <div>0</div>
          <div><button className="btn sm round"> edit <FaPen size="1.5em" /> </button></div>
        </div>
      )
    })

  const pageCount = Math.ceil(profile.length / cardsPerPage)
  const changePage = ({ selected }) => {
    setPageNum(selected);
  }


  return (
    <main>
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
        previousLabel={<FaCaretLeft style={{ fill: '#00f200' }}/>} 
        nextLabel={<FaCaretRight style={{ fill: '#00f200' }}/>}
        pageCount={pageCount}
        onPageChange={changePage}
        containerClassName={"pagination-btn"}
        previousLinkClassName={""}
        nextLinkClassName={""}
        disabledClassName={""}
        activeClassName={""}
      />
    </main>
  )
}

export default AllList;

