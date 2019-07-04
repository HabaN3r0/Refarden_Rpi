import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import serial
import threading
import time

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

		self.top = Tkinter.Tk()
		
	def update_box(self):
		self.user_box.update({
			'Humidity': humidity_val,
			'Light': light_val,
			'Manual_Mode': mode_val,
			'Pump': pump_val,
			'Temperature': temp_val
			})

	def check_box(self):
		self.values = self.root.get()
		self.humidity_val = self.values['Box System']['Humidity']
		self.light_val = self.values['Box System']['Light']
		self.mode_val = self.values['Box System']['Manual_Mode']
		self.pump_val = self.values['Box System']['Pump']
		self.temp_val = self.values['Box System']['Temperature']

	def MainEvent(self):

		pass


if __name__ == '__main__':
	firebase_test = Firebase_test()