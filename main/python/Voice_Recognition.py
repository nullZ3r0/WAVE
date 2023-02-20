# Lukas Maslianikas
# WAVE Project - Voice V1 - Python
# 19/02/2023
# # # # # # # # # #

# Imports
import speech_recognition # Imports speech recognition for Vosk and Google
import pyttsx3            # Imports support for speech_recognition
import socket             # Imports a way to open sockets on localhost
import time               # Imports time

# Variables
r = speech_recognition.Recognizer()
UDP_IP = "127.0.0.1" # Localhost as 127.0.0.1
UDP_PORT = 5005      # Port as 5005

def voice(command):         # Sets up the voice commands to start recieving input
    engine = pyttsx3.init() # <
    engine.say(command)     # <
    engine.runAndWait()     # <

while(1):
    try:
        with speech_recognition.Microphone() as source2:      # Grabs the microphone and allows it to be used
            r.adjust_for_ambient_noise(source2, duration=0.2) # <
            audio2 = r.listen(source2)                        # <
            MyText = r.recognize_google(audio2)               # <
            MyText = MyText.lower()                           # <

            MESSAGE = b"Voice: "       # Converts voice into pure bytes for
            MESSAGE += MyText.encode() # sending over to reciever

            sock = socket.socket(                    # Sends the message over UD Protocol
                                socket.AF_INET,      # <
                                socket.SOCK_DGRAM    # <
                                )                    # <
            sock.sendto(MESSAGE, (UDP_IP, UDP_PORT)) # <

            MESSAGE = b"" # Resets message
            
    except speech_recognition.RequestError:
        print("Error 0x1:") # No internet connection

    except speech_recognition.UnknownValueError:
        print("Error 0x0") # Language not available / Noise not recognised

voice() # Starts the application