import React, { useState } from 'react';
import { Navigate, Link } from 'react-router-dom';
import { handleLogin, useIdToken } from '../firebase'
import '../login.css'

export default function Login(props) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState('');
    const token = useIdToken();
    if (token) {
        console.log(token);
        return <Navigate to='/dashboard' />
    }
    const login = (e) => {
        e.preventDefault();
        handleLogin(email, password)
            .catch(error => {
                if (error.response && error.response.data && error.response.data.message) {
                    setError(error.response.data.message);
                } else {
                    setError(error.message);
                }
            });
    }
    return (
        <div className="login-page">
            <div className="form">
                <div className="login">
                    <div className="login-header">
                        <h3>LOGIN</h3>
                        <p>Please enter your credentials to login.</p>
                    </div>
                </div>
                <form className="login-form" onSubmit={login}>
                    <input type="text" placeholder="Username" value={email} onChange={(e) => setEmail(e.target.value)} />
                    <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    <p id="error" style={{ display: error ? "block" : "none" }}>{error}</p>
                    <button>login</button>
                    <p className="message">Not registered?<Link to='/signup' >Create an account</Link></p>
                </form>
            </div>
        </div>
    );
}