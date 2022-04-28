const express = require("express")
const app = express()
const port = 4000
const cors = require("cors")
const http = require('http');

app.use(express.urlencoded({ extended: false }))
app.use(express.json())
app.use(cors())

app.get('/post_registration_form', (req,res)=>{
    res.send({msg: 'postman get test', user: {}})
})

app.post("/post_registration_form", async (req, res) => {
    console.log('body print here: ', req.body);
    res.status(200).send('create admin postman test')
    
}); 

app.listen(port, () => {
    console.log(`Listening at http://localhost:${port}`)})
