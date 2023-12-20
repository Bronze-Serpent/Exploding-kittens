require("dotenv").config();
const express = require("express");
const bcrypt = require("bcryptjs");
const path = require('path');

const createPath = (page) => path.resolve(__dirname, 'pages', `${page}.html`);
const back = require("./middleware/back-connect");
const auth = require("./middleware/auth");

const app = express();

app.use(express.json());
app.use(express.static('pages'));
app.use(express.urlencoded({ extended: false }));

app.get('/welcome', (req, res) => {
    res.status(200).send("Welcome 🙌");
    //res.redirect("/index");
});

app.get('/index', auth, (req, res) => {
    res.sendFile(createPath('index'));
});

app.get('/test', (req, res) => {
    console.log("I in server!!!");
    res.sendFile(createPath('test'));
});

app.post('/test', (req, res) => {
    console.log("I in server - post!!");

    res.status(200).redirect("/index");
});

app.get('/login', (req, res) => {
    res.sendFile(createPath('login'));
});

app.post('/login', async (req, res) => {
    try {
        const { login, password, passwordEnterValueTime } = req.body;

        if (!(login && password)) {
            res.status(400).send("All input is required");
        }

        let user = {
            login: login,
            password: password,
            passwordEnterValueTime: eval(passwordEnterValueTime)
        }

        const result = await back.defaultRequest("http://localhost:8080/api/auth/login", user);

        if (result) {
            res.status(200).send(result);
        } else {
            res.status(400).send();
        }

    } catch (err) {
        console.log(err);
    }
});


app.get('/register', (req, res) => {
    res.sendFile(createPath('register'));
});

app.post('/register', async (req, res) => {
    try {
        const { login, password, passwordConfirmation, passwordEnterValueTime, passwordConfirmationEnterValueTime } = req.body;

        if (!(login && password && passwordConfirmation)) {
            res.status(400).send("All input is required");
        }

        if (password != passwordConfirmation) {
            res.status(400).send("Password mismatch!!!");
        }

        let user = {
            login: login,
            password: password,
            passwordEnterValueTime: eval(passwordEnterValueTime),
            passwordConfirmation: passwordConfirmation,
            passwordConfirmationEnterValueTime: eval(passwordConfirmationEnterValueTime)
        }

        const result = await back.defaultRequest("http://localhost:8080/api/register/", user);

        if (result) {
            res.status(200).send(result);
        } else {
            res.status(400).send();
        }
    } catch (err) {
        console.log(err);
    }
});

// Not found page
app.use("*", (req, res) => {
    res.status(404).json({
        success: "false",
        message: "Page not found",
        error: {
            statusCode: 404,
            message: "You reached a route that is not defined on this server",
        },
    });
});

module.exports = app;