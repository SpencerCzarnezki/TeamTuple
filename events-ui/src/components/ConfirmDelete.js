import { useState, useEffect, useContext, useCallback } from "react";
import { Link, useParams, useHistory } from "react-router-dom";
import { deleteEvent } from "../services/api";

function ConfirmDelete(){

    const authContext = useContext(AuthContext);
    const history = useHistory();
    const { id } = useParams();


    const handleErr = useCallback(err =>{
        if(err === 403){
            authContext.logout();
            err = "Unauthorized";
        }
        history.push("/error",err.toString)
    }, authContext, history)

    const onDelete =() =>{
        deleteEvent(id)
        .then(()=> history.push("/search"))
        .catch(handleErr);
    
    }

    return(
        <div>
        <div className = "alert alert-danger">
            This will completly delete this event.
            Are you sure?
        </div>

        <div className = "mt-2">
            <button className="btn btn-danger me -1" onClick ={onDelete}>Yes!</button>
            <Link to="/search" className="btn btn-secondary">No, Cancel</Link>

        </div>

        </div>

    )



}
export default ConfirmDelete;