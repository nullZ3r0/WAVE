# Lukas Maslianikas
# WAVE Project - Voice_V1 - Python
# 04/02/2023
# # # # # # # # # #

# Imports
import speech_recognition
import pyttsx3

# Variables
r = speech_recognition.Recognizer()

def voice(command):
    engine = pyttsx3.init()
    engine.say(command)
    engine.runAndWait()

while(1):
    try:
        with speech_recognition.Microphone() as source2:
            r.adjust_for_ambient_noise(source2, duration=0.2)
            audio2 = r.listen(source2)
            MyText = r.recognize_vosk(audio2)
            MyText = MyText.lower()


            print("Did you say ", MyText[14:-3])
            
    except speech_recognition.RequestError as e:
        print("Could not request results; {0}".format(e))

    except speech_recognition.UnknownValueError:
        print("unkown error occured")

voice()