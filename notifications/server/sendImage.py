import requests;
import base64;
import json;
import time;



def request():

	url = "https://spiritfinder5-6n152234f068o0n2.socketxp.com/save_image"

	with open('./notifications/server/image.png', 'rb') as f:
		img_bytes = f.read()

	img_b64 = base64.b64encode(img_bytes).decode('utf-8')
	time.sleep(.25)
	data = {'image': img_b64, 'file_name': 'image.png', 'business_id': 210, 'profile_id': "someOtherID2"}


	headers = {'Content-type': 'application/json'}
	req = requests.post(url, headers = headers, data = json.dumps(data))

	print(req.status_code)





request()