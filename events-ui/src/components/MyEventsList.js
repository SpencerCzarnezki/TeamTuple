import { useState, useEffect, useContext } from "react";
import { useHistory } from "react-router";
import { findAllEvents, findByEventId } from "./services/api";
import EventCard from "./EventCard";
import AuthContext from "../contexts/AuthContext";


function MyGamesList() {

    const navigate = useNavigate();
    const [EventCards, setEvents] = useState([]);
    const history = useHistory();
    const AuthContext = useContext(AuthContext);

    useEffect(() => {
        findAllEvents().then(result => setEvents(result))
            .catch((err) => {
                if (err === 403) {
                    AuthContext.logout();
                    history.push("/login");

                } else {
                    history.push("/error", err.toString());

                }
            }, [AuthContext, history]);

    })

    function myAttendingEvents(){

        
        let myEvents =[];
        let UId = AuthContext.user.id;

        if(!EventCards.length==0){
            EventCards.forEach(event => {
            let eventAttendees = event.attendees;
            eventAttendees.forEach(attendee => {
                if(attendee.id == UId){
                    myEvents.add(event);
                }

            })
            });
        }

        return myEvents;
    }



    function myOrganizedEvents(){
        let orgEvents =[];
        let uId = AuthContext.user.id;

        if(!EventCards.length==0){
            EventCards.forEach(event =>{
                if(event.organizerId == uId){
                    orgEvents.add(event);

                }
            })
        }

        return orgEvents;



    }

    let myEvents  = myAttendingEvents(EventCards);
    let orgEvents = myOrganizedEvents(EventCards);

    return (
        <div>
            <h1>My Events</h1>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3">
                {myEvents.map(e => <EventCard event={e} key={e.id} />)}
            </div>
            <h1>Organized </h1>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3">
                {orgEvents.map(e => <EventCard event={e} key={e.id} />)}
            </div>
        </div>

    );
}