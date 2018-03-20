var express = require('express');
var router = express.Router();
var user = require('../models/user');
var ticket = require('../models/ticket');
var ObjectID = require('mongodb').ObjectID;
var bcrypt = require('bcryptjs');

/* GET home page. */
router.get('/', function(req, res, next) {
	res.render('index', { title: 'Express' });
});
router.get('/hello' , function(req , res, next) {
	res.send('new hello');
	// var newUser = new user({
	// 				name: "name",
	// 				email: "email",
	// 				password: "password",
	// 				address: "address"
	// 			});
	// newUser.save();
	
});
router.post('/register' , function(req ,res) {
	console.log("Register details:- ");
	//console.log(req);
	console.log(req.body);
	//console.log('done');
	user.findOne({username:req.body.username} , function(err , data) {
		if(!data)
		{
			var newUser = new user({

				username:req.body.username,
				name: req.body.name , 
				password: req.body.password

			});
			user.createUser(newUser , function(err) {
				if(err) {
					console.log('failure');
					res.send('failure');
				}
				else
				{
					console.log('success');
					res.send('success');
				}
			});
		}
		else
		{
			res.send("exist");
		}
	});

});

router.post('/login' , function(req , res){
	console.log('Logging in');
	var username = req.body.username;
	//console.log(req.body);
	user.findOne({username: username} , function(err , data) {
		if(!data)
		{
			console.log('User doesnot exist!!');
			res.send('User doesnot exist!!');
		}
		else
		{
			var hash = data['password'];
			//console.log(hash);
			var password = req.body.password;
			bcrypt.compare(password , hash , function(err , isMatch){
			//	console.log(isMatch);
			if(isMatch)
			{
				console.log('Logged in');
				res.end('success');
			}
			else{
				console.log('failure');
				res.end('failure');
			}

		});
		}

	});

});

router.post('/verify' , function(req , res){
	console.log('inside verify!');
	var ticket_id = req.body.id;
	var monument = req.body.monument;
	console.log(ticket_id , monument);
	ticket_id = ticket_id.slice(1 , ticket_id.length - 1);
	console.log(ticket_id , monument , typeof(ticket_id));
	ticket.findOne({_id: ObjectID(ticket_id)} , function(err , data){
		if(data)
		{
			var t1 = 0 , t2 = 1;
			for(var i = 0 ; i < data.monuments.length ; i++)
			{
				if(data.monuments[i] == monument){
					t1 = 1;
					break;
				}
			}
			for(var i = 0 ; i < data.visited.length ; i++)
			{
				if(data.visited[i] == monument) {

					t2 = 0;
					break;
				}
			}
			var result = {result: 'success' , student_count: data.stcount , adult_count: data.adcount};
			console.log(typeof(JSON.stringify(result)));
			if(t1 & t2)
			{
				data.visited.push(monument);
				data.save(function(err , data1){
					if(err)
					{
						result.result = 'failure';
						console.log('Some error occcured');
						res.send(JSON.stringify(result));
					}
					else
					{
						console.log(data1);
						console.log('success');
						res.send(JSON.stringify(result));
					}
					
				});
				
			}
			else
			{
				result.result = 'failure';
				console.log('failure');
				res.send(JSON.stringify(result));
			}
		}
		else
		{
			console.log('Ticket does not exist');
			res.send(JSON.stringify({result:"failure" , student_count: 0 , adult_count: 0}));
		}
	});

});

router.post('/ticket' , function(req, res){
	
	console.log('Inside ticket booking');
	console.log(req.body);
	var monument_str = req.body.monuments.split("#");
	var booked = monument_str.slice(0 , monument_str.length - 1);
	var newTicket = new ticket({
		username: req.body.username , 
		monuments : booked ,
		adcount: req.body.adcount , 
		stcount: req.body.stcount , 
		price: parseInt(req.body.price) , 
		visited: [] , 
		date: Date()
	});
	console.log(newTicket);

		//return;
		newTicket.save(function(err , data) {
			if(err)
			{
				res.send('some error occurred!');
			}
			else
			{
				console.log('Id sent.');
				res.send(data._id);
			}
		});

	});

module.exports = router;
