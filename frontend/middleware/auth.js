const auth = (req, res, next) => {

    // Проверка токена
    if (false) {
        return res.status(403).send("A token is required for authentication");
    }
    
    return next();
};

module.exports = auth;