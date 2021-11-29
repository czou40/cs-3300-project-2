import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { handleCreateNewEvent, handleLogout, useEvents, useIdToken, useUser, handleJoinEvent } from '../api.js';
import Event from '../components/Event.js';


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
        handleCreateNewEvent(token, eventName)
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

    const displayedEvents = events.length > 0
        ?
        <ul>{events.map(i => <li key={i.id}><Event {...i} handleClick={handleClick} /></li>)}</ul>
        :
        <p>You do not have any events.</p>;
    return (
        <div className="dashboard">
            <h1>Hello, {userName}</h1>
            <button onClick={() => {
                handleLogout()
                    .then(() => {
                        navigate("/login");
                    });
            }}>Log out</button>
            <p>My Events</p>
            {displayedEvents}
            <p>Create new Event</p>
            <input type='text' required value={eventName} onChange={(e) => setEventName(e.target.value)} placeholder="New Event Name" />
            <button onClick={handleCreate}>Create new event</button>
            <p>Join Event</p>
            <input type='text' required value={eventId} onChange={(e) => setEventId(e.target.value)} placeholder="Existing Event ID" />
            <button onClick={handleJoin}>Join Existing event</button>
        </div>
    )
}