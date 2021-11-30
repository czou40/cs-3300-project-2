import React,{ useState } from 'react';
import { handleSignup, useIdToken } from '../api';
import {Link, Navigate} from 'react-router-dom';
import {validateEmail} from '../util'


function Signup() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [paypalEmail, setPaypalEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmedPassword, setConfirmedPassword] = useState("");
    const [error, setError] = useState("");
    const token = useIdToken();
    const [signedUp, setSignedUp] = useState(false);
    if (token) {
        return <Navigate to='/dashboard' />
    }
    if (signedUp) {
        return <Navigate to='/login' />
    }
    const handleClick = (e) => {
        e.preventDefault();
        if (password !== confirmedPassword) {
            setError("Passwords do not match.");
            return;
        }
        if (!validateEmail(email) || !validateEmail(paypalEmail)){
            setError("Please provide a valid email!");
            return;
        }
        handleSignup(name, email, password, paypalEmail)
            .then(result => {
                console.log(result);
                alert(result.data.message);
                setError("");
                setSignedUp(true);
            }).catch(error => {
                console.log(error)
                if (error.response && error.response.data && error.response.data.message) {
                    setError(error.response.data.message);
                } else {
                    setError(error.message);
                }
            });
    };
    return (
        <div className="signup-page">
            <div className="form">
                <div className="signup">
                    <div className="signup-header">
                        <h3>Sign Up</h3>
                        Please enter new authentication credentials.
                    </div>
                </div>
                <form name="signup-form" method="post">
                    <input type="text" name="name" placeholder="Name" autoComplete="name" required onChange={(e) => { setName(e.target.value) }} value={name} />
                    <input type="email" name="email" placeholder="Email" autoComplete="email" required onChange={(e) => { setEmail(e.target.value) }} value={email} />
                    <input type="password" name="password" placeholder="Password" required autoComplete="current-password" onChange={(e) => { setPassword(e.target.value) }} value={password} />
                    <input type="password" name="password" placeholder="Confirm Password" required onChange={(e) => { setConfirmedPassword(e.target.value) }} value={confirmedPassword} />
                    <input type="email" name="paypalEmail" placeholder="Paypal Email" required onChange={(e) => { setPaypalEmail(e.target.value) }} value={paypalEmail} />
                    <p id="error" style={{ display: error ? "inherit" : "none" }}>{error}</p>
                    <button onClick={handleClick}>sign up</button>
                    <p className="message">Already have an account?<Link to='/login'>Log in</Link></p>
                </form>
            </div>
        </div>
    );
}

export default Signup;