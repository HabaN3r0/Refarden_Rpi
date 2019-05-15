import datetime
import sys
import glob
import serial
import time
import Tkinter
from Tkinter import *

class DripTest:

	def __init__(self):
		self.state1 = True
		self.stateSerial = True
		self.stateLight = 0
		self.floodInterval = 4
		self.stateLight1 = 0
		self.floodInterval1 = 6
		self.lightOnTime = "08:00:00"
		self.lightOffTime = "02:00:00"
		
		self.lightOnTime1 = "08:00:00"
		self.lightOffTime1 = "22:00:00"
		self.ser = None
		self.top =None
		self.lightOffHour = None
		self.lightOffMinute = None
		self.lightOnHour = None
		self.lightOnMinute = None
		self.lightOffHour1 = None
		self.lightOffMinute1 = None
		self.lightOnHour1 = None
		self.lightOnMinute1 = None
		self.tempInfo = None
		

		self.lastFlood = datetime.datetime.now().hour
		self.lastFlood1 = datetime.datetime.now().hour
		
		self.port, self.baud = self.FindPort()

		while(self.state1):
	
			try:
				self.ser = serial.Serial(self.port, self.baud)
				self.ser.reset_input_buffer()
				self.state1 = False
			except:
				print("Port not connected")


		self.top = Tkinter.Tk()

		self.labelTableSystem = Label(self.top, fg = "green", text = "Table System", width = '50')	
		self.labelLightOn = Label(self.top, fg = "green", text = "Light On Time (hh:mm)")
		self.labelLightOff = Label(self.top, fg = "green", text = "Light Off Time (hh:mm)")
		self.labelPumpInterval = Label(self.top, fg = "green", text = "Set Pump Interval (hours)")
		self.labelBoxSystem = Label(self.top, fg = "green", text = "Box System", width = '50')	
		self.labelLightOn1 = Label(self.top, fg = "green", text = "Light On Time (hh:mm)")
		self.labelLightOff1 = Label(self.top, fg = "green", text = "Light Off Time (hh:mm)")
		self.labelPumpInterval1 = Label(self.top, fg = "green", text = "Set Pump Interval (hours)")
		self.labelTemp = Label(self.top, fg = "green", text = str(self.tempInfo))
		
		self.lightOnEntry = Entry(self.top, justify='center')
		self.lightOffEntry = Entry(self.top, justify='center')
		self.pumpIntervalEntry = Entry(self.top, justify='center')
		self.lightOnEntry1 = Entry(self.top, justify='center')
		self.lightOffEntry1 = Entry(self.top, justify='center')
		self.pumpIntervalEntry1 = Entry(self.top, justify='center')

		self.lightOnBtn = Tkinter.Button(self.top, text = "Set", command = self.SetLightOnTime)
		self.lightOffBtn = Tkinter.Button(self.top, text = "Set", command = self.SetLightOffTime)
		self.pumpIntervalBtn = Tkinter.Button(self.top, text = "Set", command = self.SetPumpInterval)
		self.lightOnBtn1 = Tkinter.Button(self.top, text = "Set", command = self.SetLightOnTime1)
		self.lightOffBtn1 = Tkinter.Button(self.top, text = "Set", command = self.SetLightOffTime1)
		self.pumpIntervalBtn1 = Tkinter.Button(self.top, text = "Set", command = self.SetPumpInterval1)
		
		self.justPumpBtn = Tkinter.Button(self.top, text = "Table Pump", command = self.Flood)
		self.justPumpBtn1 = Tkinter.Button(self.top, text = "Box Pump", command = self.Flood1)
		

		self.labelTableSystem.pack()
		self.labelLightOn.pack()
		self.lightOnEntry.pack()
		self.lightOnBtn.pack()
		self.labelLightOff.pack()
		self.lightOffEntry.pack()
		self.lightOffBtn.pack()
		self.labelPumpInterval.pack()
		self.pumpIntervalEntry.pack()
		self.pumpIntervalBtn.pack()
		self.labelBoxSystem.pack()
		self.labelLightOn1.pack()
		self.lightOnEntry1.pack()
		self.lightOnBtn1.pack()
		self.labelLightOff1.pack()
		self.lightOffEntry1.pack()
		self.lightOffBtn1.pack()
		self.labelPumpInterval1.pack()
		self.pumpIntervalEntry1.pack()
		self.pumpIntervalBtn1.pack()
		self.labelTemp.pack()
		self.justPumpBtn.pack()
		self.justPumpBtn1.pack()

		self.top.title("Drip Controls")
		self.top.geometry("500x500")
		self.top.after(100,self.MainEvent)
		self.top.mainloop()


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
	
	
	def Flood(self):
		time.sleep(1)
		self.ser.write("REFa")
		time.sleep(5)
		for x in range(5):
			self.ser.write("REFb")
			time.sleep(2)
			self.ser.write("REFa")
			time.sleep(2)
		self.ser.write("REFb")
		time.sleep(2)
		self.lastFlood = datetime.datetime.now().hour
	
	def Flood1(self):
		time.sleep(1)
		self.ser.write("REFe")
		time.sleep(5)
		for x in range(5):
			self.ser.write("REFf")
			time.sleep(2)
			self.ser.write("REFe")
			time.sleep(2)
		self.ser.write("REFf")
		time.sleep(2)
		self.lastFlood1 = datetime.datetime.now().hour

	def SetLightOnTime(self):
		print(self.lightOffEntry.get())
		##val = sightOnEntry.get()
		##print(val)
		##lst = val.split().strip()
		self.lightOnTime = self.lightOnEntry.get()
	
	def SetLightOffTime(self):
		print(self.lightOffEntry.get())
		self.lightOffTime = self.lightOffEntry.get()

	def SetPumpInterval(self):
		print(self.pumpIntervalEntry.get())
		self.floodInterval = int(self.pumpIntervalEntry.get())

	def SetLightOnTime1(self):
		print(self.lightOnEntry1.get())
		self.lightOnTime1 = self.lightOnEntry1.get()
	
	def SetLightOffTime1(self):
		print(self.lightOffEntry1.get())
		self.lightOffTime1 = self.lightOffEntry1.get()

	def SetPumpInterval1(self):
		print(self.pumpIntervalEntry1.get())
		self.floodInterval1 = int(self.pumpIntervalEntry1.get())
				
	def GetTemperature(self):
		self.tempInfo = self.ser.readline()
		

	def MainEvent(self):
		self.lightOnHour = int(self.lightOnTime[0:2])
		self.lightOnMinute = int(self.lightOnTime[3:5])
		self.lightOffHour = int(self.lightOffTime[0:2])
		self.lightOffMinute = int(self.lightOffTime[3:5])
		self.lightOnHour1 = int(self.lightOnTime1[0:2])
		self.lightOnMinute1 = int(self.lightOnTime1[3:5])
		self.lightOffHour1 = int(self.lightOffTime1[0:2])
		self.lightOffMinute1 = int(self.lightOffTime1[3:5])

		##self.GetTemperature()

		'''
		if datetime.datetime.now().hour >= (self.lastFlood + self.floodInterval):
			self.Flood()
		'''
		if datetime.datetime.now().hour == 0:
			self.lastFlood = 0
			self.lastFlood1 = 0
		if datetime.datetime.now().hour >= (self.lastFlood + self.floodInterval):
			print(self.lastFlood)
			print(self.floodInterval)
			print(datetime.datetime.now().hour)
			self.Flood()
		
		if datetime.datetime.now().hour >= (self.lastFlood1 + self.floodInterval1):
			print(self.lastFlood1)
			print(self.floodInterval1)
			print(datetime.datetime.now().hour)
			self.Flood1()
		
		if (self.lightOnHour > self.lightOffHour) or (self.lightOnHour == self.lightOffHour and self.lightOnMinute > self.lightOffMinute):

			if datetime.datetime.now().hour >= self.lightOnHour and datetime.datetime.now().minute	>= self.lightOnMinute:
				if(self.stateLight != 1):
					time.sleep(2)
					self.ser.write("REFc")
					time.sleep(2)
					self.stateLight = 1
					print('a')
			elif datetime.datetime.now().hour >= self.lightOffHour and datetime.datetime.now().minute >= self.lightOffMinute:
				if(self.stateLight != 2):
					time.sleep(1)
					self.ser.write("REFd")
					time.sleep(2)
					self.stateLight = 2
					print('b')
			else:
				if(self.stateLight != 3):
					time.sleep(1)
					self.ser.write("REFc")
					time.sleep(2)
					self.stateLight = 3
					print('c')
		else:
			if (datetime.datetime.now().hour == self.lightOffHour and datetime.datetime.now().minute >= self.lightOffMinute) or datetime.datetime.now().hour > self.lightOffHour:
				if(self.stateLight != 4):
					time.sleep(1)
					self.ser.write("REFd")
					time.sleep(2)
					self.stateLight = 4
					print('d')
			elif (datetime.datetime.now().hour == self.lightOnHour and datetime.datetime.now().minute	>= self.lightOnMinute) or datetime.datetime.now().hour > self.lightOnHour:
				if(self.stateLight != 5):
					time.sleep(1)
					self.ser.write("REFc")
					time.sleep(2)
					self.stateLight = 5
					print('e')
			else:
				if(self.stateLight != 6):
					time.sleep(1)
					self.ser.write("REFd")
					time.sleep(2)
					self.stateLight = 6
					print('f')
		
		if (self.lightOnHour1 > self.lightOffHour1) or (self.lightOnHour1 == self.lightOffHour1 and self.lightOnMinute1 > self.lightOffMinute1):

			if datetime.datetime.now().hour >= self.lightOnHour1 and datetime.datetime.now().minute	>= self.lightOnMinute1:
				if(self.stateLight1 != 1):
					time.sleep(2)
					self.ser.write("REFg")
					time.sleep(2)
					self.stateLight1 = 1
					print('a1')
			elif datetime.datetime.now().hour >= self.lightOffHour1 and datetime.datetime.now().minute >= self.lightOffMinute1:
				if(self.stateLight1 != 2):
					time.sleep(1)
					self.ser.write("REFh")
					time.sleep(2)
					self.stateLight1 = 2
					print('b1')
			else:
				if(self.stateLight1 != 3):
					time.sleep(1)
					self.ser.write("REFg")
					time.sleep(2)
					self.stateLight1 = 3
					print('c1')
		else:
			if (datetime.datetime.now().hour == self.lightOffHour1 and datetime.datetime.now().minute >= self.lightOffMinute1) or datetime.datetime.now().hour > self.lightOffHour1:
				if(self.stateLight1 != 4):
					time.sleep(1)
					self.ser.write("REFh")
					time.sleep(2)
					self.stateLight1 = 4
					print('d1')
			elif (datetime.datetime.now().hour == self.lightOnHour1 and datetime.datetime.now().minute	>= self.lightOnMinute1) or datetime.datetime.now().hour > self.lightOnHour1:
				if(self.stateLight1 != 5):
					time.sleep(1)
					self.ser.write("REFg")
					time.sleep(2)
					self.stateLight1 = 5
					print('e1')
			else:
				if(self.stateLight1 != 6):
					time.sleep(1)
					self.ser.write("REFh")
					time.sleep(2)
					self.stateLight1 = 6
					print('f1')
		
		

		self.stateSerial = True
		
		self.top.after(100, self.MainEvent)
		
if __name__ == "__main__":
	dripTest = DripTest()
	
		