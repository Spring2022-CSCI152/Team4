const express = require("express")
const app = express()
const port = 4000
const cors = require("cors")

app.use(express.urlencoded({ extended: true }))
app.use(express.json())
app.use(cors())



app.post("/post_registration_form", async (req, res) => {
	let { formData } = req.body
})
app.listen(port, () => {
	console.log(`Listening at http://localhost:${port}`)
})