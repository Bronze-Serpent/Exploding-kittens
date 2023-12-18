password.onkeydown = handlePass;

let prevTime;
let keys = [];

function handlePass(e) {
    if (e.key == "Backspace") {
        keys.pop();
    } else {
        if (prevTime == null) {
            prevTime = Date.now();
        } else {
            keys.push((Date.now() - prevTime));
            prevTime = Date.now();
        }
    }
    passwordEnterValueTime.value = JSON.stringify(keys);
}

passwordConfirmation.onkeydown = handleConfirm;

let prevTimePasswordConfirmation;
let keysPasswordConfirmation = [];

function handleConfirm(e) {
    if (e.key == "Backspace") {
        keysPasswordConfirmation.pop();
    } else {
        if (prevTimePasswordConfirmation == null) {
            prevTimePasswordConfirmation = Date.now();
        } else {
            keysPasswordConfirmation.push((Date.now() - prevTimePasswordConfirmation));
            prevTimePasswordConfirmation = Date.now();
        }
    }
    passwordConfirmationEnterValueTime.value = JSON.stringify(keysPasswordConfirmation);
}