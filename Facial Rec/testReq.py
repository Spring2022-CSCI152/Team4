import requests
import base64
import json
from PIL import Image, ExifTags

def execute():
	#Saving images in local machine
	url = "http://localhost:5000/save_image"
	with open('C:\\Users\\Maaku\\Desktop\\CSCI152ProjectMain\\Team4\\Facial Rec\\Marc.jpg', 'rb') as f:
		img_bytes = f.read()

	img_b64 = base64.b64encode(img_bytes).decode('utf-8')
	data = {'image': img_b64, 'file_name': 'Marc2.jpg', 'business_id': 110, 'profile_id': "Alpaca"}
	# data = {} #sets names of images 

	# print(json.dumps(data))

	headers = {'Content-type': 'application/json'}
	req = requests.post(url, headers = headers, data = json.dumps(data))

	# print(req.status_code)


	# url2 = "http://localhost:5000/get_image"
	# newData = {'business_id': 100, 'profile_id': "someID"}
	# req = requests.get(url2, headers = headers, data = json.dumps(newData))

	# print(req.json().get('result'))
	


execute()