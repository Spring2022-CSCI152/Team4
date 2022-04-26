import os
from io import BytesIO
import json
import base64
from PIL import Image, ExifTags
from flask import Flask, request, send_file
from db import DBManager, RequestParams
from decouple import config

app = Flask(__name__)

# Database connection, save DB info in .env file
database = DBManager(config('HOST'), config('UNAME'), config('PASSWORD'), config('DBNAME'))

# Saves a base64 rep of an image to local system and corresponding info to db
@app.route("/save_image", methods=['POST'])
def post():
	request_data = request.get_json()
	if 'image' in request_data:
		profile_id, file_name, business_id = get_feilds(request_data)

		path = build_and_get_save_dir(profile_id, file_name, business_id)

		save_image_to_local_system(path, request_data['image'])

		saveToDB(profile_id, path, business_id)

		return 	json.dumps({'result':'succ'}), 200, {'Content-type':'application/json'}

	return json.dumps({'result': 'fail'}), 500,  {'Content-type':'application/json'}

# Builds Directories if they dont exist then returns path to save director
def build_and_get_save_dir(profile_id, file_name, business_id):
		makeDirs(business_id, profile_id)
		return os.path.join('suspects', getDir(business_id, profile_id), file_name)

# Saves an image locally, uses a base64 string rep of an image to build the saved image
def save_image_to_local_system(path, image_b64):
		image = Image.open(BytesIO(base64.b64decode(image_b64)))
		exif = image.getexif()

		for orientation in ExifTags.TAGS.keys():
			if ExifTags.TAGS[orientation] == 'Orientation':
				break
		
		if exif[orientation] == 3:
			image=image.rotate(180, expand=True)
		elif exif[orientation] == 6:
			image=image.rotate(270, expand=True)
		elif exif[orientation] == 8:
			image=image.rotate(90, expand=True)
		
		image.save(path)
		image.close()

# extracts the fields in a request
def get_feilds(request_data):
	return request_data['profile_id'], request_data['file_name'], request_data['business_id']

# saves to the connected DB
def saveToDB(profile_id, path, business_id):
	database.save_entity(RequestParams(profile_id, path, business_id))

# Builds a Path using the specified elements
def getDir(business_id, profile_id):
	return os.path.join(str(business_id), profile_id)

# Builds the dirs if they dont exist
def makeDirs(business_id, profile_id):
	if not os.path.isdir('suspects'):
		os.mkdir('suspects')
	baseDir = os.path.join('suspects', str(business_id))
	if not os.path.isdir(baseDir):
		os.mkdir(baseDir)
	fullDir = os.path.join(baseDir, profile_id)
	if not os.path.isdir(fullDir):
		os.mkdir(fullDir)

# Finds an image by 'profile_id' and 'business_id' then returns it
@app.route("/get_image", methods=["GET"])
def get():
	request_data = request.get_json()

	profile_id = request_data['profile_id']
	business_id = request_data['business_id']
	(profile_id, path, business_id) = database.get_entity_by_ids(RequestParams(profile_id, "", business_id))

	image = Image.open(path)

	return 	send_file(path, as_attachment=True, attachment_filename=image.filename, mimetype='image/'+image.format)


if __name__ == "__main__":

	app.run()
