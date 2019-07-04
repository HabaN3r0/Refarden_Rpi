import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import serial
import threading
import time
import glob
import sys

class Firebase_test:
	def __init__(self):
		self.cred = credentials.Certificate("refarden-firebase.json")
		self.default_app = firebase_admin.initialize_app(self.cred, {'databaseURL': 'https://refarden.firebaseio.com/'})
		self.root = db.reference()
		self.values = self.root.get()
		self.user_box = self.root.child('Box System')
		self.user_table = self.root.child('Table System')
		
		self.humidity_val = self.values['Box System']['Humidity']
		self.light_val = self.values['Box System']['Light']
		self.mode_val = self.values['Box System']['Manual_Mode']
		self.pump_val = self.values['Box System']['Pump']
		self.temp_val = self.values['Box System']['Temperature']

		self.serState = True
		self.port, self.baud = self.FindPort()

		while(self.serState):
	
			try:
				self.ser = serial.Serial(self.port, self.baud)
				self.ser.reset_input_buffer()
				self.serState = False
			except:
				print("Port not connected")

		
	def FindPort(self):
		self.ports = glob.glob('/dev/tty[A-Za-z]*')
		self.baud = 9600
		result = []

		for port in self.ports:
			try:
				s = serial.Serial(port)
				s.close()
				result.append(port)
			except (OSError, serial.SerialException):
				pass
		return(result[0], self.baud)

	def UpdateBox(self):
		self.user_box.update({
			'Humidity': humidity_val,
			'Light': light_val,
			'Manual_Mode': mode_val,
			'Pump': pump_val,
			'Temperature': temp_val
			})

	def CheckBox(self):
		self.values = self.root.get()
		self.humidity_val = self.values['Box System']['Humidity']
		self.light_val = self.values['Box System']['Light']
		self.mode_val = self.values['Box System']['Manual_Mode']
		self.pump_val = self.values['Box System']['Pump']
		self.temp_val = self.values['Box System']['Temperature']

	def RecvSensor(self):
		self.ser.write("REFz")
		self.sensor_data = []
		readings_left = True

		while readings_left:
			serial_line = self.ser.readline()
			if serial_line == '':
				readings_left = False
			else:
				self.sensor_data.append(serial_line)
				if len(sensor_data) == 2:
					readings_left = False

		self.humidity_val = sensor_data[0]
		self.temp_val = sensor_data[1]

		self.user_box.update({
			'Humidity': humidity_val,
			'Temperature': temp_val
		})



	def MainEvent(self):

		while(1):
			self.CheckBox()

			



if __name__ == '__main__':
	firebase_test = Firebase_test()