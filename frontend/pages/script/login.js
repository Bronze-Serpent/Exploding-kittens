password.onkeydown = handle;

let prevTime;
let keys = [];

function handle(e) {
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

sign.onclick = sendData();

async function sendData() {
    
    let data = {
        login: login.value,
        password: password.value,
        passwordEnterValueTime: keys
    };

    fetch("http://localhost:3000/login")
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          // code here //
          if (data.error) {
            alert("Error Password or Username"); /*displays error message*/
          } else {
            window.open(
              "/index.html"
            ); /*opens the target page while Id & password matches*/
          }
        })
        .catch((err) => {
          console.log(err);
        });
    
    
}

