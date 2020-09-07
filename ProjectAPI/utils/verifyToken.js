const jwt = require('jsonwebtoken');

module.exports = {
  verifyUser: function (request, response, next) {
    const token = request.header('auth-token');
    if(!token){
      console.log("1 ");
      return response.status(401).send('Acces denied');
    }
    try {
      const verified = jwt.verify(token, process.env.TOKEN_SECRET);
      request.user = verified;
      next();
    } catch (err) {
      console.log("2 " + err + "  ");
      response.status(401).send('Invalid or expired token');
    }
  },
  verifyAdmin: function (request, response, next) {
    const token = request.header('auth-token');
    if(!token){
      return response.status(401).send('Acces denied');
    }
    try {
      const verified = jwt.verify(token, process.env.TOKEN_SECRET);
      if(verified.permission == 1){
        request.user = verified;
        next();
      } else {
        return response.status(401).send('Acces denied');
      }

    } catch (err) {
      response.status(401).send('Invalid or expired token');
    }
  },
};
