import { MDBBtn, MDBInput } from "mdb-react-ui-kit";
import { useContext, useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router";
import { Link } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { addResource, findByEventId, findResourcesByLocationId, saveEvent, deleteResource } from "../services/api";
import { findByLocationId } from "../services/location-api";
import { findAllLocations, saveLocation } from "../services/location-api";
const emptyEvent = {
    "title": "",
    "description": "",
    "date": "",
    "duration": "",
    "capacity": "",
    "eventLocationId": "",
    "category": "",
    "organizerId": "",
    "imageUrl": ""

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
    "resource": "",
    "locationId": ""
}



function AddEvent() {
    const [event, setEvent] = useState(emptyEvent);
    const [location, setLocation] = useState(emptyLocation);
    const { id } = useParams();
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);
    const [resource, setResource] = useState(emptyResource);

    function onChange(evt) {
        const nextEvent = { ...event };
        let value = evt.target.value;
        if (evt.target.type === "number") {
            value = parseInt(value, 10);
            if (isNaN(value)) {
                value = evt.target.value;
            }
        }
        nextEvent[evt.target.name] = value;
        setEvent(nextEvent);

    }
    function onChangeLocation(evt) {
        const nextLocation = { ...location };
        let value = evt.target.value;
        if (evt.target.type === "number") {
            value = parseInt(value, 10);
            if (isNaN(value)) {
                value = evt.target.value;
            }
        }
        nextLocation[evt.target.name] = value;
        setLocation(nextLocation);

    }
    function onChangeResource(evt) {
        const nextResource = { ...resource };
        let value = evt.target.value;
        if (evt.target.type === "number") {
            value = parseInt(value, 10);
            if (isNaN(value)) {
                value = evt.target.value;
            }
        }
        nextResource[evt.target.name] = value;
        setResource(nextResource);
    }


    useEffect(() => {
        if (id) {
            findByEventId(id).then(event => {
                setEvent(event);
                console.log(event);
                if (event.eventLocationId) {
                    findByLocationId(event.eventLocationId).then(location => {
                        setLocation(location);
                        findResourcesByLocationId(location.id).then(res => {
                            setResource(res[0]);
                            console.log(res);
                        })
                            .catch((error => error));
                    }).catch((error => navigate("/NotFound", error.ToString)));

                }
            }).catch((error => navigate("/NotFound", error.ToString)));


        }
        setLocation(location);
    }, [id, navigate]);




    function onSubmit(evt) {
        evt.preventDefault();
        let x = false;
        let newLocationId = "";
        findAllLocations().then(l => {
            l.forEach(loc => {
                if (loc.address === location.address && loc.title === location.title && location.city === loc.city) {
                    x = true;
                    newLocationId = loc.id;
                }

            });
            if (x) {
                event.eventLocationId = newLocationId;
                const nextEvent = { ...event };
                if (nextEvent.organizerId === authContext.user.id || nextEvent.organizerId === "") {
                    nextEvent.organizerId = authContext.user.id;
                }

                findResourcesByLocationId(event.eventLocationId).then(a => {
                    console.log(a);
                    deleteResource(a[0].resourceId).catch((err) => navigate("/NotFound", console.log(err)));

                    const nextResource = { ...resource };
                    console.log(nextResource);
                    nextResource.resourceId = 0;

                    if (nextResource.resource.length !== 0) {
                        addResource(nextResource).catch(err => err.toString());
                    }

                }).catch(a => {
                    const nextResource = { ...resource };
                    console.log(nextResource);
                    nextResource.resourceId = 0;
                    nextResource.locationId = event.eventLocationId;

                    if (nextResource.resource.length !== 0) {
                        addResource(nextResource).catch(err => err.toString());
                    }
                });

                saveEvent(nextEvent).then(() => navigate("/"));
            }
            else {
                saveLocation(location)
                    .then(json => {
                        console.log(json);
                        const nextEvent = { ...event };
                        const nextResource = { ...resource };
                        if (json != null) {
                            nextEvent.eventLocationId = json.id;
                            nextResource.locationId = json.id;
                        }


                        console.log(nextResource.resource);

                        if (nextResource.resource.length !== 0) {
                            addResource(nextResource);
                        }

                        console.log("Add Event ", authContext.user);
                        if (nextEvent.organizerId === authContext.user.id || nextEvent.organizerId === "") {
                            nextEvent.organizerId = authContext.user.id;
                        }
                        saveEvent(nextEvent).then(() => navigate("/"));
                    })
                    .catch((err) => navigate("/NotFound", console.log(err)));
            }
        });

    }
    let headertitle = "Add an Event";
    let buttonname = "Add Event";
    if (id !== undefined) {
        headertitle = "Edit an Event";
        buttonname = "Edit Event";
    }

    return (
        <form onSubmit={onSubmit} className="text-white">
            <h2 className="text-left m-4 display-5">{headertitle}</h2>
            <div className="m-2">
                <div className="m-4 w-25">
                    <MDBInput label="Event Title" id="title" name="title" type="text" size="lg" className="col form-control" contrast required
                        value={event.title} onChange={onChange}></MDBInput>
                </div>
                <div className="m-4 w-50">
                    <MDBInput label="Event Description" id="description" contrast name="description" type="textarea" textarea rows={5} size="lg" className="col form-control"
                        value={event.description} onChange={onChange}></MDBInput>
                </div>
                <div className="m-4 w-25">
                    <MDBInput label="Category" id="category" contrast name="category" type="text" size="lg" className="col form-control" required
                        value={event.category} onChange={onChange}></MDBInput>
                </div>
                <div className="m-4 ">
                    <label className="m-1" required>Start Date and Time:  </label>
                    <input type="datetime-local" id="date" name="date" value={event.date} onChange={onChange} min="2021-11-10T23:00:00"></input>
                </div>
                <div className="m-4 w-25">
                    <MDBInput label="Event Duration (Minutes)" id="duration" contrast name="duration" type="number" size="lg" className="col form-control" required
                        value={event.duration} onChange={onChange} min={0} max={10000}></MDBInput>
                </div>
                <div className="m-4 w-25">
                    <MDBInput label="Capacity" id="capacity" contrast name="capacity" type="number" size="lg" className="col form-control" required
                        value={event.capacity} onChange={onChange} min={0}></MDBInput>
                </div>
                <div className="m-4">
                    <MDBInput label="Image Url" id="imageUrl" name="imageUrl" type="url" contrast size="lg" className="col form-control"
                        value={event.imageUrl} onChange={onChange}></MDBInput>
                </div>
            </div>
            <h2 className="text-left m-4 display-5">Location</h2>
            <div className="">
                <div className="m-4 w-25">
                    <MDBInput label="Location Title (ex: Cabin 2)" id="title" name="title" contrast type="text" size="lg" className="col form-control"
                        value={location.title} onChange={onChangeLocation}></MDBInput>
                </div>
                <div className="m-4 w-50">
                    <MDBInput label="Address" id="address" name="address" type="text" size="lg" contrast className="col form-control"
                        value={location.address} onChange={onChangeLocation}></MDBInput>
                </div>
                <div className="m-4 w-25">
                    <MDBInput label="City" id="city" name="city" type="text" size="lg" contrast className="col form-control" required
                        value={location.city} onChange={onChangeLocation}></MDBInput>
                </div>
                <div className="m-4 w-25">
                    <MDBInput label="state" id="state" name="state" type="text" size="lg" contrast className="col form-control" required
                        value={location.state} onChange={onChangeLocation} maxLength={2}></MDBInput>
                </div>
                <div className="m-4 w-25">
                    <MDBInput label="Zip Code" id="zipcode" name="zipcode" type="number" contrast size="lg" className="col form-control"
                        value={location.zipcode} onChange={onChangeLocation} min={0} max={99999}></MDBInput>
                </div>
                <div className="m-4 w-50">
                    <MDBInput label="Amenities" id="resource" name="resource" type="text" contrast textarea size="lg" className="col form-control"
                        value={resource.resource} onChange={onChangeResource}></MDBInput>
                </div>
            </div>
            <Link to="/" className="m-4 btn btn-lg btn-outline-primary">Cancel</Link>
            <MDBBtn className="m-4 btn btn-primary btn-lg">{buttonname}</MDBBtn>
        </form>

    );

}
export default AddEvent;