#import imp
import cv2
import numpy as np
import face_recognition
import os

#Notifications
import time
from datetime import timedelta, datetime
import json
import requests
from db import DBManager, RequestParams
from decouple import config

database = DBManager(config('HOST'), config('UNAME'), config('PASSWORD'), config('DBNAME'))


def sendtoFront(pathS):
   address = "http://127.0.0.1:5000"
   (profile_id, pathS, business_id) = database.get_entity_by_file_path(pathS)

   data = {
       "profile_id": profile_id, 
       "business_id": business_id
   }

   headers = {'content-type': 'application/json'}
   x = requests.post(address, headers = headers, data = json.dumps(data))

detectionQueue = {}

def enqueue(imagePath):
   if not detectionQueue.get(imagePath) or  datetime.now() - detectionQueue.get(imagePath) > timedelta(minutes=10):
       detectionQueue[imagePath] = datetime.now()
       sendtoFront(imagePath)

#encode pictures
# FOR FUTURE REFERENCE, from terminal cd 'C:\\Users\\Maaku\\Desktop\\CSCI152ProjectMain\\Team4\\Facial Rec'
# ONCE IN CORRECT directory in terminal, then type python LookForFace.py <- this should run correctly
## GLOBALS
path = 'suspects'
SusNames = []
myList = []
encodeListKnown = [] #create init encode list
cap = None
def setup():
    
    global myList 
    myList = getdirs() 
    images = []
    for rpt in myList:
        curImage = cv2.imread(rpt)
        images.append(curImage)
        SusNames.append(os.path.split(rpt)[1])
    global encodeListKnown 
    encodeListKnown = findEncoding(images)
        
        
def getdirs():
    currDir = []
    for path2, subdirs, files in os.walk(path):
        for name in files:
            currDir.append(os.path.join(path2,name))
    return currDir



def findEncoding(imagesList): #function to create list of encoded values for each image
    encodeList = []
    for img in imagesList:
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        encode = face_recognition.face_encodings(img)[0]
        encodeList.append(encode) # List of encoding numbers of each image
    return encodeList


def execute():
    global myList
    global encodeListKnown
    while True:
        if  not myList == getdirs(): #update encode list
            setup()

        success, img2 = cap.read()
        imgSm = cv2.resize(img2,(0,0),None,0.25,0.25)
        imgSm = cv2.cvtColor(imgSm,cv2.COLOR_BGR2RGB)

        faceCurrFrame = face_recognition.face_locations(imgSm) #Finds faces
        encodesCurrFrame = face_recognition.face_encodings(imgSm, faceCurrFrame) # takes face location, creates encoding for each face in frame


        for encodeFace,faceLoc in zip(encodesCurrFrame, faceCurrFrame): #Every frame faces are checked through folder
            matches = face_recognition.compare_faces(encodeListKnown,encodeFace) 
            faceDis = face_recognition.face_distance(encodeListKnown,encodeFace)
            print(faceDis)
            matchIndex = np.argmin(faceDis) #get min element from array

            if matches[matchIndex]: #In feed Creates box and places name

                #name = SusNames[matchIndex].upper()
                #print(name) # Where to send notification
                print(myList[matchIndex])
                #enqueue(path)

                """
                y1,x2,y2,x1 = faceLoc
                y1,x2,y2,x1 = y1*4,x2*4,y2*4,x1*4
                cv2.rectangle(img2,(x1,y1),(x2,y2),(0,255,0),2)
                cv2.rectangle(img2,(x1,y2-35),(x2,y2),(0,255,0),cv2.FILLED)
                cv2.putText(img2,name,(x1+6,y2-6),cv2.FONT_HERSHEY_COMPLEX,1,(255,255,255),2)
                """

        #cv2.imshow('Webcam', img2)
        #cv2.waitKey(1)

setup()
print('Total Encode images: ', len(encodeListKnown), " / ", len(myList))
cap = cv2.VideoCapture(0) #running image capture
execute()



################################################################################################################################################################
#                                         CODE CLEANUP V2: Suggestions on Code Cleanliness and Maintainability 
################################################################################################################################################################
# import cv2
# import numpy as np
# import os
# from datetime import timedelta, datetime
# import json
# import requests
# from db import DBManager, RequestParams
# from decouple import config

# SERVER_PATH = 'http://127.0.0.1:5000'

# Encapsulate Facial Rec activities to FaceRec class
# class FaceRec:

      # Use instance vars instead of Globals
#     def __init__(self,host, username, password, dbname, path):
#         self.notif_manager = NotificationManager(host, username, password, dbname)
#         self.encoded_images = []
#         self.image_list = []
#         self.cap = None
#         self.path = path
#         self.setup()

