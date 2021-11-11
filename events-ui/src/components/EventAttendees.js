import { MDBIcon } from "mdb-react-ui-kit";

function EventAttendees({ attendees }) {
    if (attendees.length !== 0) {
        return (
            <div>
                <MDBIcon fas icon="user" /> {attendees && attendees.user.fname} {attendees && attendees.user.lname}, {attendees && attendees.user.username}
            </div>
        );

    }
    return (
        <div style={{ style: "italics" }}>No Attendees</div>
    );
};
export default EventAttendees;