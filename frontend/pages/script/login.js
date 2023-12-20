password.onkeydown = handle;

let prevTime;
let keys = [];

function handle(e) {
    if (e.key == "Backspace") {
        prevTime = Date.now();
        keys.pop();
    } else {
        if (prevTime == null) {
            prevTime = Date.now();
        } else {
            keys.push((Date.now() - prevTime));
            prevTime = Date.now();
        }
    }
}

function sendData() {
    let data = {
        login: login.value,
        password: password.value,
        passwordEnterValueTime: keys
    };

    fetch("/login", {
      method: 'POST',
      headers: { 
          "Accept": "application/json, text/plain, */*",
                    "Content-type": "application/json; charset = UTF-8" 
      },
      body: JSON.stringify(data)
    })
        .then((response) => {
        if (!response.ok) {
            const container = document.getElementById('result-container');
            const textNode = document.createTextNode("Invalid credintials!!!");
            container.appendChild(textNode);
            throw new Error("Invalid Credentials"); 
        }                                          
        return response.json();
    })
        .then((data) => {
          //console.log(data); // token
          localStorage.setItem('type', data.type);
          localStorage.setItem('accessToken', data.accessToken);
          localStorage.setItem('refreshToken', data.refreshToken);
          window.location.href = "/index.html";
        })
        .catch((err) => {
          console.log(err);
        });
}

