# Lukas Maslianikas
# WAVE Project - gesture_helper - Python
# 04/02/2023
# # # # # # # # # #

# Imports
import time

# Variables


# Counts up to a 100 with one second delay between each count
def Counting():
    x = 0
    while True:
        x += 1
        print("From Python: " + str(x))
        time.sleep(1)

Counting()