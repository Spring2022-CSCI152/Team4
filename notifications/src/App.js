import { Route, Routes } from "react-router-dom";
import { useState, useEffect } from "react";
import { Navigate, Outlet } from "react-router";
import Reports from "./Reports";
import Notifications from "./Notifications";
import Profiles from "./Profiles";
import Admin from "./Admin";
import Register from "./components/Register";
import SplashPage from "./SplashPage";
import NavBarComp from "./components/NavBarComp";
import AddReport from "./components/AddReport";
import UpdatedLogs from "./components/UpdatedLogs"
import AllList from "./components/AllList";
import Notif from "./components/example_notif";



const App = () => {
  const [user, setUser] = useState(false);

  const Layout = () => (
    <>
      <NavBarComp signOutComplete={() => setUser(false)} />
      <Notif loggedIn={user}/>
      <Outlet />
    </>
  );


  return (
    <div>
  

    
    <Routes>
      {!user && (
        <>
          <Route
            path="/"
            element={<SplashPage signInComplete={() => setUser(true)} />}
          />
          <Route path="/Register" element={<Register />} />
        </>
      )}
      {user && (
        //if user, route to reports and provide routes to following pages, otherwise route to splash
        <>
          <Route element={<Layout />}>
            <Route path="/Reports" element={<Reports />}>
                <Route index element ={<AllList/>}/>
                <Route path="AllList" element={<AllList />}/>
                <Route path="AddReport" element={<AddReport />}/>
                <Route path="UpdatedLogs" element={<UpdatedLogs />}/>
            </Route>
            <Route path="/Notifications" element={<Notifications />} />
            <Route path="/Profiles" element={<Profiles />} />
            <Route path="/Admin" element={<Admin />} />
            <Route
              path="*"
              element={<Navigate to={user ? "Reports" : "/"} />}
            />
          </Route>
        </>
      )}
    </Routes>
    
    </div>
  );
};

export default App;
