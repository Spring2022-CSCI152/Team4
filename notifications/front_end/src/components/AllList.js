import react from "react";
import FetchAllReports from "./FetchAllReports";
import React, { useState} from "react";
import mockData from "../mockData.js";
import ReactPaginate from "react-paginate";
import {FaPen} from 'react-icons/fa';
import ReportTabs from "./ReportTabs";



/* 
const fetchAll = async (pageNum) => {
  const res = await fetch(`https://jsonplaceholder.typicode.com/photos/?_limit=8&_page=${pageNum}`);
  return res.json();
}

const AllList = () => {
  const [pageNum, setPageNum] = useState(1);
  const { data, status } = useQuery(['all', pageNum],() => fetchAll(pageNum));
   console.log("data ", data, "page", pageNum);
*/ 
function AllList() {

{/* Cards display and page calculation */}
  const [profile, setProfile]= useState(mockData);
  const [pageNum, setPageNum] = useState(0);
  const cardsPerPage = 8;             
  const pagesVisited = pageNum * cardsPerPage;  
  const AllReports = profile
    .slice(pagesVisited, pagesVisited + cardsPerPage)
    .map( (profile) => {
      return (
        <div className="card">
          <div className="nested">
            <div>ID: {profile.id}</div>
            <div>mm dd yy</div>
         </div>
         <img src = {profile.thumbnailUrl}/>
         <div className="nested">
            <div>name:{profile.title} </div>
            <div>status: n/a</div>
         </div>

         <div>Type</div>
         <div>location: n/a</div>
         <div>0</div>
         <div><button className="btn sm"> edit <FaPen size="1.5em"/> </button></div>

      </div> 
      ) 
    }) 
 
      const pageCount = Math.ceil(profile.length/cardsPerPage)
      const changePage =({selected}) => {
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
       

      {/* Pagination functionality*/}
          <ReactPaginate
            previousLabel={"prev"}
            nextLabel={"next"}
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

  /*
    <>
    <div>
      <h2>AllList</h2>
 
      {status === "loading" && <div>Loading. Please Wait</div>}
      {status === "error" && <div>Error fetching data</div>}
      {status === "success" && (
        <div>
          {data.map((item) => (<FetchAllReports key={item.id} item={item} />))}
        </div>
      )}
    </div>

    <div>
      <button onClick ={()=> setPageNum ((pageNum)=> pageNum-1)} 
      disabled={pageNum === 1 }> Prev </button>
  
      <button onClick ={()=> setPageNum ((pageNum)=> pageNum+1)} 
      disabled={pageNum === 5 }> Next </button>
      </div> 
    </>
  );   
};

export default function Wrapped() {
  return (
    <QueryClientProvider client={queryClient}>
      <AllList />
    </QueryClientProvider>
  );
}*/
