import React from 'react';

export default function Item({name, price, quantity, orderer}) {
    return (
        <div className="item">
            {'Name: ' + name}
            <div className="details">
                <span>{'Price: $' + price}</span>
                <span>{'Quantity: ' + quantity}</span>
                <span>{'Orderer: ' + orderer.name}</span>
            </div>
        </div>
    )
}