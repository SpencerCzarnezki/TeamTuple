import { MDBBtn, MDBInput } from "mdb-react-ui-kit";
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
        <form onSubmit={onSubmit} className="text-white">
            <h2 className="m-5 ">Login</h2>
            <div className="m-4 mb-2 w-25">
                <MDBInput label="Username" contrast id="username" name="username" type="text" size="lg" className="form-control" required
                value={candidate.username} onChange={onChange}></MDBInput>
            </div>
            <div className=" m-4 mb-2 w-25">
            <MDBInput label="Password" id="password" contrast name="password" type="password" size="lg" className="form-control" required
                value={candidate.password} onChange={onChange}></MDBInput>


            </div>
            <div>
                <Link to="/" className="btn btn-warning me-2 m-4">Cancel</Link>
                <MDBBtn type="submit"  className="btn btn-primary m-4">Log In</MDBBtn>
            </div>
            {hasError && <div className="alert alert-danger m-4">Bad credentials...</div>}
        </form>
    );
}

export default Login;