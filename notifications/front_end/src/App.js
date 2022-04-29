import { Route, Routes } from "react-router-dom";
import { useState } from "react";
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

const App = () => {
  const [user, setUser, formData] = useState(null);

  const Layout = () => (
    <>
      <NavBarComp authenticate={() => setUser(false)} />
      <Outlet />
    </>
  );


  return (
    <Routes>
      {!user && (
        <>
          <Route
            path="/"
            element={<SplashPage authenticate={() => setUser(true)} />}
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
  );
};

export default App;
