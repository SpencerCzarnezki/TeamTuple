import { useState, useEffect, useContext } from "react";
import { findAllEvents } from "../services/api";
import EventCard from "./EventCard";
import AuthContext from "../contexts/AuthContext";
import { useNavigate } from "react-router";

function AdminEventsList() {
    const authContext = useContext(AuthContext);
    const [EventCards, setEvents] = useState([]);

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

    }, [authContext, history]);


    function pendingEvents() {
        let pendingEventsList = [];
        EventCards.forEach(event => {

            if (event.status === false) {
                pendingEventsList.push(event);
            }

        });
        return pendingEventsList;

    }

    function approvedEvents() {
        let approvedEventsList = [];
        EventCards.forEach(event => {

            if (event.status === true) {
                approvedEventsList.push(event);
            }

        });
        return approvedEventsList;

    }


    let pEvents = pendingEvents(EventCards);
    let aEvents = approvedEvents(EventCards);

    return (
        <div className="text-white">
            <div className="m-2">
                <h1 className="m-4">Approved Events</h1>
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                    {aEvents.map(e => <EventCard event={e} key={e.id} />)}
                </div>
            </div>
            <div className="m-2">
                <h1 className="m-4">Pending Events </h1>
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                    {pEvents.map(e => <EventCard event={e} key={e.id} />)}
                </div>
            </div>
        </div>

    );
}
export default AdminEventsList;