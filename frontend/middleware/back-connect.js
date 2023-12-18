class BackConnect {
    async registration(data) {
        let response = await fetch("http://localhost:8080/api/register/", {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(data)
        });
        

        if (response.ok) {
            //?
            let result = await response.json();
            console.log(result);
            return result;
        } else {
            //&
            console.log(response);
            return false;
        }
    }

    async autorization(data) {
        let response = await fetch("http://localhost:8080/api/auth/login", {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(data)
        });

        // если HTTP-статус в диапазоне 200-299
        // получаем тело ответа
        if (response.ok) {
            //? 
            let result = await response.json();
            return result;
        } else {
            //&
            return false;
        }
    }
}

module.exports = new BackConnect();