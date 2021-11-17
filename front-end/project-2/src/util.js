function encodeItem(name, price, quantity) {
    return JSON.stringify({name, price, quantity});
}

export {encodeItem}; 