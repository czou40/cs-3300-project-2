import React,{ useState } from 'react';
import { handleSignup, useIdToken } from '../auth';
import { Navigate } from 'react-router-dom';


function Signup() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
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
        handleSignup(name, email, password)
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
                <input type="text" name="name" placeholder="Name" autoComplete="name" onChange={(e) => { setName(e.target.value) }} value={name} />
                <input type="email" name="email" placeholder="Email" autoComplete="email" onChange={(e) => { setEmail(e.target.value) }} value={email} />
                <input type="password" name="password" placeholder="Password" autoComplete="current-password" onChange={(e) => { setPassword(e.target.value) }} value={password} />
                <input type="password" name="password" placeholder="Confirm Password" onChange={(e) => { setConfirmedPassword(e.target.value) }} value={confirmedPassword} />
                <button onClick={handleClick}>Login</button>
            </form>
            <p id="error" style={{ display: error ? "inherit" : "none" }}>{error}</p>
            <p>Already have an account? <a href="./login">Log in</a></p>
        </div>
    );
}

export default Signup;