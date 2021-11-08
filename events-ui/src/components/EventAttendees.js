import userEvent from "@testing-library/user-event";
import Event from "../Event";

function EventAttendees({attendees}){
    {console.log(attendees)}
    if(attendees.length !== 0){
         return(
            <div>
                {attendees && attendees.user.fname} {attendees && attendees.user.lname}, {attendees && attendees.user.username} 
            </div>
         );
            
    }
    return (
        <div style={{style: "italics"}}>No Attendees</div>
    );
};
export default EventAttendees;