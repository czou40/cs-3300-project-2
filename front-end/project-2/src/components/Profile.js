import React from 'react';
import Account from './Account.js';

export default function Profile({profile}) {
    return (
        <div className="profile">
            <h1>{profile.username}</h1>
            {profile.accounts.map((account, i) => {
                return <Account account={account} />
            })}
            <button>Add account</button>
        </div>
    )
}