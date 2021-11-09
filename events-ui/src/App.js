import { CommandBarBase, ContextualMenu, Link, Nav } from "@fluentui/react";
import React, { useEffect, useState } from "react";
import { Route, Router, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import ErrorPage from "./components/ErrorPage";
import EventCard from "./components/EventCard";
import Homepage from "./components/Homepage";
import Search from "./components/SearchEvents";
import Login from "./Login";
import Register from "./Register";
import Event from "./Event";
import SearchEvents from "./components/SearchEvents";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import { MDBContainer, MDBNavbar, MDBNavbarBrand, MDBNavbarItem, MDBNavbarLink, MDBNavbarNav } from "mdb-react-ui-kit";
import AuthContext from "./contexts/AuthContext";
import { logout, refresh } from "./services/auth-api";
import AddEvent from "./components/AddEvent";

function App() {


  const [user, setUser] = useState();
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    refresh().then(loginName => setUser(loginName))
    .catch(() => setUser())
    .finally(() => setInitialized(true));
  }, []);

  const authorization = {
    user,
    login: (loginName) => setUser(loginName),
    logout: () => {
      logout().finally(() => setUser());
    }
  };
  console.log(user);




  return (
    <AuthContext.Provider value={authorization}>
    <BrowserRouter>
      <MDBNavbar expand="lg" light bgColor="light" >
        <MDBContainer fluid>
          <MDBNavbarBrand><img src="logo.png" className="float-left" height='60' /></MDBNavbarBrand>
          <MDBNavbarNav className='me-auto mb-2 mb-lg-0'>
            <MDBNavbarItem>
              <MDBNavbarLink href="/">
                Home
              </MDBNavbarLink>
            </MDBNavbarItem>
            <MDBNavbarItem>
              <MDBNavbarLink href="/search">
                Search Events
              </MDBNavbarLink>
            </MDBNavbarItem>
            <MDBNavbarItem>
          <MDBNavbarLink href="/add">Add Event</MDBNavbarLink>
        </MDBNavbarItem>
          </MDBNavbarNav>
      <MDBNavbarNav right fullWidth={false}>

      </MDBNavbarNav>

      {user ?
    <MDBNavbarNav right fullWidth={false}>
      <MDBNavbarItem>{user.id}</MDBNavbarItem>
    <button type="button" className="btn btn-lg btn-danger" onClick={authorization.logout}>
          Logout 
        </button>
        </MDBNavbarNav> :


          <MDBNavbarNav right fullWidth={false}>
            
            <MDBNavbarItem>
              <MDBNavbarLink href="/register">
                Register
              </MDBNavbarLink>
            </MDBNavbarItem> 
            <MDBNavbarItem>
              <MDBNavbarLink href="/login">
                Login
              </MDBNavbarLink> 
            </MDBNavbarItem> 
            
          </MDBNavbarNav>   
            }

        </MDBContainer>
      </MDBNavbar>
  




      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/event/:eventId" element={<Event />} />
        <Route path="/login" element={<Login />} />
        <Route path="/search" element={<SearchEvents />} />
        <Route path="/register" element={<Register />} />
        <Route path="/add" element={<AddEvent />} />


        <Route path="*" element={<ErrorPage />} />



      </Routes>
    </BrowserRouter>


</AuthContext.Provider>






  );
}

export default App;
