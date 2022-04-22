import mysql.connector
from mysql.connector import Error
from decouple import config

"""
A reusable Database interface for the frmwImages database. Provide
the host address, username, password, and database name to instantiate the object

If the required table does not exists, this will create the needed table on instantiation
"""
class DBManager:
	create_images_table = """
	CREATE TABLE IF NOT EXISTS images (
	profile_id VARCHAR(255) NOT NULL,
	path VARCHAR(255) NOT NULL,
	business_id INTEGER NOT NULL,
	PRIMARY KEY (profile_id, business_id)
	);"""

	def __init__(self, host_name, user_name, user_password, db_name):
		self.connection = self.create_connection(host_name, user_name, user_password, db_name)
		self.execute_query(self.connection, DBManager.create_images_table)

	def create_connection(self, host_name, user_name, user_password, db_name):
		connection = None
		try:
			connection = mysql.connector.connect(
				host=host_name,
				user=user_name,
				password=user_password,
				database=db_name)
			print("Connection to MySQL DB successful!")
		except Error as e:
			print(f"The error '{e}' occured")

		return connection

	def execute_query(self, connection, query):
		cursor = connection.cursor()
		try:
			cursor.execute(query)
			connection.commit()
			print("Query executed successfully")
		except Error as e:
			print(f"The error '{e}' occured")

	def execute_read_query(self, connection, query):
		cursor = connection.cursor()
		result = None
		try:
			cursor.execute(query)
			result = cursor.fetchone()
			return result
		except Error as e:
			print(f"The error '{e}' occured")

	def save_entity(self, params):
		query = self.create_image_insertion_query(params)
		self.execute_query(self.connection, query)

	def get_entity_by_ids(self, params):
		query = self.create_image_selection_query_by_profile_id_and_business_id(params)
		ret = self.execute_read_query(self.connection, query)
		return ret

	def get_entity_by_file_path(self, params):
		query = self.create_image_selection_query_by_file_path(params)
		ret = self.execute_read_query(self.connection, query)

	def create_image_insertion_query(self, params):
		path = params.path.replace('\\','\\\\')
		path = '\'' + path + '\''
		profile_id = '\'' + params.profile_id + '\''
		line = """INSERT IGNORE INTO frmwimages.images 
				(profile_id, path, business_id) 
			VALUES 
				(%s, %s, %d);""" % (profile_id, path, params.business_id) 
		print(line)
		return line
	
	def create_image_selection_query_by_profile_id_and_business_id(self, params):
		profile_id = '\'' + params.profile_id + '\''
		return """SELECT * FROM images WHERE profile_id = {0} AND business_id = {1};""".format(profile_id, params.business_id)

	def create_image_selection_query_by_file_path(self, params):
		path = params.path.replace('\\','\\\\')
		path = '\'' + path + '\''
		return """SELECT * FROM images WHERE profile_id = {0};""".format(path)

class RequestParams:

	def __init__(self, profile_id, path, business_id):
		self.profile_id = profile_id
		self.path = path
		self.business_id = business_id
