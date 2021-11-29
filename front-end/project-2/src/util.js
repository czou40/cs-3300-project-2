function encodeItem(token, eventId, name, price, quantity) {
    return JSON.stringify({token, eventId, name, price, quantity});
}

export {encodeItem}; 