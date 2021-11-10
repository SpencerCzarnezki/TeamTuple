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
    let myEvents =[];
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
            if(authContext.user){
            findEventIdsByUserId(authContext.user.id).then(eventUserKeys => {
                const promises = [];
                for (const userKey of eventUserKeys){
                    promises.push(findByEventId(userKey.eventId));
                }
                Promise.all(promises).then(events => setEvent(events) );
                console.log(event);


                // for (let index = 0; index < eventUserKeys.length; index++) {
                //    findByEventId(eventId[index].eventId).then(event => setEvent(event));
                //      console.log(event);
                //  }
                // console.log(eventId);

                // setEventIds(eventId.eventId);
                //   eventIds.forEach(id => {  myEvents.push(findByEventId(id.eventId))});
                //     console.log(eventId[0].eventId);
             
                    
                 
            
            });
        }
            



     
                      
                    
                
            
        

    },  [authContext, history]);

  



    function myOrganizedEvents() {
        let orgEvents = [];
        // let uId = authContext.user.id;

        if (authContext.user && !EventCards.length == 0) {
            EventCards.forEach(event => {
                if (event.organizerId == authContext.user.id) {
                    orgEvents.push(event);

                }
            })
        }

        return orgEvents;



    }

    let orgEvents = myOrganizedEvents(EventCards);

    return (
        <div>
            <div className="m-2">
            <h1 className="m-4">My Events</h1>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                {event.map(e => <EventCard event={e} key={e.id} />)}
            </div>
            </div>
            <div className="m-2">
            <h1 className="m-4">Organized </h1>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3 m-2">
                {orgEvents.map(e => <EventCard event={e} key={e.id} />)}
            </div>
            </div>
        </div>

    );
}
export default MyEventsList;