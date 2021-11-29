import React from 'react';
import CreateBill from '../components/CreateBill.js';
import Notification from '../components/Notification.js';
import Profile from '../components/Profile.js';


export default function Dashboard({ profile, notifications }) {
    return (
        <div className="dashboard">
            <Profile profile={profile} />
            <CreateBill user={profile} />
            <div className="notifications">
                {notifications.map((notification, i) => {
                    return <Notification notification={notification} number={i + 1} />
                })}
            </div>

        </div>
    )
}