#     def setup(self):
#         # Set list of all image paths
#         self.cap = cv2.VideoCapture(0)
#         self.set_image_dirs()
        
#         # transponse image files into ML friendly values
#         # encode images and store the encodings
#         for img in self.image_list:
#             cur_image = read_and_encode_image(img)
#             self.encoded_images.append(face_recognition.face_encodings(cur_image)[0])
            
      # extract certain functions for readability
#     def read_and_encode_image(self, image):
#         return cv2.cvtColor(cv2.imread(image), cv2.COLOR_BGR2RGB)
        
        
#     # Walks through the file system and stores the paths to all images found
#     def set_image_dirs(self):
#         # Walk through the subdirectories and find all files
#         for path, subdirs, files in os.walk(self.path):
#             for name in files:
#                 file_path = os.path.join(path, name)
#                 # Save only if not already in list
#                 if not path in self.image_list: # <- doing a check here prevents having to rebuild from scratch later
#                     # only save the paths that end with a file
#                     self.image_list.append(file_path)
    
      # rename a function call for understandibility
#     def update_image_dirs(self):
#         self.set_image_dirs();
        
#     def execute_facial_rec(self):
#         # Keep Processing
#         while True:
#             self.update_image_dirs()
            
#             success, img = cap.read()
#             img_to_process = self.resize_and_encode_image(img) # <- inline encodings with extracted function
    
#             #Finds faces
#             face_curr_frame = face_recognition.face_locations(img_to_process) 
            
#             # takes face location, creates encoding for each face in frame
#             encodes_curr_frame = face_recognition.face_encodings(img_to_process, face_curr_frame) 
    
#             #Every frame faces are checked through folder
#             for encode_face, face_loc in zip(encodes_curr_frame, face_curr_frame): 
#                 matches = face_recognition.compare_faces(self.encoded_images, encode_face) 
#                 face_dis = face_recognition.face_distance(self.encoded_images, encode_face)
#                 match_index = np.argmin(face_dis) #get min element from array
    
#                 #In feed Creates box and places name
#                 if matches[match_index]: 
    
#                     #name = SusNames[matchIndex].upper()
#                     #print(name) # Where to send notification
#                     print(myList[matchIndex])
#                     # On Match -> enqueue to match to send notification
#                     self.notif_manger.enqueue_match(self.image_list[match_index], self.path)
    
#                     """
#                     y1,x2,y2,x1 = faceLoc
#                     y1,x2,y2,x1 = y1*4,x2*4,y2*4,x1*4
#                     cv2.rectangle(img2,(x1,y1),(x2,y2),(0,255,0),2)
#                     cv2.rectangle(img2,(x1,y2-35),(x2,y2),(0,255,0),cv2.FILLED)
#                     cv2.putText(img2,name,(x1+6,y2-6),cv2.FONT_HERSHEY_COMPLEX,1,(255,255,255),2)
#                     """
    
#             #cv2.imshow('Webcam', img2)
#             #cv2.waitKey(1)
            
      # Extract and inline function coall, notice cv2.resize() is now a parameter to cv2.cvtColor()
#     def resize_and_encode_image(self, image):
#         return cv2.cvtColor(cv2.resize(image,(0,0),None,0.25,0.25), cv2.COLOR_BGR2RGB) 
            
# Encapulate Notification Sending Activities to NotificationManager
# class NotificationManager:
      
      # Using instance vars, and using built in dict() constructor
#     def __init__(self, host, username, password, dbname):
#         self.database = DBManger(host, username, password, dbname)
#         self.queue = dict()

#     def enqueue_match(self, file_path, root):
#         file_path = file_path.replace(root + '/', '')
#         #If the image has not been seen OR if its been at least 10 minutes
#         if not detectionQueue.get(file_path) or datetime.now() - detectionQueue.get(imagePath) > timedelta(minutes=10):
#             # enqueue with a timestamp
#             detectionQueue[file_path] = datetime.now()
#             self.send_notif(file_path)
    
#     def send_notif(self, file_path):
#         global SERVER_PATH
        
#         (profile_id, file_path, business_id) = self.database.get_entity_by_file_path(file_path)

#         data = {
#             "profile_id": profile_id, 
#             "business_id": business_id
#         }

#         headers = {'content-type': 'application/json'}
#         x = requests.post(SERVER_PATH, headers = headers, data = json.dumps(data))
        
# fr = FaceRec(config('HOST'), config('UNAME'), config('PASSWORD'), config('DBNAME'), 'suspects')
# fr.execute_facial_rec()
