import { MDBInput } from "mdb-react-ui-kit";
import { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "./contexts/AuthContext";
import { login } from "./services/auth-api";

function Login() {

    const [candidate, setCandidate] = useState({
        username: "",
        password: ""
    });
    const [hasError, setHasError] = useState(false);

    const navigate = useNavigate();
    const authContext = useContext(AuthContext);

    const onChange = (evt) => {
        const clone = { ...candidate };
        clone[evt.target.name] = evt.target.value;
        setCandidate(clone);
    }

    const onSubmit = (evt) => {
        evt.preventDefault();
        login(candidate)
            .then(principal => {
                authContext.login(principal);
                navigate("/");
            }).catch(() => setHasError(true));
    }

    return (
        <form onSubmit={onSubmit}>
            <h2 className="m-4">Login</h2>
            <div className="m-4 mb-2 w-25">
                <MDBInput label="Email" id="username" name="username" type="email" size="lg" className="form-control" required
                value={candidate.username} onChange={onChange}></MDBInput>
            </div>
            <div className=" m-4 mb-2 w-25">
            <MDBInput label="Password" id="password" name="password" type="password" size="lg" className="form-control" required
                value={candidate.password} onChange={onChange}></MDBInput>


            </div>
            <div>
                <Link to="/" className="btn btn-outline-primary me-2 m-4">Cancel</Link>
                <button type="submit" className="btn btn-primary m-4">Log In</button>
            </div>
            {hasError && <div className="alert alert-danger m-4">Bad credentials...</div>}
        </form>
    );
}

export default Login;