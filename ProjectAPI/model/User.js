const mongoose = require('mongoose')

const userSchema = new mongoose.Schema({
  permission: {
    type: Number,
    required: true
  },
  login: {
    type: String,
    required: true
  },
  password: {
    type: String,
    required: true
  },
  active: {
    type: Boolean,
    required: true
  },
  name: {
    type: String,
    required: true
  },
  surname: {
    type: String,
    required: true
  }


})

module.exports = mongoose.model('User', userSchema);
