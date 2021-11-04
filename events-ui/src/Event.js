import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "./contexts/AuthContext";

function Event({ event }) {

    const auth = useContext(AuthContext);

    return (
        <div className="col">
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
                    {/* <div className="row">
                        <div className="col"><strong>Location:</strong> </div>
                        <div className="col">{event.duration}</div>
                    </div> */}
                    <div className="row">
                        <div className="col"><strong>Number of available spots:</strong> </div>
                        <div className="col">{event.capacity}</div>
                    </div>
                
                    {/* <div className="row">
                        <div className="col"><strong>Attendees:</strong> </div>
                        <div className="col">{event.category}</div>
                    </div> */}
                </div>
                {auth.credentials && auth.credentials.hasAuthority("USER", "ADMIN") &&
                    <div className="card-footer text-center">
                        {auth.credentials.hasAuthority("ADMIN") &&
                            <Link to={`/delete/${event.id}`} className="btn btn-danger me-1">Delete</Link>}
                        {auth.credentials.hasAuthority("ADMIN") &&
                            <Link to={`/edit/${event.id}`} className="btn btn-secondary">Edit</Link>}
                    </div>
                }
            </div>
        </div>
    );
}

export default Event;