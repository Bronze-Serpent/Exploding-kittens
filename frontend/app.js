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
    console.log("index req - ", req.body)
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

        const result = await back.autorization(user);

        if (result) {
            //записать токены
            //console.log(result);
            
            res.redirect("/index");
        } else {
            res.status(400).send("Invalid Credentials")
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

        const result = await back.registration(user);

        if (result) {
            res.redirect("/login");
        } else {
            res.send("Invalid input");
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