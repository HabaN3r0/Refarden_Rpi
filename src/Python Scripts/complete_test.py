#!/usr/bin/env python

import datetime
import threading
import sys
import glob
import serial
import time
import Tkinter as Tkinter 	#change when port to pi
from Tkinter import * 		#change when port to pi

class DripTest:

	def __init__(self):
		self.state1 = True
		self.stateSerial = True
		self.stateLight = 0
		self.floodInterval = 8
		self.stateLight1 = 0
		self.floodInterval1 = 6
		self.lightOnTime = ["08:00"]
		self.lightOffTime = ["02:00"]
		self.lightOnTime1 = ["08:00"]
		self.lightOffTime1 = ["22:00"]
		
		self.lightCount = 0
		self.stateOnOff = 0
		self.state = False		
		self.lightCount1 = 0
		self.stateOnOff1 = 0
		self.state1 = False

		
		self.ser = None
		self.top = None
		self.serState = True
		
		'''
		self.lightOffHour = None
		self.lightOffMinute = None
		self.lightOnHour = None
		self.lightOnMinute = None
		self.lightOffHour1 = None
		self.lightOffMinute1 = None
		self.lightOnHour1 = None
		self.lightOnMinute1 = None
		'''
		self.tempInfo = None
		

		self.lastFlood = datetime.datetime.now().hour
		self.lastFlood1 = datetime.datetime.now().hour
		self.floodTiming = datetime.datetime.now().hour
		self.floodStart = False
		self.floodState = True
		self.floodTiming1 = datetime.datetime.now().hour
		self.floodStart1 = False
		self.floodState1 = True
		

		# activate when port to pi
		# self.port, self.baud = self.FindPort()

		# while(self.serState):
	
		# 	try:
		# 		self.ser = serial.Serial(self.port, self.baud)
		# 		self.ser.reset_input_buffer()
		# 		self.serState = False
		# 	except:
		# 		print("Port not connected")


		self.top = Tkinter.Tk()

		self.labelTableSystem = Label(self.top, fg = "green", text = "Table System", width = '50')	
		self.labelLightOn = Label(self.top, fg = "green", text = "Light On and Off Times (hh:mm,hh:mm)")
		##self.labelLightOff = Label(self.top, fg = "green", text = "Light Off Time (hh:mm)")
		self.labelPumpInterval = Label(self.top, fg = "green", text = "Set Pump Interval (hours)")
		self.labelBoxSystem = Label(self.top, fg = "green", text = "Box System", width = '50')	
		self.labelLightOn1 = Label(self.top, fg = "green", text = "Light On and Off Times (hh:mm,hh:mm)")
		##self.labelLightOff1 = Label(self.top, fg = "green", text = "Light Off Time (hh:mm)")
		self.labelPumpInterval1 = Label(self.top, fg = "green", text = "Set Pump Interval (hours)")
		self.labelTemp = Label(self.top, fg = "green", text = "Temperature: " + "26.2C") #str(self.tempInfo))
		self.labelHumid = Label(self.top, fg = "green", text = "Humidity: " + "55%") #str(self.tempInfo))
		
		self.lightOnEntry = Entry(self.top, justify='center')
		##self.lightOffEntry = Entry(self.top, justify='center')
		self.pumpIntervalEntry = Entry(self.top, justify='center')
		self.lightOnEntry1 = Entry(self.top, justify='center')
		##self.lightOffEntry1 = Entry(self.top, justify='center')
		self.pumpIntervalEntry1 = Entry(self.top, justify='center')

		self.lightOnBtn = Tkinter.Button(self.top, text = "Set", command = self.SetLightOnTime)
		##self.lightOffBtn = Tkinter.Button(self.top, text = "Set", command = self.SetLightOffTime)
		self.pumpIntervalBtn = Tkinter.Button(self.top, text = "Set", command = self.SetPumpInterval)
		self.lightOnBtn1 = Tkinter.Button(self.top, text = "Set", command = self.SetLightOnTime1)
		##self.lightOffBtn1 = Tkinter.Button(self.top, text = "Set", command = self.SetLightOffTime1)
		self.pumpIntervalBtn1 = Tkinter.Button(self.top, text = "Set", command = self.SetPumpInterval1)
		
		self.justPumpBtn = Tkinter.Button(self.top, text = "Table Pump", command = self.Flood)
		self.justPumpBtn1 = Tkinter.Button(self.top, text = "Box Pump", command = self.Flood1)
		

		self.labelTableSystem.pack()
		self.labelLightOn.pack()
		self.lightOnEntry.pack()
		self.lightOnBtn.pack()
		##self.labelLightOff.pack()
		##self.lightOffEntry.pack()
		##self.lightOffBtn.pack()
		self.labelPumpInterval.pack()
		self.pumpIntervalEntry.pack()
		self.pumpIntervalBtn.pack()
		self.labelBoxSystem.pack()
		self.labelLightOn1.pack()
		self.lightOnEntry1.pack()
		self.lightOnBtn1.pack()
		##self.labelLightOff1.pack()
		##self.lightOffEntry1.pack()
		##self.lightOffBtn1.pack()
		self.labelPumpInterval1.pack()
		self.pumpIntervalEntry1.pack()
		self.pumpIntervalBtn1.pack()
		self.labelTemp.pack()
		self.labelHumid.pack()
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
	
	def CheckFlood(self):
		if datetime.datetime.now().hour == 0:
			self.lastFlood = 0
			self.lastFlood1 = 0
			
		if datetime.datetime.now().hour >= (self.lastFlood + self.floodInterval):
			print(self.lastFlood)
			print(self.floodInterval)
			print(datetime.datetime.now().hour)
			if self.floodState:
				self.floodState = False
				self.Flood()

		if datetime.datetime.now().hour >= (self.lastFlood1 + self.floodInterval1):
			print(self.lastFlood1)
			print(self.floodInterval1)
			print(datetime.datetime.now().hour)
			if self.floodState1:
				self.floodState1 = False
				self.Flood1()

	def FloodThread(self):
		self.ser.write("REFa")
		time.sleep(3)
		self.ser.write("REFb")
		self.floodState = True;
		time.sleep(1)
		
		self.lastFlood = datetime.datetime.now().hour	
	
	def Flood(self):
		pass
		#t = threading.Thread(target = self.FloodThread)
		#t.start()

	def FloodThread1(self):
		self.ser.write("REFe")
		time.sleep(30)
		self.ser.write("REFf")
		self.floodState1 = True;
		time.sleep(2)
		
		self.lastFlood1 = datetime.datetime.now().hour	
	
	def Flood1(self):
		t1 = threading.Thread(target = self.FloodThread1)
		t1.start()
	
		
	def Lights(self):
		if self.lightOnTime[0] > self.lightOffTime[0]:
			if datetime.datetime.now().hour < int(self.lightOffTime[0][0:2]) or (datetime.datetime.now().hour == int(self.lightOffTime[0][0:2]) and datetime.datetime.now().minute < int(self.lightOffTime[0][3:5])):
				self.state = True
			else:
				self.lightCount = len(self.lightOnTime)
				for i in range(self.lightCount):
					if datetime.datetime.now().hour > int(self.lightOnTime[self.lightCount - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOnTime[self.lightCount - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOnTime[self.lightCount - i - 1][3:5])):       
						self.state = True
						break
					elif datetime.datetime.now().hour > int(self.lightOffTime[self.lightCount - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOffTime[self.lightCount - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOffTime[self.lightCount - i - 1][3:5])):
						self.state = False
						break
			if self.lightOnTime[-1] < self.lightOffTime[0]:
				if datetime.datetime.now() < int(self.lightOffTime[0][0:2]) and datetime.datetime.now() > int(self.lightOnTime[-1][0:2]):
					self.state = False						
		elif self.lightOffTime[0] > self.lightOnTime[0]:
			if datetime.datetime.now().hour < int(self.lightOnTime[0][0:2]) or (datetime.datetime.now().hour == int(self.lightOnTime[0][0:2]) and datetime.datetime.now().minute < int(self.lightOnTime[0][3:5])):
				self.state = False
				#print('a')
			else:
				self.lightCount = len(self.lightOnTime)
				for i in range(self.lightCount):
					if datetime.datetime.now().hour > int(self.lightOffTime[self.lightCount - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOffTime[self.lightCount - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOffTime[self.lightCount - i - 1][3:5])):       
						self.state = False
						#print('c')
						break
					elif datetime.datetime.now().hour > int(self.lightOnTime[self.lightCount - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOnTime[self.lightCount - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOnTime[self.lightCount - i - 1][3:5])):
						self.state = True
						break
			print((self.lightOffTime[-1], self.lightOnTime[0]))
			if self.lightOffTime[-1] < self.lightOnTime[0]:
				#print('e')
				if datetime.datetime.now().hour < int(self.lightOnTime[0][0:2]) and datetime.datetime.now().hour >= int(self.lightOffTime[-1][0:2]):
					self.state = False
					#print('d')

			
		else:
			print("Time for off and on should never be the same. Please change the values")
					
		if self.state and self.stateOnOff != 1:
			print("On")
			time.sleep(1)
			self.ser.write("REFc")
			time.sleep(1)
			self.stateOnOff = 1
		elif not self.state and self.stateOnOff != 2:
			print("Off")
			time.sleep(1)
			self.ser.write("REFd")
			time.sleep(1)
			self.stateOnOff = 2

	def Lights1(self):		
		if self.lightOnTime1[0] > self.lightOffTime1[0]:
			if datetime.datetime.now().hour < int(self.lightOffTime1[0][0:2]) or (datetime.datetime.now().hour == int(self.lightOffTime1[0][0:2]) and datetime.datetime.now().minute < int(self.lightOffTime1[0][3:5])):
				self.state1 = True
			else:
				self.lightCount1 = len(self.lightOnTime1)
				for i in range(self.lightCount1):
					if datetime.datetime.now().hour > int(self.lightOnTime1[self.lightCount1 - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOnTime1[self.lightCount1 - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOnTime1[self.lightCount1 - i - 1][3:5])):       
						self.state1 = True
						break
					elif datetime.datetime.now().hour > int(self.lightOffTime1[self.lightCount1 - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOffTime1[self.lightCount1 - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOffTime1[self.lightCount1 - i - 1][3:5])):
						self.state1 = False
						break
			if self.lightOffTime1[-1] < self.lightOnTime1[0]:
				if datetime.datetime.now().hour < int(self.lightOnTime1[0][0:2]) and datetime.datetime.now().hour > int(self.lightOffTime1[-1][0:2]):
					self.state1 = False
		elif self.lightOffTime1[0] > self.lightOnTime1[0]:
			
			if datetime.datetime.now().hour < int(self.lightOnTime1[0][0:2]) or (datetime.datetime.now().hour == int(self.lightOnTime1[0][0:2]) and datetime.datetime.now().minute < int(self.lightOnTime1[0][3:5])):
				self.state1 = False
			else:
				self.lightCount1 = len(self.lightOnTime1)
				for i in range(self.lightCount1):
					if datetime.datetime.now().hour > int(self.lightOffTime1[self.lightCount1 - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOffTime1[self.lightCount1 - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOffTime1[self.lightCount1 - i - 1][3:5])):       
						self.state1 = False
						break
					elif datetime.datetime.now().hour > int(self.lightOnTime1[self.lightCount1 - 1 - i][0:2]) or (datetime.datetime.now().hour == int(self.lightOnTime1[self.lightCount1 - i - 1][0:2]) and datetime.datetime.now().minute > int(self.lightOnTime1[self.lightCount1 - i - 1][3:5])):
						self.state1 = True
						break
			if self.lightOffTime1[-1] < self.lightOnTime1[0]:
				if datetime.datetime.now().hour < int(self.lightOnTime1[0][0:2]) and datetime.datetime.now().hour >= int(self.lightOffTime1[-1][0:2]):
					self.state1 = False
		else:
			print("Time for off and on should never be the same. Please restart the program")


		if self.state1 and self.stateOnOff1 != 1:
			print("On1")
			time.sleep(1)
			self.ser.write("REFg")
			time.sleep(1)
			self.stateOnOff1 = 1
		elif not self.state1 and self.stateOnOff1 != 2:
			print("Off1")
			time.sleep(1)
			self.ser.write("REFh")
			time.sleep(1)
			self.stateOnOff1 = 2
		

	def SetLightOnTime(self):
		##print(self.lightOnEntry.get())
		placeHolder = self.lightOnEntry.get().strip().split(',')
		##placeHolder.sort()
		placeHolder = [x.strip() for x in placeHolder]
		self.lightOnTime = [placeHolder[0]]
		self.lightOffTime = [placeHolder[1]]
		for i in range(len(placeHolder)/2 - 1):
			self.lightOnTime.append(placeHolder[i*2 + 2])
			self.lightOffTime.append(placeHolder[i*2 + 3])		
		print(self.lightOnTime)
		print(self.lightOffTime)
			
	def SetPumpInterval(self):
		print(self.pumpIntervalEntry.get())
		self.floodInterval = int(self.pumpIntervalEntry.get())

	def SetLightOnTime1(self):
		##print(self.lightOnEntry.get())
		placeHolder = self.lightOnEntry1.get().strip().split(',')
		##placeHolder.sort()
		placeHolder = [x.strip() for x in placeHolder]
		self.lightOnTime1 = [placeHolder[0]]
		self.lightOffTime1 = [placeHolder[1]]
		for i in range(len(placeHolder)/2 - 1):
			self.lightOnTime1.append(placeHolder[i*2 + 2])
			self.lightOffTime1.append(placeHolder[i*2 + 3])		
		print(self.lightOnTime1)
		print(self.lightOffTime1)

	def SetPumpInterval1(self):
		print(self.pumpIntervalEntry1.get())
		self.floodInterval1 = int(self.pumpIntervalEntry1.get())
				
	def GetTemperature(self):
		self.tempInfo = self.ser.readline()
		

	def MainEvent(self):

		##self.GetTemperature()

		self.CheckFlood()
			
		self.Lights()
		self.Lights1()

		self.stateSerial = True
		
		self.top.after(100, self.MainEvent)
		
if __name__ == "__main__":
	dripTest = DripTest()
	
		