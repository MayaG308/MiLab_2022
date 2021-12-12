const express = require('express')
const app = express()
const fs = require('fs')
const path = require('path')


// Welcoming note
app.get('/', function (req, res) {
  res.send(`Welcome to Maya\'s file reader app!`);
});

//not complete path
app.get('/files', function (req, res) {
  res.send(`please re-enter the name of the requested file`);
});

app.get('/files/:picname', (req,res) => {
	//taking the pic from the URL
	const openpic = req.params.picname
	
	//creating path to file
	const picPath = path.join(__dirname, `/files/${openpic}.png`)
	var stat = fs.statSync(picPath)
	
	const readStream = fs.createReadStream(picPath)
	
	readStream.pipe(res)
	
});
	
	

//Server invokation
app.listen(8080, function () {
  console.log('Running on port 8080!')
});
