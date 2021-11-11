import { MDBCard, MDBCardHeader, MDBRipple, MDBCardImage, MDBCardBody, MDBCardTitle, MDBCardText, MDBListGroup, MDBListGroupItem, MDBRow, MDBCol, MDBCardFooter } from "mdb-react-ui-kit";
import { React, useContext, useEffect, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import { findByLocationId } from "../services/location-api";


function EventCard({ event }) {
    const navigate = useNavigate();
    const auth = useContext(AuthContext);
    const [location, setLocation] = useState([]);
    function onClick() {
        navigate('/event/' + event.id);
    }
    var dateTimeArray = event.date.split("T");
    dateTimeArray[1] = dateTimeArray[1].slice(0, 5);

    function onDelete() {
        navigate(`/confirmd/${event.id}`);
    }

    function onEdit() {
        navigate(`/edit/${event.id}`);
    }
    useEffect(() => {
        findByLocationId(event.eventLocationId).then(l =>
            setLocation(l))
            .catch(err => err.toString());
    });



    return (



        <MDBCol className="m-2">
            <MDBCard style={{ width: '300px' }} className="m-4 hover-shadow bg-image text-white  hover-zoom h-100 " background="dark" border="light" shadow="">
                <MDBCardImage src={event.imageUrl} className=""></MDBCardImage>
                <MDBCardBody className="" onClick={onClick} back>
                    <MDBCardTitle>  <strong> {event.title} </strong> </MDBCardTitle>
                    <MDBCardText>{event.description}</MDBCardText>
                </MDBCardBody>
                <MDBListGroup onClick={onClick} className="light">
                    <MDBListGroupItem color="dark">Date: {dateTimeArray[0]}</MDBListGroupItem>
                    <MDBListGroupItem color="dark">Time: {dateTimeArray[1]} </MDBListGroupItem>
                    <MDBListGroupItem color="dark">Event Capacity: {event.capacity}</MDBListGroupItem>
                    <MDBListGroupItem color="dark" background="dark">{location.address}, {location.city}, {location.state}</MDBListGroupItem>

                </MDBListGroup>
                <MDBCardFooter>
                    {auth.user && (auth.user.authorities[0] === 'ADMIN' || auth.user.id === event.organizerId) &&
                        <button type="button" className="btn btn-primary btn-lg m-2" onClick={onEdit}>Edit Event</button>}
                    {auth.user && auth.user.authorities[0] === 'ADMIN' &&
                        <button type="button" className="btn btn-primary btn-lg m-2" onClick={onDelete}>Delete Event</button>}
                </MDBCardFooter>

            </MDBCard>
        </MDBCol>








    );
}
export default EventCard;