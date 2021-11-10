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
import { MDBContainer, MDBFooter, MDBIcon, MDBNavbar, MDBNavbarBrand, MDBNavbarItem, MDBNavbarLink, MDBNavbarNav } from "mdb-react-ui-kit";
import AuthContext from "./contexts/AuthContext";
import { logout, refresh } from "./services/auth-api";
import AddEvent from "./components/AddEvent";
import Category from "./components/Category";
import MyEventsList from "./components/MyEventsList";
import ConfirmDelete from "./components/ConfirmDelete";

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
      <MDBNavbar expand="md" light bgColor="light" >
        <MDBContainer fluid>
          <MDBNavbarBrand><img src="logo.png" className="float-left" height='50' className="" /></MDBNavbarBrand>
          <MDBNavbarNav className='me-auto mb-2 mb-lg-0'>
            <MDBNavbarItem>
              <MDBNavbarLink href="/" className="ms-3 m-2">
                <h5>
                Home
                </h5>
              </MDBNavbarLink>
            </MDBNavbarItem>
            <MDBNavbarItem>
              <MDBNavbarLink href="/search" className="ms-3 m-2">
                <h5>
                Search Events
                </h5>
              </MDBNavbarLink>
            </MDBNavbarItem>
            <MDBNavbarItem>
            <MDBNavbarItem>
              <MDBNavbarLink href="/myevents" className="ms-3 m-2">
                <h5>
                My Events
                </h5>
              </MDBNavbarLink>
            </MDBNavbarItem>
        
        </MDBNavbarItem>
          </MDBNavbarNav>
      <MDBNavbarNav right  fullWidth={true} className="">
            <MDBNavbarLink href="/add" className="btn btn-lg btn-primary">Add Event</MDBNavbarLink>
      </MDBNavbarNav>

      {user ?
    <MDBNavbarNav right fullWidth={true}>
      <MDBNavbarItem className="text-success m-2"><MDBIcon color="black" fas icon="user-circle" />{user.username} </MDBNavbarItem>
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
        <Route path="/category" element={<Category />} />
        <Route path ="/myevents" element ={<MyEventsList/>}/>
        <Route path ="/confirmd" element={<ConfirmDelete/>}/>

        <Route path="*" element={<ErrorPage />} />
        <MDBFooter bgColor="dark" className='text-center text-lg-left'>
        <div className="text-center p-3" style={{ backgroundColor: 'rgba(0, 0, 0, 0.2)' }}><a>Test</a></div>
      </MDBFooter>


      </Routes>
    </BrowserRouter>


</AuthContext.Provider>






  );
}

export default App;
