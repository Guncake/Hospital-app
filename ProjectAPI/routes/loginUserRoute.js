const router = require('express').Router();
const StandardLoginResponse = require('../model/StandardLoginResponse');
const crypto = require('crypto');
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
const moment = require('moment');

router.post('/login', (request, response) => {
  var accounts = request.accounts;
  var userToLook = request.body.login;
  console.log(moment().format("YYYY MM DD hh:mm:ss - ") + userToLook + "is trying to log in");

  for(i = 0; i<accounts.accounts.length; i++){
    if(accounts.accounts[i].login == userToLook){
      var user = accounts.accounts[i];
      break;
    }
  }
  if(user){
    const hash = crypto.createHash('sha512');
    if(hash.update(request.body.password, 'utf-8').digest('hex') == user.password){
      var error = false;
      var message = "logged in succesfully";
      var token = jwt.sign({"_id": user.id, "permission": user.permission, "login": user.login, "active": user.active}, process.env.TOKEN_SECRET, {expiresIn: 60*60});

      console.log(moment().format("YYYY MM DD hh:mm:ss - ") + 'user ' + user.login + ' have just logged in and recieved token' );

    }else {
      var error = true;
      var message = "bad request";
      var token = null;
    }
  } else {
    var error = true;
    var message = "login or password is incorrect";
    var token = null;
  }

  const loginResponse = new StandardLoginResponse({
    error: error,
    message: message,
    token: token
  });
  response.send(loginResponse);
});
module.exports = router;
