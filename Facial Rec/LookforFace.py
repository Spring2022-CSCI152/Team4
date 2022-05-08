import cv2
import face_recognition
import numpy as np
import os
from datetime import timedelta, datetime
import json
import requests
from db import DBManager, RequestParams
from decouple import config

# Update to Notification Server IP <- should be encapsulated in .env file
SERVER_PATH = 'http://127.0.0.1:5000'

# Encapsulate Facial Rec activities to FaceRec class
class FaceRec:

      # Use instance vars instead of Globals
    def __init__(self,host, username, password, dbname, path):
        self.notif_manager = NotificationManager(host, username, password, dbname)
        self.encoded_images = []
        self.image_list = []
        self.cap = None
        self.path = path
        self.need_update = False
        self.setup()

     # extract certain functions for readability
    def read_and_encode_image(self, image):
         return cv2.cvtColor(cv2.imread(image), cv2.COLOR_BGR2RGB) 

    def setup(self):
        # Set list of all image paths
        print("Connecting To Camera")
        self.cap = cv2.VideoCapture(0)
        print("Setting image dirs")
        self.set_image_dirs()
        print("Encoding Images")
        self.encode_images()

        # transponse image files into ML friendly values
        # encode images and store the encodings
    def encode_images(self):
        self.need_update = False
        self.encoded_images = []
        for img in self.image_list:
            cur_image = self.read_and_encode_image(img)
            for enc in face_recognition.face_encodings(cur_image):
                self.encoded_images.append(enc) 
                  
     # Walks through the file system and stores the paths to all images found
    def set_image_dirs(self):
        # Walk through the subdirectories and find all files
        for path, subdirs, files in os.walk(self.path):
            for name in files:
                file_path = os.path.join(path, name)
                # Save only if not already in list
                if file_path not in self.image_list: # <- doing a check here prevents having to rebuild from scratch later
                    # only save the paths that end with a file
                    self.image_list.append(file_path)
                    self.need_update = True
    
     # rename a function call for understandibility
    def update_image_dirs(self):
        self.set_image_dirs()
        if self.need_update:
            self.encode_images()
        
    def execute_facial_rec(self):

        while True:
            self.update_image_dirs()
            
            success, img = self.cap.read()
            img_to_process = self.resize_and_encode_image(img) 
            
            #Finds faces
            face_curr_frame = face_recognition.face_locations(img_to_process) 
            
            # takes face location, creates encoding for each face in frame
            encodes_curr_frame = face_recognition.face_encodings(img_to_process, face_curr_frame) 

            if len(self.image_list) == 0: #nothing in suspects folder
                    continue
            else:
                for encode_face, face_loc in zip(encodes_curr_frame, face_curr_frame): 
                    matches = face_recognition.compare_faces(self.encoded_images, encode_face) 
                    face_dis = face_recognition.face_distance(self.encoded_images, encode_face)
                   

                    #In feed Creates box and places name
                    if True in matches:
                        match_index = matches.index(True)
                        self.notif_manager.enqueue_match(self.image_list[match_index])
    
                        """
                        y1,x2,y2,x1 = faceLoc
                        y1,x2,y2,x1 = y1*4,x2*4,y2*4,x1*4
                        cv2.rectangle(img2,(x1,y1),(x2,y2),(0,255,0),2)
                        cv2.rectangle(img2,(x1,y2-35),(x2,y2),(0,255,0),cv2.FILLED)
                        cv2.putText(img2,name,(x1+6,y2-6),cv2.FONT_HERSHEY_COMPLEX,1,(255,255,255),2)
                        """
            
    # Extract and inline function coall, notice cv2.resize() is now a parameter to cv2.cvtColor()
    def resize_and_encode_image(self, image):
        return cv2.cvtColor(cv2.resize(image,(0,0),None,0.25,0.25), cv2.COLOR_BGR2RGB) 
            
# Encapulate Notification Sending Activities to NotificationManager
class NotificationManager:
      
    # Using instance vars, and using built in dict() constructor
    def __init__(self, host, username, password, dbname):
        self.database = DBManager(host, username, password, dbname)
        self.queue = dict()
        self.detectionQueue = {}

    def enqueue_match(self, file_path):
        #If the image has not been seen OR if its been at least 10 minutes
        if not self.detectionQueue.get(file_path) or datetime.now() - self.detectionQueue.get(file_path) > timedelta(minutes=10):
            # enqueue with a timestamp
            self.detectionQueue[file_path] = datetime.now()
            self.send_notif(file_path)
    
    def send_notif(self, file_path):
            
        global SERVER_PATH
      
        try:
            (profile_id, file_path, business_id) = self.database.get_entity_by_file_path(file_path)

            data = {
                "profile_id": profile_id, 
                "business_id": business_id
            }

            headers = {'content-type': 'application/json'}
            
            x = requests.post(SERVER_PATH, headers = headers, data = json.dumps(data))
            
        except TypeError :
            
            print("Database entry Not Found")

fr = FaceRec(config('HOST'), config('UNAME'), config('PASSWORD'), config('DBNAME'), 'suspects')
fr.execute_facial_rec()
