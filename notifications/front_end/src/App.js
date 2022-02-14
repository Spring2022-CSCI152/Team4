import ReportTabs from "./components/ReportTabs";
import AllList from "./components/AllList";
import WatchList from "./components/WatchList";
import BannedList from "./components/BannedList";
import UpdatedLogs from "./components/FetchUpdatedLogs";
import React, { useState } from "react";
import reports from "./components/reports";
import Notifications from "./components/Notifications";
import {BsFilePersonFill} from 'react-icons/bs';


const Header = ({ nav, setNav }) => {
  const isReports = nav === "reports";
  const setReports = () => setNav("reports");
  const setNotifications = () => setNav("nofitications");

  return (
    <header>
    <h1>Most Wanted</h1>
    <div className="txtCenter">
      <BsFilePersonFill size="1.5em"/>
      <br></br>name
    </div>

    {/* --Nav bar--*/}
        <nav>
          <button className ="btn" onClick={setReports}>
            Reports
          </button>
          <button className ="btn" onClick={setNotifications} >
            Notifications
          </button>
        </nav>

        <button className="btn sm">Sign Out</button>
    </header>
  );
};

const Main = ({ nav }) => {
  return <main>{nav === "reports" ? <AllList /> : <Notifications />}</main>;
};


const Footer = () => {
  return <footer>icon attribution stuff</footer>;
};



export default function App() {
  const [nav, setNav] = useState("reports");

  return (
    <div id="app">
      <Header {...{ nav, setNav }} />
      <Main nav={nav} />
      <Footer />
    </div>
  );
}



/*
function App() {

  const [tab, setTab] = useState([]); //initial mount on page load

  return (
    <div id="app"> 

      <div className="header">
      <h1>Most Wanted</h1> 
      </div>

      <div className="main">
        <ReportTabs setTab={setTab} />

        <div className="searchBar">search</div>
        <div className="label">
          <div>Report Number</div>
          <div>Involved Parties</div>
          <div>Type</div>
          <div>Location</div>
          <div>Attachments</div>
        </div>
     
        {tab === "AllList" ? <AllList /> : null}
        {tab === "WatchList" ? <WatchList /> : null}
        {tab === "BannedList" ? <BannedList /> : null}
        {tab === "UpdatedLogs" ? <UpdatedLogs /> : null}
     </div>

      <div className="footer">
        <h1>footer</h1>
      </div>


    </div>
   
      
   

    
  ); 


  
}

export default App;

*/