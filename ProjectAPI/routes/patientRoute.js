const router = require('express').Router();
const verify = require('../utils/verifyToken');
const fs = require('fs');
const moment = require('moment');
const StandardGetPatientResponse = require('../model/StandardGetPatientResponse');

router.post('/getpatient', verify.verifyUser, (request, response, next) => {

  var patients = request.patients;
  var patientToLook = request.body.patientID;
  console.log(moment().format("YYYY MM DD hh:mm:ss - ") + "data of patient: " + patientToLook + " Requested");

  for(i = 0; i<patients.patients.length; i++){
    if(patients.patients[i].id == patientToLook){
      var patient = patients.patients[i];
      break;
    }
  }

  if(patient){
    var error = false;
    var message = "patient data downloaded";
    var patientData = patient;
    console.log(moment().format("YYYY MM DD hh:mm:ss - ") + patientToLook + " data has been requested by: " + request.user.login);
  }else {
    var error = true;
    var message = "patient not found";
    var patientData = null;
  }


  const patientResponse = new StandardGetPatientResponse({
    error: error,
    message: message,
    patientData: patientData
  });
  response.send(patientResponse);
});
module.exports = router;
