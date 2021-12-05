const express = require('express')
const todo = express()
const fs = require('fs')

const tasks = JSON.parse(fs.readFileSync('tasks.json', 'utf8'))

// Welcoming note
todo.get('/', function (req, res) {
  res.send('Welcome to Maya\'s Todo list app!');
});



//Printing all tasks
todo.get("/tasks", function(req, res) {
    res.send(tasks);
})

//adding new task
todo.get("/tasks/new", (req, res) => {
    
	//creating new element from URL
	var newTask ={
		"id": req.query.id,
		"task": req.query.task
    }; 
    
    tasks.tasksList.push(newTask)
	const jsonData = JSON.stringify(tasks, null, 2);

    fs.writeFile('tasks.json', jsonData, (err) => {
        if (err){
			res.send(err); 
		} else {
			res.send(`Added task ${newTask.id} successfully`);
		}
    })

})

//Removing task
todo.get("/tasks/remove", (req, res) => {
    
    let idRemove = req.query.id

    tasks.tasksList = tasks.tasksList.filter(task => task.id != idRemove)
	
	const jsonData2 = JSON.stringify(tasks, null, 2);
    fs.writeFile('tasks.json', jsonData2, (err) => {
        if (err){
			res.send(err)
		} else {
			 res.send(`Removed task ${idRemove} successfully`)
		}
    })

})

//Server invokation
todo.listen(8080, function () {
  console.log('Running on port 8080!')
});
