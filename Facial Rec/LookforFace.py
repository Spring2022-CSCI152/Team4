#import imp
import cv2
import numpy as np
import face_recognition
import os

#Notifications
#import time
#from datetime import timedelta
#import json
#import requests
#from db import DBManager, RequestParams
#from decouple import config
#
#database = DBManager(config('HOST'), config('UNAME'), config('PASSWORD'), config('DBNAME'))
#
#
#def sendtoFront(pathS):
#    address = "http://127.0.0.1:5000"
#    (profile_id, pathS, business_id) = database.get_entity_by_file_path(pathS)
#
#    data = {
#        "profile_id": profile_id, 
#        "business_id": business_id
#    }
#
#    headers = {'content-type': 'application/json'}
#    x = requests.post(address, headers = headers, data = json.dumps(data))
#
#detectionQueue = {}
#
#def enqueue(imagePath):
#    if not detectionQueue.get(imagePath) or  time.now() - detectionQueue.get(imagePath) > timedelta(minutes=10):
#        detectionQueue.put(imagePath, time.now())
#        sendtoFront(imagePath)

#encode pictures
# FOR FUTURE REFERENCE, from terminal cd 'C:\\Users\\Maaku\\Desktop\\CSCI152ProjectMain\\Team4\\Facial Rec'
## GLOBALS
path = '\\suspects'
images =  []
SusNames = []
myList = []
encodeListKnown = [] #create init encode list
cap = None
def setup():
    
    myList = getdirs()
    
    for rpt in myList:
        curImage = cv2.imread(rpt)
        images.append(curImage)
        SusNames.append(os.path.split(rpt)[1])
        
    encodeListKnown = findEncoding(images)
        
        
def getdirs():
    currDir = []
    for path2, subdirs, files in os.walk(path):
        for name in files:
            currDir.append(os.path.join(path2,name))
    return currDir



def findEncoding(images): #function to create list of encoded values for each image
    encodeList = []
    for img in images:
        print(os.path.exists(path))
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        encode = face_recognition.face_encodings(img)[0]
        encodeList.append(encode) # List of encoding numbers of each image
    return encodeList


def execue():
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
