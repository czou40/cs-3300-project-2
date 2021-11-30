import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { handleCreateNewEvent, handleLogout, useEvents, useIdToken, useUser, handleJoinEvent } from '../api.js';
import Event from '../components/Event.js';
import '../dashboard.css';


export default function Dashboard({ props }) {
    const events = useEvents();
    const user = useUser();
    const [eventName, setEventName] = useState("");
    const [eventId, setEventId] = useState("");
    const userName = user ? user.displayName : "Unknown";
    const navigate = useNavigate();
    const token = useIdToken();

    const handleClick = (id) => {
        console.log(id)
        navigate("/splitter/" + id)
    };

    const handleCreate = () => {
        if (eventName.trim().length==0){
            alert("Please enter the event name!")
            return;
        }
        handleCreateNewEvent(token, eventName.trim())
            .then(res => {
                console.log(res.data);
                navigate("/splitter/" + res.data.id)
            })
            .catch(err => {
                console.log(err)
                if (err.response && err.response.data && err.response.data.message) {
                    alert(err.response.data.message)
                } else {
                    alert(err.toString())

                }
            })
    };

    const handleJoin = () => {
        if (eventId.trim().length==0){
            alert("Please enter the event ID!")
            return;
        }
        handleJoinEvent(token, eventId.trim())
            .then(res => {
                console.log(res.data);
                navigate("/splitter/" + eventId.trim())
            })
            .catch(err => {
                console.log(err)
                if (err.response && err.response.data && err.response.data.message) {
                    alert(err.response.data.message)
                } else {
                    alert(err.toString())

                }
            })
    };

    if (!token) {
        return null;
    }

    const displayedEvents = events.length > 0
        ?
        <ul>{events.map(i => <li key={i.id}><Event {...i} handleClick={handleClick} /></li>)}</ul>
        :
        <p>You do not have any events.</p>;
    return (
        <div className="dashboard">
            <div className="form">
                <div className="logout">
                    <button onClick={() => {
                        handleLogout()
                            .then(() => {
                                navigate("/login");
                            });
                    }}>Log out</button>
                </div>
                <h1>Hello, {userName}</h1>
                <h4>Create New Event</h4>
                <input type='text' required value={eventName} onChange={(e) => setEventName(e.target.value)} placeholder="New Event Name" />
                <button onClick={handleCreate}>Create new event</button>
                <h4>Join Event</h4>
                <input type='text' required value={eventId} onChange={(e) => setEventId(e.target.value)} placeholder="Existing Event ID" />
                <button onClick={handleJoin}>Join Existing event</button>
                <div className="my-events">
                    <h4>My Events</h4>
                    {displayedEvents}
                </div>
            </div>
        </div>
    )
}