import { useState, useEffect, useContext } from "react";
import { findAllEvents, findByEventId } from "../services/api";
import EventCard from "./EventCard";
import AuthContext from "../contexts/AuthContext";
import { useNavigate } from "react-router";


function MyEventsList() {
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();
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
                
            }, [authContext, history]);

    })

    function myAttendingEvents(){

        
        let myEvents =[];
        // let UId = authContext.user.id;

        if(authContext.user &&  !EventCards.length==0){
            // console.log(EventCards);
            // for(let x = 0; x< EventCards.length; x++){
            //    let eventAttendees = EventCards[x];
            // //    console.log(eventAttendees);
            //    if(eventAttendees.length !== 0){
            //    for(let y = 0; y < eventAttendees.length; y++){
            //             console.log(eventAttendees);
            //        if(eventAttendees[y].id == authContext.user.id){
            //             myEvents.push(EventCards[x]);
            //        }
            //    }
            // }
            // }

            EventCards.forEach(event => {
            let eventAttendees = event.attendees;
            eventAttendees.forEach(attendee => {
                if(attendee.id ==  authContext.user.id){
                    myEvents.push(event);
                }

            })
            });
        }

        return myEvents;
    }



    function myOrganizedEvents(){
        let orgEvents =[];
        // let uId = authContext.user.id;

        if(authContext.user && !EventCards.length==0){
            EventCards.forEach(event =>{
                if(event.organizerId ==  authContext.user.id){
                    orgEvents.push(event);

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
export default MyEventsList;