import { useContext, useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import EventAttendees from "./components/EventAttendees";
import AuthContext from "./contexts/AuthContext";
import { findByEventId, findByLocationId, findResourcesByLocationId, updateEvent } from "./services/api";
import { MDBIcon, MDBRow } from "mdb-react-ui-kit";
import { findUserById } from "./services/user-api";
import Resource from "./components/Resource";
import { addAUserToEvent, leaveEvent } from "./services/event-join-api";


function setMapUrl(address, city) {
    const map = "https://maps.google.com/maps?q=" + address + " " + city + "k&t=&z=13&ie=UTF8&iwloc=&output=embed";
    return map;
}
const emptyEventUser = {

    "eventId": "",
    "userId": ""
}


function Event() {
    const [event, setEvent] = useState([]);
    const [location, setLocation] = useState([]);
    const [url, setUrl] = useState([]);
    const auth = useContext(AuthContext);
    const navigate = useNavigate();
    const { eventId } = useParams();
    const [user, setUser] = useState([]);
    const [resources, setResource] = useState([]);
    const [eventUser, setEventUser] = useState(emptyEventUser);
    const [checkJoined, setCheckJoined] = useState(false);
    const [joinError, setJoinError] = useState(false);
    function onJoin() {
        const nextEventUser = { ...eventUser };
        nextEventUser.eventId = event.id;
        nextEventUser.userId = auth.user.id;
        setEventUser(nextEventUser);
        
        if((auth.user.id != event.id) && (event.attendees.length < event.capacity)){
            console.log("Find this one", event);
        addAUserToEvent(nextEventUser).then(window.location.reload(true))
            .catch((err) => console.log(err));
        } else{
            setJoinError(true);
        }


    };
    function onLeave() {
        leaveEvent(event.id, auth.user.id).then(window.location.reload(true))
            .catch((err) => console.log(err));

    };

    function onDelete() {
        // deleteEvent(event.id).then(() => );
        navigate(`/confirmd/${event.id}`);
    }

    function onAccept() {
        const nextEvent = {...event};
        nextEvent.status = true;
        updateEvent(nextEvent).then(window.location.reload(true))
        .catch(err => err.toString());
    }

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

            console.log(event.attendees);
        };
        console.log(auth);
        if (event.attendees && auth.user) {
            const attendeeList = event.attendees;
            for (let x = 0; x < event.attendees.length; x++) {
                console.log(event.attendees[x].user.userId);
                console.log(auth.user.authorities);
                if (event.attendees[x].user.userId == auth.user.id) {
                    let nextCheck = { ...checkJoined };
                    nextCheck = true;
                    setCheckJoined(nextCheck);
                }
            }
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
                        <div className="col"><strong>Number of spots filled:</strong> </div>
                        <div className="col"> {event.attendees && event.attendees.length}/{event.capacity}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Organizer: </strong> </div>
                        <div className="col">{user.fname} {user.lname}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Amenities: </strong>

                        </div>
                        <div className="col">
                            {resources && resources.map((r) => <Resource resources={r} key={r.resourceId} />)}

                        </div>
                    </div>

                </div>

            </div>
            <MDBRow className="m-2">
                <div><MDBIcon fas icon="users" /> Attendees:
                    {event.attendees && event.attendees.map((a) => <EventAttendees attendees={a} key={a.user.userId} />)}
                    {console.log(event)}
                    {console.log(resources)}
                </div>
            </MDBRow>
            {auth.user ? <div>
                {checkJoined ? <div>
                    <button type="button" className="btn btn-primary btn-lg m-2" onClick={onLeave}>Leave Event</button>
                </div>
                    : <div>
                        <button type="button" className="btn btn-primary btn-lg m-2" onClick={onJoin}>Join Event</button>
                    </div>
                } </div> : ""}

                {joinError && <div className="bg-danger text-white" >Cannot Join Event</div>}
            <div>

                <div>
                    {auth.user && auth.user.authorities[0] === 'ADMIN' &&
                    <div>
                        <button type="button" className="btn btn-primary btn-lg m-2" onClick={onDelete}>Delete Event</button>
                        {!event.status &&
                        <button type="button" className="btn btn-primary btn-lg m-2" onClick={onAccept}>Approve Event</button>}
                    </div>}
                </div>
            </div>

            <div className="flex-center md-6">
                <iframe src={url} width="1275" height="450" frameborder="0" style={{ border: 0 }} allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
            </div>
        </div>
    );
}

export default Event;