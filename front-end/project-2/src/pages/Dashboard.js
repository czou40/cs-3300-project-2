import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useEvents, useIdToken, useUser } from '../auth.js';
import Event from '../components/Event.js';


export default function Dashboard({ props }) {
    const events = useEvents();
    const user = useUser();
    const userName = user ? user.displayName : "Unknown";
    const navigate = useNavigate();

    const handleClick = (id) => {
        console.log(id)
        navigate("/splitter/"+id)
    };

    const displayedEvents = events.length > 0
        ?
        <ul>{events.map(i => <li key={i.id}><Event {...i} handleClick={handleClick} /></li>)}</ul>
        :
        <p>Items will be shown here.</p>;
    return (
        <div className="dashboard">
            <h1>Hello, {userName}</h1>
            {displayedEvents}
        </div>
    )
}