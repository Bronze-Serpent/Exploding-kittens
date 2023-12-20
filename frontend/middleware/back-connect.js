class BackConnect {
    async defaultRequest(url, data) {
        let repsonse = await fetch(url, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(data)
        });

        if (repsonse.ok) {
            let result = await repsonse.json(); // возвращать в любом случае? с последующей обработкой status code
            return result;
        } else {
            return false;
        }
    }
}

module.exports = new BackConnect();