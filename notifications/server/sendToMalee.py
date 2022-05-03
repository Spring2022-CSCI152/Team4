import requests
import json


def sendMessageToMaleeServer():

    url = "http://localhost:4000"
    data = {"profile_id": "someID", "business_id": 100}

    header = {"Content-type": "application/json", }

    r = requests.post(url, data=json.dumps(data), headers=header)
    print(r)
    print(r.status_code)

sendMessageToMaleeServer()