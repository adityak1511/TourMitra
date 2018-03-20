bcrypt = require('bcryptjs');
password = 'admin' ; 
bcrypt.genSalt(10 , function(err, salt)
	{
		bcrypt.hash(password, salt, function(err,hash)
		{
			console.log(hash);
			bcrypt.compare("amin" , hash , function(err , data) {
				console.log(data);
			});
		});
	});
