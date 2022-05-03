import requests
import json


def sendMessageToMaleeServer():

    url = "http://localhost:8080/"
    data = {"profile_id": "someOtherID2", "business_id": 210}

    header = {"Content-type": "application/json", }

    r = requests.post(url, data=json.dumps(data), headers=header)
    print(r)
    print(r.status_code)

sendMessageToMaleeServer()