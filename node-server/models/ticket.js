
var mongoose = require('mongoose');
var bcrypt = require('bcryptjs');


//User Schema
var ticketSchema = mongoose.Schema({
	username: {
		type: String,
		index:true
	},
	adcount: {type: Number} , 
	stcount: {type: Number} , 
	monuments: {
		type: Array,
		default: "[]"
	},
	visited: {type:Array , default: "[]"} , 
	hash: {type: String} , 
	price: {type: Number} , 
	date: {type: Date}
});


var ticket  = module.exports = mongoose.model('tickets' , ticketSchema);

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
	User.findOne(query , callback);
}

module.exports.getUserById = function(id , callback){
	//var query = {username: username};
	User.findById(id, callback);
}

module.exports.comparePassword = function(candidatePassword , hash, callback){
	bcrypt.compare(candidatePassword, hash, function(err, isMatch) {
    if(err) throw err;
    callback(null , isMatch);
   });
}
