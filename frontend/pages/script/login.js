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

    fetch("http://localhost:8080/api/auth/login", {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(data)
    })
        // проверяем ответ от backend сервера
        .then((response) => {
            if (!response.ok) {
                const container = document.getElementById('login-res');
                container.classList.remove("d-none")
                throw new Error("Invalid Credentials"); // ???
            }
            return response.json();
        })
        // забираем данные
        .then((data) => {
            localStorage.setItem('type', data.type);
            localStorage.setItem('accessToken', data.accessToken);
            localStorage.setItem('refreshToken', data.refreshToken);
            localStorage.setItem('userId', data.userId);
            window.location.href = "/waitroom.html";
        })
        .catch((err) => {
            console.log(err);
        });
}