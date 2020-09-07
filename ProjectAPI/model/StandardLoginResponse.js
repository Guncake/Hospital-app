const mongoose = require('mongoose')

const standardLoginResponseSchema = new mongoose.Schema({
  error: {
    type: Boolean,
    required: true
  },
  message: {
    type: String,
    required: true
  },
  token: {
    type: String,
    required: true
  }
});

module.exports = mongoose.model('StandardLoginResponse', standardLoginResponseSchema);
