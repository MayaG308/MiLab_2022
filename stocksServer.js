const express = require('express')
const app = express()
const port = 8080
const API_KEY = "NHNQ5V86S1CKR5RA"
const request = require('request')


app.get('/', (req, res) => {
    res.send(`Welcome to Maya\'s server!`)
})

app.get('/:stockName', (req, res) => {
    const stockName = req.params.stockName
    const url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${stockName}&apikey=${API_KEY}`
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
                const price = data[`Global Quote`][`05. price`]
                console.log(price)
				console.log(data["Global Quote"])
                res.send({
                    price
                });
				return price;
            }
        })
})

app.listen(port, () => {
    console.log(`server up and running on port ${port}`)
})