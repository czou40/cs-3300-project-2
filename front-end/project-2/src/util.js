const alphanumeric = /^[A-Za-z0-9 ]+$/;

function isNumber(value) {
    return !isNaN(value) && !isNaN(parseFloat(value));
}

function encodeItem(token, eventId, name, price, quantity) {
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
    return JSON.stringify({ token, eventId, name, price, quantity });
}

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function calculatePayment(email, event) {
    email = email.trim();
    let cents = 0;
    if (email===event.payer.email) {
        return -1;
    } else {
        for (let i of event.items) {
            if (email===i.orderer.email) {
                cents += Math.round(i.price * 100) * i.quantity;
            }
        }
    }
    return cents / 100;
}

function calculateIncome(email, event) {
    email = email.trim();
    let cents = 0;
    if (email!==event.payer.email) {
        return -1;
    } else {
        for (let i of event.items) {
            if (email!==i.orderer.email) {
                cents += Math.round(i.price * 100) * i.quantity;
            }
        }
    }
    return cents / 100;
}


export { encodeItem, validateEmail, calculatePayment, calculateIncome };
