import requests
import json


def sendMessageToMaleeServer():

<<<<<<< HEAD
    url = "http://localhost:4000"
    data = {"profile_id": "someID", "business_id": 100}
=======
    url = "http://172.24.239.76:4000/get_image/210/someOtherID2"
    data = {"profile_id": "someOtherID2", "business_id": 210}
>>>>>>> c72089e5b573a6c2630811ac8ed4d3d9378f6d6e

    header = {"Content-type": "application/json", }

    r = requests.post(url, data=json.dumps(data), headers=header)
    print(r)
    print(r.status_code)

sendMessageToMaleeServer()