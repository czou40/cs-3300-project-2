function encodeItem(token, eventId, name, price, quantity) {
    return JSON.stringify({ token, eventId, name, price, quantity });
}

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}


export { encodeItem, validateEmail };