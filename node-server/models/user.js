
var mongoose = require('mongoose');
var bcrypt = require('bcryptjs');


//User Schema
var UserSchema = mongoose.Schema({
	username: {
		type: String,
		index:true
	},
	password: {
		type: String
	},
	name: {
		type: String
	},
	address: {
		type: String
	},
    resetPasswordToken: String,
    resetPasswordExpires: Date
});


var newUser  = module.exports = mongoose.model('users' , UserSchema);

//console.log(User);

module.exports.createUser = function(newUser , callback)
{
	bcrypt.genSalt(10 , function(err, salt)
	{
		bcrypt.hash(newUser.password, salt, function(err,hash)
		{
			newUser.password = hash;
			newUser.save(callback);
		});
	});
}
module.exports.generate_hash = function(password , callback)
{
	bcrypt.genSalt(10 , function(err, salt)
	{
		bcrypt.hash(password, salt, function(err,hash)
		{
			callback(err , hash);
		});
	});	
}

module.exports.removeUser = function(username, callback){
	console.log("Remove "+username);
	User.remove({username:username} , callback);
}

module.exports.saveUser = function(newUser , callback)
{
	bcrypt.genSalt(10 , function(err, salt)
	{
		bcrypt.hash(newUser.password, salt, function(err,hash)
		{
			newUser.password = hash;
			newUser.save(callback);
		});
	});
}

module.exports.getUserByUsername = function(username , callback){
	var query = {username: username};
	newUser.findOne(query , callback);
}

module.exports.getUserById = function(id , callback){
	//var query = {username: username};
	newUser.findById(id, callback);
}

module.exports.comparePassword = function(candidatePassword , hash, callback){
	bcrypt.compare(candidatePassword, hash, function(err, isMatch) {
    if(err) throw err;
    callback(null , isMatch);
   });
}
