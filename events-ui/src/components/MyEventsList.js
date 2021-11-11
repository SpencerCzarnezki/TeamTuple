import { useState, useEffect, useContext } from "react";
import { findAllEvents, findByEventId } from "../services/api";
import EventCard from "./EventCard";
import AuthContext from "../contexts/AuthContext";
import { useNavigate } from "react-router";
import { findEventIdsByUserId } from "../services/event-join-api";


function MyEventsList() {
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();
    const [EventCards, setEvents] = useState([]);
    const [event, setEvent] = useState([]);
    const history = useNavigate();

    useEffect(() => {
        findAllEvents().then(result => setEvents(result))
            .catch((err) => {
                if (err === 403) {
                    authContext.logout();
                    history("/login");
                } else {
                    history("/error", err.toString());
                }

            });
        if (authContext.user) {
            findEventIdsByUserId(authContext.user.id).then(eventUserKeys => {
                const promises = [];
                if (eventUserKeys.length !== 0) {
                    for (const userKey of eventUserKeys) {
                        promises.push(findByEventId(userKey.eventId));
                    }
                    Promise.all(promises).then(events => setEvent(events));
                    console.log(event);
                }

            }).catch(err => err.toString());
        }
    }, [authContext, history]);


    function myPendingOrganizedEvents() {
        let orgEvents = [];
        // let uId = authContext.user.id;


        if (authContext.user && !EventCards.length == 0) {

            const holder = EventCards.filter(e => e.status === false);
            const yourPendingEvents = holder.filter(e => e.organizerId === authContext.user.id);

            orgEvents = yourPendingEvents;
        }

        return orgEvents;
    }

    function myAcceptedOrganizedEvents() {
        let orgEvents = [];
        // let uId = authContext.user.id;

        if (authContext.user && !EventCards.length == 0) {

            const holder2 = EventCards.filter(e => e.status === true);
            const yourAcceptedEvents = holder2.filter(e => e.organizerId === authContext.user.id);
            orgEvents = yourAcceptedEvents;
        }

        return orgEvents;
    }

    let orgAEvents = myAcceptedOrganizedEvents(EventCards);
    let orgPEvents = myPendingOrganizedEvents(EventCards);

    return (
        <div className="text-white">
            <div className="m-2">
                <h1 className="m-4">My Events</h1>
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                    {event.map(e => <EventCard event={e} key={e.id} />)}
                </div>
            </div>
            <div className="m-2">
                <h1 className="m-4">Organized: Accepted Events </h1>
                <div>
                    <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                        {orgAEvents.map(e => <EventCard event={e} key={e.id} />)}
                    </div>
                </div>
            </div>
            <div className="m-2">
                <h1 className="m-4">Organized: Pending Events </h1>
                <div>
                    <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                        {orgPEvents.map(e => <EventCard event={e} key={e.id} />)}
                    </div>
                </div>
            </div>
        </div>

    );
}
export default MyEventsList;