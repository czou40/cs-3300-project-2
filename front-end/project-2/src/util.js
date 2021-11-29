function encodeItem(name, price, quantity) {
    function isNumber(value) {
        return !isNaN(value) && !isNaN(parseFloat(value));
    }

    if (!name) {
        window.alert("The item name is empty.");
        return null;
    } else if (!isNumber(price) || price <= 0) {
        window.alert("The submitted price is not a positive number.");
        return null;
    } else if (!isNumber(quantity) || !Number.isInteger(parseInt(quantity)) || quantity <= 0) {
        window.alert("The submitted quantity is not a positive whole number.");
        return null;
    }
    return JSON.stringify({name, price, quantity});
}

export {encodeItem};
