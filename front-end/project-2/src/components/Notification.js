import React from 'react';

export default function Notification({notification, number}) {
    return (
        <div className="notification">
            <div className="message">
                <p>{number + '.'}</p>
                <p>{notification.requester + ' requested ' + notification.amount + ' from you for ' + notification.reason + '.'}</p>
            </div>
            <div className="payment-methods">

            </div>
        </div>
    )
}