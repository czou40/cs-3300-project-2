import './App.css';
import React, { useState } from 'react';
import SockJsClient from 'react-stomp';
import { encodeItem } from './util';
import Item from './components/Item';



function App() {
  const [wsClient, setWsClient] = useState(null);
  const [items, setItems] = useState([]);
  const [nameValue, setNameValue] = useState("");
  const [priceValue, setPriceValue] = useState(0.00);
  const [quantityValue, setQuantityValue] = useState(0);
  const handleSend = () => {
    const js = encodeItem(nameValue, priceValue, quantityValue);
    try {
      wsClient.sendMessage('/test', js);
    } catch (e) {
      alert("Cannot connect to the server!")
    }
  };

  const handleReceiveMessage = (item) => {
    console.log(item);
    setItems([...items, item]);
  };

  const displayedItems = items.length > 0
    ?
    <ul>{items.map(i => <li key={i.name}><Item {...i}/></li>)}</ul>
    :
    <p>Items will be shown here.</p>;
  return (
    <div>
      <SockJsClient url='http://localhost:8080/ws' topics={['/topic/test']}
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
    </div>
  );
}

export default App;
