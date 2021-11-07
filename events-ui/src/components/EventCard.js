import { DocumentCard, DocumentCardDetails, DocumentCardLocation, DocumentCardStatus, DocumentCardTitle } from "@fluentui/react";
import { MDBCard, MDBCardHeader, MDBRipple, MDBCardImage, MDBCardBody, MDBCardTitle, MDBCardText, MDBListGroup,MDBListGroupItem,MDBRow, MDBCol, MDBCardFooter } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { findByKeyWord } from "../services/api";

function EventCard({event}){
        const navigate = useNavigate();
    function onClick(){
        navigate('/event/' + event.id);
    }
    var dateTimeArray = event.date.split("T");
    dateTimeArray[1] = dateTimeArray[1].slice(0,5);
   




    return(

        
        
            <MDBCol className="m-2">
        <MDBCard style={{width: '300px'}} className="m-4 hover-shadow bg-image hover-zoom h-100" border="dark" shadow="" onClick={onClick}>
            <MDBCardImage src={event.imageUrl} className=""></MDBCardImage>
            <MDBCardBody className="">
                <MDBCardTitle>  <strong> {event.title} </strong> </MDBCardTitle>
                <MDBCardText>{event.description}</MDBCardText>
            </MDBCardBody>
            <MDBListGroup>
                <MDBListGroupItem>Date: {dateTimeArray[0]}</MDBListGroupItem>
                <MDBListGroupItem>Time: {dateTimeArray[1]} </MDBListGroupItem>
                

                <MDBListGroupItem>Event Capacity: {event.capacity}</MDBListGroupItem>
                <MDBListGroupItem></MDBListGroupItem>

            </MDBListGroup>
            <MDBCardFooter>
                <small>New Event</small>
            </MDBCardFooter>

        </MDBCard>
        </MDBCol>



        
       


        
    );
}
export default EventCard;