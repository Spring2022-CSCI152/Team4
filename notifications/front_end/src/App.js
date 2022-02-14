import ReportTabs from "./components/ReportTabs";
import AllList from "./components/AllList";
import WatchList from "./components/WatchList";
import BannedList from "./components/BannedList";
import UpdatedLogs from "./components/FetchUpdatedLogs";
import React, { useState } from "react";
import Notifications from "./components/Notifications";
import {BsFilePersonFill} from 'react-icons/bs';

const Header = () => {
 
  return (
    <header className="area-padding">
     most wanted
      <div className = "txt-right">
        <BsFilePersonFill size="1.5em"/>
        <div>username</div>
      </div>
    </header>
  );
};


const NavBar = () => {
  return (
  <nav-bar className="areaPadding">
  <ul>
    <li><a className="active">Reports</a></li>
    <li><a>Notifications</a></li>
    <li><a>Profiles</a></li>   
  </ul>
  <button className="btn-signout">Signout</button>
   </nav-bar>);

};


const Main = () => {
  return (
  <main className="area-padding">
    <ReportTabs/>
    <AllList /> 
  </main>
  );
};


const Footer = () => {
  return <footer  className="area-padding">footer ret</footer>;
};



export default function App() {
  const [nav, setNav] = useState("reports");

  return (
    <div id = "app">
      <Header />
      <NavBar/>
      <Main  />
      <Footer />
    </div>
  );
}


