import time
import serial

port = "/dev/ttyACM0"				## Rotate from ports 0 to 4
##port = "COM11"
baud = 9600
timer = "T" + str(int(time.time()))
state1 = 1
state2 = 0
while(state1):
	try:
		print(timer)
		ser = serial.Serial(port, baud)
		ser.reset_input_buffer()
		state1 = 0
		state2 = 1
	except:
		print("Port not connected")

while(state2):
	message = ser.read()

	print(message)
	if message == "z":		
		ser.write(timer)
		print(timer)
	if message == "x":
		ser.close()
		print("halleluyah")
		state2 = 0
	if message == "c":
		messageTime = ser.readline()
		print(messageTime)
		
