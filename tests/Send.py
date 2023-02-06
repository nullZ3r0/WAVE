# Lukas Maslianikas
# WAVE Project - Send - Python
# 05/02/2023
# # # # # # # # # #

import socket
import time

UDP_IP = "127.0.0.1"
UDP_PORT = 5005
MESSAGE = input("Message? ").encode()

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))