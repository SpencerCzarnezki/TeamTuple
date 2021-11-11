import { MDBCard, MDBCardHeader, MDBRipple, MDBCardImage, MDBCardBody, MDBCardTitle, MDBCardText, MDBListGroup, MDBListGroupItem, MDBRow, MDBCol, MDBCardFooter } from "mdb-react-ui-kit";
import { React, useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";


function EventCard({ event }) {
    const navigate = useNavigate();
    const auth = useContext(AuthContext);
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



    return (



        <MDBCol className="m-2">
            <MDBCard style={{ width: '300px' }} className="m-4 hover-shadow bg-image hover-zoom h-100" border="dark" shadow="">
                <MDBCardImage src={event.imageUrl} className=""></MDBCardImage>
                <MDBCardBody className="" onClick={onClick}>
                    <MDBCardTitle>  <strong> {event.title} </strong> </MDBCardTitle>
                    <MDBCardText>{event.description}</MDBCardText>
                </MDBCardBody>
                <MDBListGroup onClick={onClick}>
                    <MDBListGroupItem>Date: {dateTimeArray[0]}</MDBListGroupItem>
                    <MDBListGroupItem>Time: {dateTimeArray[1]} </MDBListGroupItem>
                    <MDBListGroupItem>Event Capacity: {event.capacity}</MDBListGroupItem>
                    <MDBListGroupItem></MDBListGroupItem>

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