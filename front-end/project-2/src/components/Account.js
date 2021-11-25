import React from 'react';

export default function Account(account) {
    return (
        <div className="account">
            <p>{account.type + ': ' + account.number}</p>
        </div>
    )
}