const express = require("express")
const app = express()
const port = 4000
const cors = require("cors")

app.all('/', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    next()
  });

app.use(express.urlencoded({ extended: true }))
app.use(express.json())
app.use(cors())



app.post("/post_registration_form", async (req, res) => {
	res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
	let { formData } = req.body
	res.json(req.body);
	res.redirect('http://172.24.158.171:8080/api/v1/accounts/register_business')
})
app.listen(port, () => {
	console.log(`Listening at http://localhost:${port}`)
})