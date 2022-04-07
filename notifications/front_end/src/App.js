import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useState } from "react";
import { Navigate, Outlet } from "react-router";
import Reports from "./Reports";
import Notifications from "./Notifications";
import Profiles from "./Profiles";
import Admin from "./Admin";
import SignIn from "./components/SignIn";
import Register from "./components/Register";
import SplashPage from "./SplashPage";
import NavBarComp from "./components/NavBarComp";

const App = () => {
  const [user, setUser] = useState(null);


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
          <Route path="/"element={<SplashPage authenticate={() => setUser(true)} />}/>
          <Route path="/Register"element={<Register/>}/>
        </>
      )}
      {user && (
        <>
          <Route element={<Layout />}>
            <Route path="/Reports" element={<Reports />} />
            <Route path="/Notifications" element={<Notifications />} />
            <Route path="/Profiles" element={<Profiles />} />
            <Route path="/Admin" element={<Admin />} />
            <Route
              path="*" element={<Navigate to={user ? "/Reports" : "/"} />}
            />
          </Route>
        </>
      )}
    </Routes>
  );
};

export default App;
