import { MDBBtn, MDBInput } from 'mdb-react-ui-kit';
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
        <form onSubmit={onSubmit} className="text-white m-3 ">
            <h2 className="m-3">Register</h2>
            <div className="mb-2 m-3 w-25">
                <MDBInput type="text"  label="First Name" contrast id="fname" name="fname" className=" w-50"
                    value={user.fname} onChange={onChange} />
            </div>
            <div className="mb-2 m-3 w-25">
                <MDBInput type="text" contrast label="Last Name" id="lname" name="lname" className=" m-3"
                    value={user.lname} onChange={onChange} />
            </div>
            <div className="mb-2 m-3 w-25">
                <MDBInput type="text" label="Username" contrast id="userName" name="userName" className=" m-3" required
                    value={user.userName} onChange={onChange} />
            </div>
            <div className="mb-2 m-3 w-25">
                <MDBInput type="text" label="Email Address" contrast id="email" name="email" className=" m-3"
                    value={user.email} onChange={onChange} />
            </div>
            <div className="mb-2 m-3 w-25">
                <MDBInput type="password" label="Password" contrast id="password" name="password" className=" m-3" required
                    value={user.password} onChange={onChange} />
            </div>
            <div className="mb-2 m-3 w-25">
                <MDBInput type="password" contrast id="confirmPassword" label="Confirm Password" name="confirmPassword" className=" m-3" required
                    value={user.confirmPassword} onChange={onChange} />
            </div>
            {err && <div className="alert alert-danger">{err}</div>}
            <div className=""> 
                <Link to="/" className="btn btn-secondary m-3">Cancel</Link>
                <MDBBtn type="submit" className="btn btn-primary  m-3">Save</MDBBtn>
               
            </div>
        </form>
    );
}

export default Register;