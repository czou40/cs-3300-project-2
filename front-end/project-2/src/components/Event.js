import React from "react";

export default function Event({ id, eventName, handleClick }) {
    return (
        <div style={{display:'flex'}}>
            <p>{eventName}</p>
            <button onClick={() => handleClick(id)}>Go</button>
        </div>
    )
};