import React, { useState } from "react";
import mockData from "../mockData.js";
import ReactPaginate from "react-paginate";
import { FaPen, FaCaretLeft, FaCaretRight } from "react-icons/fa";
import ReportModal from "./ReportModal";
import { Card, Stack } from "react-bootstrap";


function AllList() {
  {
    /* Cards display and page calculation */
  }

  const [profiles, setProfiles] = useState(mockData);
  const [pageNum, setPageNum] = useState(0);
  const cardsPerPage = 2;
  const pagesVisited = pageNum * cardsPerPage;
  const AllReports = profiles
    .slice(pagesVisited, pagesVisited + cardsPerPage)
    .map((profiles) => {
      return (
        <Card
          className="card-margin"
          key={profiles.r_id}
          onClick={() => setIsOpen(true)}
        >
          <div className="row p-1 d-flex align-items-center">
            <div className="col-auto p-1">
              <p>ID: {profiles.r_id}</p>
              <p>{profiles.date}</p>
            </div>

            <div className="col-5 p-1">
              <div className="col-12">
                {profiles.profile.map((item, i) => {
                  return (
                    <div key={i} className="d-flex justify-content-end">
                      <img src={item.url} />
                    </div>
                  );
                })}
              </div>

              <div className="d-flex align-content-around flex-wrap">
                {profiles.profile.map((item, i) => {
                  return (
                    <div className="col-auto" key={i}>
                      <Stack>
                        {item.name}
                        <br />
                        {item.status}
                      </Stack>
                    </div>
                  );
                })}
              </div>
            </div>

            <div className="col-1 p-1"> {profiles.type} </div>
            <div className="col-2 p-1"> {profiles.location} </div>
            <div className="col-1 p-1"> {profiles.attachements}</div>
            <div className="col-1 p-1">
              <button type="button" className="btn btn-sm btn-light mr-5">
                Edit <FaPen size=".75em" />
              </button>
            </div>
          </div>
        </Card>
      );
    });

  const pageCount = Math.ceil(profiles.length / cardsPerPage);
  const changePage = ({ selected }) => {
    setPageNum(selected);
  };

  const [isOpen, setIsOpen] = useState(false);
  return (
    <main>
      <ReportModal open={isOpen} onClose={() => setIsOpen(false)}>
      </ReportModal>
      <div className="searchBar">search</div>
      <div className="card-label txt-align-left card-margin">
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
