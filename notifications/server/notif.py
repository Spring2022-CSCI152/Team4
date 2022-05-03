import requests
import json

def sendNotif():
	url = "http://localhost:8080/"

	data = {"business_id": 100, "profile_id": "profile_id"}

	headers = {"Content-type": "application/json"}

	r = requests.post(url, data=json.dumps(data), headers=headers)

	print(r.status_code)

sendNotif()