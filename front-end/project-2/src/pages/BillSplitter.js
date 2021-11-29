import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { encodeItem } from '../util';
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import SockJsClient from 'react-stomp';
import Item from '../components/Item';
import { useIdToken, handleLogout } from '../api';


const initialOptions = {
    "client-id": "Adkjwefh_LAksnx2SC4DlZHGttsu1vhnngRkhRKjYQ-jO3ir4u0x6gkbVb2rq5kZ68tpQLIv_YmB0efU"
}

export default function BillSplitter(props) {
    const [nameValue, setNameValue] = useState("");
    const [priceValue, setPriceValue] = useState(0.00);
    const [quantityValue, setQuantityValue] = useState(0);
    const [event, setEvent] = useState(null);
    const [wsClient, setWsClient] = useState(null);
    const { id } = useParams();
    const token = useIdToken();
    const QUEUE = '/user/queue/events/' + id;
    const TOPIC = '/topic/events/' + id;
    const navigate = useNavigate();
    const handleSend = () => {
        const js = encodeItem(token, id, nameValue, priceValue, quantityValue);
        try {
            wsClient.sendMessage('/events', js);
        } catch (e) {
            alert("Cannot connect to the server! " + e.toString());
        }
    };

    const handleReceiveMessage = (event, route) => {
        console.log(event);
        setEvent(event);
    };

    const displayedItems = event && event.items.length > 0
        ?
        <ul>{event.items.map(i => <li key={i.id}><Item {...i} /></li>)}</ul>
        :
        <p>Items will be shown here.</p>;



    return (
        <>
            {token ? <SockJsClient url='http://localhost:8080/ws' topics={[QUEUE, TOPIC]}
                onMessage={handleReceiveMessage}
                onConnect={() => {
                    wsClient.sendMessage('/events/join', JSON.stringify({
                        token: token,
                        eventId: id
                    }))
                }}
                ref={(client) => { setWsClient(client); }} /> : null}
            <h1>Bill Splitter</h1>
            {event?<h2>Event Name: {event.eventName}</h2>:null}
            {event?<h2>Payer: {event.payer.name}</h2>:null}
            {event?<h2>Attendees:</h2>:null}

            {event?event.attendees.map(i=><h3 key={i.email}>{i.name}</h3>):null}

            <button onClick={() => {
                handleLogout()
                    .then(() => {
                        navigate("/login");
                    });
            }}>Log out</button>
            <button onClick={() => {
                navigate("/dashboard");
            }}>Return to Dashboard</button>
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