import React from 'react';

export default function Item({name, price, quantity, orderer}) {
    return (
        <div style={{margin:20,backgroundColor:'antiquewhite'}}>
            <p>{'Name: ' + name}</p>
            <p>{'Price: ' + price}</p>
            <p>{'Quantity: ' + quantity}</p>
            <p>{'Orderer: ' + orderer.name}</p>

        </div>
    )
}