import requests
import json


def sendMessageToMaleeServer():

    url = "http://localhost:8081/"
    data = {"profile_id": "Abigail Bowen", "business_id": 18084}

    header = {"Content-type": "application/json"}

    r = requests.post(url, data=json.dumps(data), headers=header)
    print(r)
    print(r.status_code)

sendMessageToMaleeServer()