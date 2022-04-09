import cv2
import numpy as np
import face_recognition
import os

#encode pictures
path = 'Facial_Recognition\Suspects'
images =  []

SusNames = []
myList = os.listdir(path)
print(myList)

for rpt in myList:
    curImage = cv2.imread(f'{path}/{rpt}')
    images.append(curImage)
    SusNames.append(os.path.split(rpt)[1])
#print(SusNames) # Images in array Test: ['Julie.jpg', 'Malee.jpg', 'Marc.jpg', 'Ryan.png']

def findEncoding(images): #function to create list of encoded values for each image
    encodeList = []
    for img in images:
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        encode = face_recognition.face_encodings(img)[0]
        encodeList.append(encode) # List of encoding numbers of each image
    return encodeList

encodeListKnown = findEncoding(images)
print('Total Encode images: ', len(encodeListKnown), " / ", len(myList))

cap = cv2.VideoCapture(0) #running image capture

while True:
    success, img2 = cap.read()
    imgSm = cv2.resize(img2,(0,0),None,0.25,0.25)
    imgSm = cv2.cvtColor(imgSm,cv2.COLOR_BGR2RGB)

    faceCurrFrame = face_recognition.face_locations(imgSm) #Finds faces
    encodesCurrFrame = face_recognition.face_encodings(imgSm, faceCurrFrame) # takes face location, creates encoding for each face in frame

    for encodeFace,faceLoc in zip(encodesCurrFrame, faceCurrFrame): #Every frame faces are checked through folder
        matches = face_recognition.compare_faces(encodeListKnown,encodeFace) 
        faceDis = face_recognition.face_distance(encodeListKnown,encodeFace)
        print(faceDis)
        matchIndex = np.argmin(faceDis)

        if matches[matchIndex]: #In feed Creates box and places name
            name = SusNames[matchIndex].upper()
            print(name)
            y1,x2,y2,x1 = faceLoc
            y1,x2,y2,x1 = y1*4,x2*4,y2*4,x1*4
            cv2.rectangle(img2,(x1,y1),(x2,y2),(0,255,0),2)
            cv2.rectangle(img2,(x1,y2-35),(x2,y2),(0,255,0),cv2.FILLED)
            cv2.putText(img2,name,(x1+6,y2-6),cv2.FONT_HERSHEY_COMPLEX,1,(255,255,255),2)
    
    cv2.imshow('Webcam', img2)
    cv2.waitKey(1)
