import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { encodeItem, calculatePayment, calculateIncome } from '../util';
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import SockJsClient from 'react-stomp';
import Item from '../components/Item';
import { useIdToken, handleLogout, useUser, WS } from '../api';
import '../billsplitter.css';

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
    const user = useUser();
    const email = user ? user.email : "";
    const payment = email && event ? calculatePayment(email, event) : null;
    const income = email && event ? calculateIncome(email, event) : null;

    const QUEUE = '/user/queue/events/' + id;
    const TOPIC = '/topic/events/' + id;
    const navigate = useNavigate();
    const handleSend = () => {
        const js = encodeItem(token, id, nameValue, priceValue, quantityValue);
        if (js != null) {
            try {
                wsClient.sendMessage('/events', js);
            } catch (e) {
                alert("Cannot connect to the server! " + e.toString());
            }
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


    if (!token) {
        return null;
    }
    return (
        <>
            {token ? <SockJsClient url={WS} topics={[QUEUE, TOPIC]}
                onMessage={handleReceiveMessage}
                onConnect={() => {
                    wsClient.sendMessage('/events/join', JSON.stringify({
                        token: token,
                        eventId: id
                    }))
                }}
                ref={(client) => { setWsClient(client); }} /> : null}
            <div className="billSplitter">
                <div className="form">
                    <div className="logout">
                        <button onClick={() => {
                            handleLogout()
                                .then(() => {
                                    navigate("/login");
                                });
                        }}>Log out</button>
                    </div>
                    <h1>Bill Splitter</h1>
                    {event ? <h2>Event Name: {event.eventName}</h2> : null}
                    {event ? <p>Event ID: {id}</p> : null}
                    {event ? <p>Payer: {event.payer.name} ({event.payer.paypalEmail})</p> : null}
                    {event ? <p>Attendees:</p> : null}
                    <ol>
                        {event ? event.attendees.map(i => <li className="attendee" key={i.email}>{i.name}</li>) : null}
                    </ol>
                    <button onClick={() => {
                        navigate("/dashboard");
                    }}>Return to Dashboard</button>
                    <h3>Items</h3>
                    {displayedItems}
                    <h3>Add Item</h3>
                    <label>Item Name:</label>
                    <input type="text" value={nameValue} onChange={(e) => { setNameValue(e.target.value) }} />
                    <br />
                    <label>Price:</label>
                    <input type="number" step="0.01" value={priceValue} onChange={(e) => { setPriceValue(e.target.value) }} />
                    <br />
                    <label>Quantity:</label>
                    <input type="number" step="1" value={quantityValue} onChange={(e) => { setQuantityValue(e.target.value) }} />
                    <br />
                    <button onClick={handleSend}>Add Item</button>
                    <h3>Payment</h3>
                    {payment != null ?
                        (payment >= 0 ?
                            <p>You need to pay ${payment} to {event.payer.name} ({event.payer.paypalEmail})</p>
                            :
                            <p>You are the payer! You should receive ${income} from other attendees.</p>
                        )
                        :
                        null
                    }
                    {
                        event && event.payer.paypalEmail && payment && payment > 0 ?
                            < PayPalScriptProvider options={initialOptions}>
                                <PayPalButtons
                                    createOrder={(data, actions) => {
                                        console.log(event)
                                        return actions.order.create({
                                            purchase_units: [{
                                                amount: {
                                                    value: payment
                                                },
                                                payee: {
                                                    email_address: event.payer.paypalEmail
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
                            :
                            null
                    }
                </div>
            </div>
        </>
    );
}