console.log('server is starting');
//used packages
const express = require('express');
const fs = require('fs');
const crypto = require('crypto');
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
const moment = require('moment');
const StandardLoginResponse = require('./model/StandardLoginResponse');

//
dotenv.config();
const app = express();
app.use(express.static('website'));
app.use(express.json());

//DATABASE//
//Reading accounts file
var accountsbytes = fs.readFileSync('./database/accounts.json');
var accounts = JSON.parse(accountsbytes);

var patientsbytes = fs.readFileSync('./database/patients.json');
var patients = JSON.parse(patientsbytes);
//refreshing database every 5 minutes
setInterval(function() {
	console.log(moment().format("YYYY MM DD hh:mm:ss - ") + 'refreshing database');

	accountsbytes = fs.readFileSync('./database/accounts.json');
	accounts = JSON.parse(accountsbytes);

	patientsbytes = fs.readFileSync('./database/patients.json');
	patients = JSON.parse(patientsbytes);
}, 1000*60*5);

//loading Routes
const createUserRoute = require('./routes/createUserRoute');
const loginUserRoute = require('./routes/loginUserRoute');
const patientRoute = require('./routes/patientRoute');

//Routes
app.use('/admin', createUserRoute);
app.use('/user',(request, response, next) => {request.accounts = accounts; next();}, loginUserRoute);
app.use('/patient',(request, response, next) => {request.patients = patients; next();}, patientRoute);

//set listening
var server = app.listen(3001, process.env.ADDRESS, () => {console.log(moment().format("YYYY MM DD hh:mm:ss - ") + 'listening...')});
