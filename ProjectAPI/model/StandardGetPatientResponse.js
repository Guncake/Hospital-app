const mongoose = require('mongoose')

const standardGetPatientResponseSchema = new mongoose.Schema({
  error: {
    type: Boolean,
    required: true
  },
  message: {
    type: String,
    required: true
  },
  patientData: {
    type: Object,
    required: true
  }
});

module.exports = mongoose.model('standardGetPatientResponse', standardGetPatientResponseSchema);
