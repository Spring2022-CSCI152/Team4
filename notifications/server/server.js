const http = require('http');
const hostname = '0.0.0.0';
const port = 8080;

const server = http.createServer((req, res) => {
  if (req.method === "GET") {
    res.writeHead(200, { "Content-Type": "text/html" });
    fs.createReadStream("./public/form.html", "UTF-8").pipe(res);
} else if (req.method === "POST") {
   var body = "";
    req.on("data", function (chunk) {
      body += chunk;
    });
    req.on("end", function (chunk) {
      console.log(JSON.parse(body))
    });
    res.statusCode = 200;
    res.setHeader('Content-type', 'application/json');
    res.end('{"Get": "fucked"}');
}





});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});