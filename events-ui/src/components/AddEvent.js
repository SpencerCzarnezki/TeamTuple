import { getResourceUrl } from "@fluentui/utilities";
import { MDBInput } from "mdb-react-ui-kit";
import { useContext, useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router";
import { Link } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { addResource, findByEventId, saveEvent, } from "../services/api";
import {addLocation, findAllLocations } from "../services/location-api";
const emptyEvent = {
    "title": "",
    "description" : "",
    "date" : "",
    "duration" : "",
    "capacity" : "",
    "eventLocationId" : "",
    "category" : "",
    "organizerId" : "",
    "imageUrl" : ""

}
const emptyLocation = {
    "id": "",
    "title": "",
    "city": "",
    "address": "",
    "zipcode": "",
    "state": ""
}
const emptyResource = {
    "resource" : "",
    "locationId": ""
}



function AddEvent(){
    const[event, setEvent] = useState(emptyEvent);
    const[location, setLocation] = useState(emptyLocation);
    const[locationId, setLocationId] = useState('');
    const { id } = useParams();
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);
    const[resource, setResource] = useState(emptyResource);

    function onChange(evt){
        const nextEvent = {...event};
        let value = evt.target.value;
        if(evt.target.type === "number"){
            value = parseInt(value, 10);
            if(isNaN(value)){
                value = evt.target.value;
            }
        }
        nextEvent[evt.target.name] = value;
        setEvent(nextEvent);

    }
    function onChangeLocation(evt){
        const nextLocation = {...location};
        let value = evt.target.value;
        if(evt.target.type === "number"){
            value = parseInt(value, 10);
            if(isNaN(value)){
                value = evt.target.value;
            }
        }
        nextLocation[evt.target.name] = value;
        setLocation(nextLocation);

    }
    function onChangeResource(evt){
        const nextResource = {...resource};
        let value = evt.target.value;
        if(evt.target.type === "number"){
            value = parseInt(value, 10);
            if(isNaN(value)){
                value = evt.target.value;
            }
        }
        nextResource[evt.target.name] = value;
        setResource(nextResource);
    }


    useEffect(() => {
        if(id) {
            findByEventId(id).then(event => setEvent(event))
            .catch((error => navigate("/NotFound", error.ToString)));
        }
        setLocation(location);
        
    }, [id, navigate]);
    
    function onSubmit(evt){
        evt.preventDefault();
        addLocation(location)
        .then(json => {
            console.log(json);
            const nextEvent = {...event};
            nextEvent.eventLocationId = json.id;
            const nextResource = {...resource};
            nextResource.locationId = json.id;
            console.log(nextResource);
            addResource(nextResource);
            console.log("Add Event ", authContext.user);
            nextEvent.organizerId = authContext.user.id;
            saveEvent(nextEvent).then(() => navigate("/"));
        })
        .catch((err) => navigate("/NotFound", console.log(err)));
        console.log(location);
       
        
        // event.eventLocationId = locationId.id;
        
    }
    let headertitle = "Add an Event";
    if(id !== undefined){
        headertitle = "Edit an Event";
    }

    return(
        <form onSubmit={onSubmit}>
              <h2 className="text-left m-4 display-5">{headertitle}</h2>
              <div  className="row">
                  <div className="m-2">
              <MDBInput label="Event Title" id="title" name="title" type="text" size="lg" className="col form-control" required
                value={event.title} onChange={onChange}></MDBInput>
                </div>
                <div className="m-2">
                <MDBInput label="Event Description" id="description" name="description" type="textarea" textarea size="lg" className="col form-control" 
                value={event.description} onChange={onChange}></MDBInput>
                </div>
                <div className="m-2">
              <MDBInput label="Category" id="category" name="category" type="text" size="lg" className="col form-control" required
                value={event.category} onChange={onChange}></MDBInput>
                </div>
                <div className="m-2">
                    <label className="m-1" required>Start Date and Time:  </label>
              <input type="datetime-local" id="date" name="date" value={event.date} onChange={onChange}></input>
                </div>
                <div className="m-2">
              <MDBInput label="Event Duration (Minutes)" id="duration" name="duration" type="number" size="lg" className="col form-control" required
                value={event.duration} onChange={onChange}></MDBInput>
                </div>
                <div className="m-2">
              <MDBInput label="Capacity" id="capacity" name="capacity" type="number" size="lg" className="col form-control" required
                value={event.capacity} onChange={onChange}></MDBInput>
                </div>
                <div className="m-2">
              <MDBInput label="Image Url" id="imageUrl" name="imageUrl" type="url" size="lg" className="col form-control" 
                value={event.imageUrl} onChange={onChange}></MDBInput>
                </div>
              </div>
              <h2 className="text-left m-4 display-5">Location</h2>
              <div  className="row">
                  <div className="m-2">
              <MDBInput label="Location Title (ex: Cabin 2)" id="title" name="title" type="text" size="lg" className="col form-control" 
                value={location.title} onChange={onChangeLocation}></MDBInput>
                </div>
                <div className="m-2">
                <MDBInput label="Address" id="address" name="address" type="text"  size="lg" className="col form-control" 
                value={location.address} onChange={onChangeLocation}></MDBInput>
                </div>
                <div className="m-2">
              <MDBInput label="City" id="city" name="city" type="text" size="lg" className="col form-control" required
                value={location.city} onChange={onChangeLocation}></MDBInput>
                </div>
                <div className="m-2">
              <MDBInput label="state" id="state" name="state" type="text" size="lg" className="col form-control" required
                value={location.state} onChange={onChangeLocation} maxLength={2}></MDBInput>
                </div>
                <div className="m-2">
              <MDBInput label="Zip Code" id="zipcode" name="zipcode" type="number" size="lg" className="col form-control" 
                value={location.zipcode} onChange={onChangeLocation} min={0} max={99999}></MDBInput>
                </div>
                <div className="m-2">
                <MDBInput label="Amenities" id="resource" name="resource" type="text" textarea size="lg" className="col form-control"
                value={resource.resource} onChange={onChangeResource}></MDBInput>
                </div>
              </div>
              <Link to="/">Cancel</Link>
              <button className="btn btn-primary btn-lg">Add Event</button>
        </form>

    );

}
export default AddEvent;