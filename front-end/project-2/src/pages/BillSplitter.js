import React, { useState } from 'react';
import { encodeItem } from '../util';
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import SockJsClient from 'react-stomp';
import Item from '../components/Item';


const initialOptions = {
    "client-id": "Adkjwefh_LAksnx2SC4DlZHGttsu1vhnngRkhRKjYQ-jO3ir4u0x6gkbVb2rq5kZ68tpQLIv_YmB0efU"
}
const QUEUE = '/user/queue/test';
const TOPIC = '/topic/test';

export default function BillSplitter(props) {
    const [nameValue, setNameValue] = useState("");
    const [priceValue, setPriceValue] = useState(0.00);
    const [quantityValue, setQuantityValue] = useState(0);
    const [items, setItems] = useState([]);
    const [wsClient, setWsClient] = useState(null);
    const eventId = props.match.params.id;
    const handleSend = () => {
        const js = encodeItem(nameValue, priceValue, quantityValue);
        try {
            wsClient.sendMessage('/test', js);
        } catch (e) {
            alert("Cannot connect to the server! " + e.toString());
        }
    };

    const handleReceiveMessage = (item, route) => {
        console.log(item);
        setItems(route === QUEUE ? items.concat(item) : [...items, item]);
    };

    const displayedItems = items.length > 0
        ?
        <ul>{items.map(i => <li key={i.name}><Item {...i} /></li>)}</ul>
        :
        <p>Items will be shown here.</p>;

    return (
        <>
            <SockJsClient url='http://localhost:8080/ws' topics={[QUEUE, TOPIC]}
                onMessage={handleReceiveMessage}
                ref={(client) => { setWsClient(client); }} />
            <h1>Bill Splitter</h1>
            {displayedItems}
            <label>Item Name:</label>
            <input type="text" value={nameValue} onChange={(e) => { setNameValue(e.target.value) }} />
            <br />
            <label>Price:</label>
            <input type="number" step="0.01" value={priceValue} onChange={(e) => { setPriceValue(e.target.value) }} />
            <br />
            <label>Quantity:</label>
            <input type="number" step="1" value={quantityValue} onChange={(e) => { setQuantityValue(e.target.value) }} />
            <br />
            <button onClick={handleSend}>Update Item</button>
            <PayPalScriptProvider options={initialOptions}>
                <PayPalButtons
                    createOrder={(data, actions) => {
                        return actions.order.create({
                            purchase_units: [{
                                amount: {
                                    value: '0.01'
                                },
                                payee: {
                                    email_address: 'sb-fnoyw8756325@personal.example.com'
                                }
                            }]
                        });
                    }}
                    onApprove={(data, actions) => {
                        return actions.order.capture().then(function (details) {
                            alert('Transaction completed by ' + details.payer.name.given_name);
                        });
                    }}
                />
            </PayPalScriptProvider>
        </>
    );
}