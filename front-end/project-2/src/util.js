function encodeItem(token, eventId, name, price, quantity) {
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