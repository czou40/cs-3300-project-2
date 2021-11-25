import { initializeApp } from "firebase/app";
import { getAuth, signInWithEmailAndPassword, setPersistence, browserLocalPersistence, onAuthStateChanged, signOut } from "firebase/auth";
import { useState, useEffect } from "react";
import axios from 'axios';

const firebaseConfig = {
    apiKey: "AIzaSyCIwViG-QtSFLVrBVSzY9nFkMK2nUhoCu8",
    authDomain: "cs-3300-project-1-93e05.firebaseapp.com",
    projectId: "cs-3300-project-1-93e05",
    storageBucket: "cs-3300-project-1-93e05.appspot.com",
    messagingSenderId: "190278454096",
    appId: "1:190278454096:web:c12f71f20d824460f7fa55",
    measurementId: "G-MSDVLQY2VY"
};
const SIGNUP_URL = './signup'

// Initialize Firebase
initializeApp(firebaseConfig);
const auth = getAuth();
const handleLogin = async (email, password) => {
    return setPersistence(auth, browserLocalPersistence)
        .then(() => {
            return signInWithEmailAndPassword(auth, email, password);
        })
}

const handleSignup = async (name, email, password) => {
    return axios.post(SIGNUP_URL, {
        name, email, password
    });
}

const handleLogout = async () => {
    return signOut(auth);
}

const useUser = () => {
    const [authUser, setAuthUser] = useState(null);
    useEffect(() => {
        const unlisten = onAuthStateChanged(auth, authUser => {
            if(authUser) {
                setAuthUser(authUser);
            } else {
                setAuthUser(null);
            }
        });
        return () => {
            unlisten();
        }
    }, []);
    return authUser;
}

const useIdToken = () => {
    const [token, setToken] = useState(null);
    const user = useUser();
    useEffect(()=>{
        if (user) {
            user.getIdToken()
            .then(token=>{
                setToken(token);
            })
            .catch(err=>{
                console.error(err);
                setToken(null);
            })
        }
    }, [user]);
    return token;
}

export { handleLogin, handleSignup, useUser, useIdToken, handleLogout };