import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { createUser } from "./services/user-api";

function Register() {

    const [user, setUser] = useState({
        fname: "",
        lname: "",
        userName: "",
        email: "",
        password: "",
        confirmPassword: ""
    });
    const [err, setErr] = useState();

    const navigate = useNavigate();

    const onChange = (evt) => {
        const clone = { ...user };
        clone[evt.target.name] = evt.target.value;
        setUser(clone);
    };

    const onSubmit = (evt) => {
        evt.preventDefault();
        if (user.password !== user.confirmPassword) {
            setErr("passwords do not match");
        } else {
            console.log(user);
            createUser(user)
                .then(() => navigate("/login"))
                .catch(err => {
                    if (err.status === 400) {
                        setErr(err.messages[0]);
                    } else {
                        navigate("/error", err.toString());
                    }
                });
        }
    }

    return (
        <form onSubmit={onSubmit}>
            <h2>Register</h2>
            <div className="mb-2">
                <label htmlFor="fname" className="form-label">First Name</label>
                <input type="text" id="fname" name="fname" className="form-control"
                    value={user.fname} onChange={onChange} />
            </div>
            <div className="mb-2">
                <label htmlFor="lname" className="form-label">Last Name</label>
                <input type="text" id="lname" name="lname" className="form-control"
                    value={user.lname} onChange={onChange} />
            </div>
            <div className="mb-2">
                <label htmlFor="userName" className="form-label">Username</label>
                <input type="text" id="userName" name="userName" className="form-control" required
                    value={user.userName} onChange={onChange} />
            </div>
            <div className="mb-2">
                <label htmlFor="email" className="form-label">Email Address</label>
                <input type="text" id="email" name="email" className="form-control"
                    value={user.email} onChange={onChange} />
            </div>
            <div className="mb-2">
                <label htmlFor="name" className="form-label">Password</label>
                <input type="password" id="password" name="password" className="form-control" required
                    value={user.password} onChange={onChange} />
            </div>
            <div className="mb-2">
                <label htmlFor="name" className="form-label">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" className="form-control" required
                    value={user.confirmPassword} onChange={onChange} />
            </div>
            {err && <div className="alert alert-danger">{err}</div>}
            <div className="mb-2">
                <button type="submit" className="btn btn-primary me-1">Save</button>
                <Link to="/" className="btn btn-secondary">Cancel</Link>
            </div>
        </form>
    );
}

export default Register;