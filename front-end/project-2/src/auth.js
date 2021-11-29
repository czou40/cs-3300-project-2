import { initializeApp } from "firebase/app";
import { getAuth, signInWithEmailAndPassword, setPersistence, browserLocalPersistence, onAuthStateChanged, signOut } from "firebase/auth";
import { useState, useEffect } from "react";

import axios from 'axios';

const firebaseConfig = {
    apiKey: "AIzaSyAiXnow51Kxx-njcfT0UdnArP5-TI5jKwE",
    authDomain: "cs-3300-project-2-75b15.firebaseapp.com",
    projectId: "cs-3300-project-2-75b15",
    storageBucket: "cs-3300-project-2-75b15.appspot.com",
    messagingSenderId: "786958645951",
    appId: "1:786958645951:web:413554094b35e08e65640a",
    measurementId: "G-35YH4CMQPW"
};
const BASE_URL = 'http://localhost:8080'
const SIGNUP_URL = BASE_URL + '/signup'
const EVENT_URL = BASE_URL + '/me/events'

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
            if (authUser) {
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
    useEffect(() => {
        if (user) {
            user.getIdToken()
                .then(token => {
                    setToken(token);
                })
                .catch(err => {
                    console.error(err);
                    setToken(null);
                })
        }
    }, [user]);
    return token;
}

const useEvents = () => {
    const [events, setEvents] = useState([]);
    const token = useIdToken();
    useEffect(() => {
        if (token) {
            axios.get(EVENT_URL, {
                headers: {
                  'Authorization': 'Bearer ' + token
                }
              })
              .then(res=>{
                  console.log(res.data)
                  setEvents(res.data);
              })
              .catch(err=>{
                  console.error(err.response.data);
              })
        }
    }, [token])
    return events;
};
export { handleLogin, handleSignup, useUser, useIdToken, handleLogout, useEvents };