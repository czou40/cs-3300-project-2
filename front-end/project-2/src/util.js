const alphanumeric = /^[A-Za-z0-9 ]+$/;

function encodeItem(name, price, quantity) {
    function isNumber(value) {
        return !isNaN(value) && !isNaN(parseFloat(value));
    }

    if (!name) {
        window.alert("The item name is empty.");
        return null;
    } else if (!(name.trim())) {
        window.alert("The item name cannot be whitespace only.");
        return null;
    } else if(!name.match(alphanumeric)) {
        window.alert("The item name must only contain alphanumeric and whitespace characters.");
        return null;
    } else if (!isNumber(price) || price <= 0) {
        window.alert("The submitted price is not a positive number.");
        return null;
    } else if (!isNumber(quantity) || !(quantity % 1 === 0) || quantity <= 0) {
        window.alert("The submitted quantity is not a positive integer.");
        return null;
    }
    return JSON.stringify({name, price, quantity});
}

export {encodeItem};
