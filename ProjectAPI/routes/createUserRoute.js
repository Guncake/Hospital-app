const router = require('express').Router();
const User = require('../model/User');
const verify = require('../utils/verifyToken');
const fs = require('fs');
const moment = require('moment');

router.post('/register', verify.verifyAdmin, (request, response, next) => {
  //reading actual database
  var accountsbytes = fs.readFileSync('../database/accounts.json');
  var accounts = JSON.parse(accountsbytes);

  const user = new User({
    permission: request.body.permission,
    login: request.body.login,
    password: request.body.password,
    active: request.body.active,
    name: request.body.name,
    surname: request.body.surname
  });

  try {
    //saving user to database
    accounts.accounts[accounts.accounts.length] = user;
    var data = JSON.stringify(accounts, null, 2);
    fs.writeFile('../database/accounts.json', data, (err) => {
      if(err) {
        console.log('An error occured: ' + err);
        response.status(400).send('cant create account');

      }
      else {
        console.log(moment().format("YYYY MM DD hh:mm:ss - ") + 'user account: '+ user.login + ' created by: ' + request.user.login);
        response.status(200).send('OK');

      }
    });
  } catch (err) {
    console.log('An error occured: ' + err);
    response.status(400).send('something went wrong');
  }
});
module.exports = router;
