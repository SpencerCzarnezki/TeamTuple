import { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import EventAttendees from "./components/EventAttendees";
import AuthContext from "./contexts/AuthContext";
import { findByEventId, findByLocationId, findResourcesByLocationId } from "./services/api";
import { MDBRow } from "mdb-react-ui-kit";
import { findUserById } from "./services/user-api";


function setMapUrl(address, city) {
    const map = "https://maps.google.com/maps?q=" + address + " " + city + "k&t=&z=13&ie=UTF8&iwloc=&output=embed";
    return map;
}


function Event() {
    const [event, setEvent] = useState([]);
    const [location, setLocation] = useState([]);
    const [url, setUrl] = useState([]);
    const auth = useContext(AuthContext);
    const { eventId } = useParams();
    const [user, setUser] = useState([]);
    const [resources, setResource] = useState([]);

    // function onClick()

    useEffect(() => {
        if (url.indexOf('h') !== 0) {
            if (eventId) {
                findByEventId(eventId).then(event => setEvent(event))
                    .catch((error) => error.toString());
            }
            findByLocationId(event.eventLocationId).then(location => setLocation(location))
                .catch((error) => error.toString());
            if (location.length !== 0) {
                setUrl(setMapUrl(location.address, location.city));
                console.log(location.address);
                console.log(event.title);
                const array = event.attendees;
                console.log(array);
            }
            findUserById(event.organizerId).then(user => setUser(user))
            .catch((error) => error.toString());
            findResourcesByLocationId(event.eventLocationId).then(resources => setResource(resources))
            .catch((error) => error.toString());
            console.log(resources);
        }
    }, [eventId, location, event, user, resources])

    return (
        <div className="col w-auto">
            <div className="card h-100">
                <div className="card-body">
                    <h5 className="card-title">{event.title}</h5>
                    <div className="row">
                        <div className="col"><strong>When:</strong> </div>
                        <div className="col">{event.date}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Duration:</strong> </div>
                        <div className="col">{event.duration}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Category:</strong> </div>
                        <div className="col">{event.category}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Description:</strong> </div>
                        <div className="col">{event.description}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Location:</strong> </div>
                        <div className="col">{location.address}, {location.city}, {location.state}, {location.zipcode}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Number of available spots:</strong> </div>
                        <div className="col">{event.capacity}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Organizer: </strong> </div>
                        <div className="col">{user.fname} {user.lname}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Amenities: </strong> </div>
                        <div className="col">
                            {resources.resource && resources.resource.forEach(r =><div resource={r} key={r.resourceId} />)} 
                            </div>
                    </div>
        
                </div>

            </div>
            <MDBRow className="m-2">
                <div> Attendees: 
                {event.attendees && event.attendees.map(a => <EventAttendees attendees={a} key={a.eventId} />)}
                </div>
            </MDBRow>
            <div>
                <button type="button" className="btn btn-primary btn-lg m-2" onClick>Join Event</button>
            </div>
            <div>
                {/* {auth.credentials && auth.credentials.hasAuthority("USER", "ADMIN") &&
                    <div className="card-footer text-center">
                        {auth.credentials.hasAuthority("ADMIN") &&
                            <Link to={`/delete/${event.id}`} className="btn btn-danger me-1">Delete</Link>}
                        {auth.credentials.hasAuthority("ADMIN") &&
                            <Link to={`/edit/${event.id}`} className="btn btn-secondary">Edit</Link>} */}
                {/* </div>
                } */}
            </div>

            <div className="flex-center md-6">
                <iframe src={url} width="1275" height="450" frameborder="0" style={{ border: 0 }} allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
            </div>
        </div>
    );
}

export default Event;