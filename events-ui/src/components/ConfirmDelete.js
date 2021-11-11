import { useContext, useCallback } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { deleteEvent } from "../services/api";
import AuthContext from "../contexts/AuthContext";

function ConfirmDelete() {

    const authContext = useContext(AuthContext);
    const navigate = useNavigate();
    const { id } = useParams();


    const handleErr = useCallback(err => {
        if (err === 403) {
            authContext.logout();
            err = "Unauthorized";
        }
        navigate("/error", err.toString)
    }, authContext, navigate)

    const onDelete = () => {
        deleteEvent(id)
            .then(() => navigate("/search"))
            .catch(handleErr);

    }

    return (
        <div>
            <div className="alert alert-danger">
                This will completly delete this event.
                Are you sure?
            </div>

            <div className="mt-2">
                <button className="btn btn-danger me -1" onClick={onDelete}>Yes!</button>
                <Link to="/search" className="btn btn-secondary">No, Cancel</Link>

            </div>

        </div>

    )



}
export default ConfirmDelete;