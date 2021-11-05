import { useState, useEffect, useContext } from "react";
import AuthContext from "./contexts/AuthContext";


function EventInfoPage({ event }) {


    const authContext = useContext(AuthContext);

    return (
        <div>
            <div>
                <div>
                    <h5>{event.title}</h5>
                    <div className="row">
                        <div className="col"><strong>Category:</strong> </div>
                        <div className="col">{event.category}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Description:</strong> </div>
                        <div className="col">{event.description}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Date and Time:</strong> </div>
                        <div className="col">{event.date}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Duration:</strong> </div>
                        <div className="col">{event.duration}</div>
                    </div>
                    <div className="row">
                        <div className="col"><strong>Capacity:</strong> </div>
                        <div className="col">{event.capacity}</div>
                    </div>

                </div>
                <div>
                    
                </div>


                {auth.credentials && auth.credentials.hasAuthority("USER", "ADMIN") &&
                    <div className="card-footer text-center">
                        {auth.credentials.hasAuthority("ADMIN") &&
                            <Link to={`/delete/${game.id}`} className="btn btn-danger me-1">Delete</Link>}
                        <Link to={`/edit/${game.id}`} className="btn btn-secondary">Edit</Link>
                    </div>
                }
            </div>
        </div>

    );

}

export default EventInfoPage;