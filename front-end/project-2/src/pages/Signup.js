import React,{ useState } from 'react';
import { handleSignup, useIdToken } from '../api';
import { Navigate } from 'react-router-dom';
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
        <div className="container">
            <h2>Sign Up</h2>
            <form name="signup" method="post">
                <input type="text" name="name" placeholder="Name" autoComplete="name" required onChange={(e) => { setName(e.target.value) }} value={name} />
                <input type="email" name="email" placeholder="Email" autoComplete="email" required onChange={(e) => { setEmail(e.target.value) }} value={email} />
                <input type="password" name="password" placeholder="Password" required autoComplete="current-password" onChange={(e) => { setPassword(e.target.value) }} value={password} />
                <input type="password" name="password" placeholder="Confirm Password" required onChange={(e) => { setConfirmedPassword(e.target.value) }} value={confirmedPassword} />
                <input type="email" name="paypalEmail" placeholder="Paypal Email" required onChange={(e) => { setPaypalEmail(e.target.value) }} value={paypalEmail} />
                <button onClick={handleClick}>Login</button>
            </form>
            <p id="error" style={{ display: error ? "inherit" : "none" }}>{error}</p>
            <p>Already have an account? <a href="./login">Log in</a></p>
        </div>
    );
}

export default Signup;