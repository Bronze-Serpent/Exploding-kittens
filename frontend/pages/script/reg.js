password.onkeydown = handlePass;

let prevTime;
let keys = [];

function handlePass(e) {
    if (e.key == "Backspace") {
        keys.pop();
        prevTime = Date.now();
    } else {
        if (prevTime == null) {
            prevTime = Date.now();
        } else {
            keys.push((Date.now() - prevTime));
            prevTime = Date.now();
        }
    }
}

passwordConfirmation.onkeydown = handleConfirm;

let prevTimePasswordConfirmation;
let keysPasswordConfirmation = [];

function handleConfirm(e) {
    if (e.key == "Backspace") {
        keysPasswordConfirmation.pop();
        prevTimePasswordConfirmation = Date.now();
    } else {
        if (prevTimePasswordConfirmation == null) {
            prevTimePasswordConfirmation = Date.now();
        } else {
            keysPasswordConfirmation.push((Date.now() - prevTimePasswordConfirmation));
            prevTimePasswordConfirmation = Date.now();
        }
    }
}

function sendData() {
    let data = {
        login: login.value,
        password: password.value,
        passwordEnterValueTime: keys,
        passwordConfirmation: passwordConfirmation.value,
        passwordConfirmationEnterValueTime: keysPasswordConfirmation
    };

    fetch("/register", {
      method: 'POST',
      headers: { 
          "Accept": "application/json, text/plain, */*",
                    "Content-type": "application/json; charset = UTF-8" 
      },
      body: JSON.stringify(data)
    })
        .then((response) => {
        if (!response.ok) {
            const container = document.getElementById('register-res');
            container.classList.remove("d-none")
            throw new Error("User exist!"); 
        }                                          
        return response.json();
    })
        .then((data) => {
          window.location.href = "/login.html";
        })
        .catch((err) => {
          console.log(err);
        });
}