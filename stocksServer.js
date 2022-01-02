const express = require('express')
const app = express()
const port = 8080
const API_KEY = "NHNQ5V86S1CKR5RA"
const request = require('request')


app.get('/', (req, res) => {
    res.send(`Welcome to Maya\'s server!`)
})


app.get(`/:stock`, function (req, res) {
  
  let stockName = req.query.symbol;
  console.log(stockName)
  
  fetchPrice(stockName, (err, price) => {
	  if (err) {
		 return res.status(500).json({err: err.message});
	  }
	  
	  else {
		  return res.json(price)
	  }
  });
});


function fetchPrice(stock, cb) {
	
    let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${stock}&apikey=${API_KEY}`
	
    request.get({
            url: url,
            json: true,
            headers: {
                'User-Agent': 'request'
            }
        },
        (err, result, data) => {
            if (err) {
                console.log('Error:', err);
            } else if (result.statusCode !== 200) {
                console.log('Status:', result.statusCode);
            } else {
                // data is successfully parsed as a JSON object
				console.log(data)
				
                price = data[`Global Quote`][`05. price`]
                console.log(`The price is ${price}`)
				
				return cb(null, {
					symbol: stock,
					price: price
				});
            }
        });
}

app.listen(port, () => {
    console.log(`server up and running on port ${port}`)
})