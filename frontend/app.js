require("dotenv").config();
const express = require("express");
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

app.get('/login', (req, res) => {
    res.sendFile(createPath('login'));
});

app.get('/register', (req, res) => {
    res.sendFile(createPath('register'));
});

app.get('/room', (req, res) => {
    res.render(createPath('room'));
});

app.get('/waitroom', (req, res) => {
    res.sendFile(createPath('waitroom'));
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