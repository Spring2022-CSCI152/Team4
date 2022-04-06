from cgi import test
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageTk
import cv2
import dlib
import face_recognition


def show_frames():
    face_cascade = cv2.CascadeClassifier('Facial_Recognition\haarcascade_frontalface_default.xml') #Learning database to find faces
    capture = cv2.VideoCapture(0) #starts video stream

    

    while True:
        _, img = capture.read()
        Gchange = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(Gchange, 1.1, 3)
        
        for(x,y,w,h) in faces:
            cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0), 2)
        cv2.imshow('img',img)
        k = cv2.waitKey(30) & 0xff #breaks if esc key is pressed
        if k==27:
            break
        
    capture.release() 

    

Master = Tk()
Master.geometry("750x500")
label = Label(Master, text="Setup Window")
label.pack(pady=10)

btn = Button(Master, text="Open Camera",command=show_frames)
btn.pack(pady=10)


mainloop()
