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
		self.floodInterval = 1
		self.lightOnTime = "17:38:00"
		self.lightOffTime = "17:40:00"
		self.ser = None
		self.top =None
		self.lightOnHour = None
		self.lightOnMinute = None
		self.lightOffHour = None
		self.lightOffMinute = None
		

		self.lastFlood = datetime.datetime.now().minute
		
		self.port, self.baud = self.FindPort()

		while(self.state1):
	
			try:
				self.ser = serial.Serial(self.port, self.baud)
				self.ser.reset_input_buffer()
				self.state1 = False
			except:
				print("Port not connected")


		self.top = Tkinter.Tk()
	
		self.labelLightOn = Label(self.top, fg = "green", text = "Light On Time (hh:mm)")
		self.labelLightOff = Label(self.top, fg = "green", text = "Light Off Time (hh:mm)")
		self.labelPumpInterval = Label(self.top, fg = "green", text = "Set Pump Interval (hours)")


		self.lightOnEntry = Entry(self.top, justify='center')
		self.lightOffEntry = Entry(self.top, justify='center')
		self.pumpIntervalEntry = Entry(self.top, justify='center')



		self.lightOnBtn = Tkinter.Button(self.top, text = "Set", command = self.SetLightOnTime)
		self.lightOffBtn = Tkinter.Button(self.top, text = "Set", command = self.SetLightOffTime)
		self.pumpIntervalBtn = Tkinter.Button(self.top, text = "Set", command = self.SetPumpInterval)

		self.label1 = Label(self.top, fg = "green")




		self.labelLightOn.pack()
		self.lightOnEntry.pack()
		self.lightOnBtn.pack()
		self.labelLightOff.pack()
		self.lightOffEntry.pack()
		self.lightOffBtn.pack()
		self.labelPumpInterval.pack()
		self.pumpIntervalEntry.pack()
		self.pumpIntervalBtn.pack()


		self.label1.pack()
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
		self.lastFlood = datetime.datetime.now().minute

	def SetLightOnTime(self):
		print(self.lightOnEntry.get())
		self.lightOnTime = self.lightOnEntry.get()
	
	def SetLightOffTime(self):
		print(self.lightOffEntry.get())
		self.lightOffTime = self.lightOffEntry.get()

	def SetPumpInterval(self):
		print(self.pumpIntervalEntry.get())
		self.floodInterval = int(self.pumpIntervalEntry.get())

	def MainEvent(self):
		self.lightOnHour = int(self.lightOnTime[0:2])
		self.lightOnMinute = int(self.lightOnTime[3:5])
		self.lightOffHour = int(self.lightOffTime[0:2])
		self.lightOffMinute = int(self.lightOffTime[3:5])
		'''
		if datetime.datetime.now().hour >= (lastFlood + floodInterval):
			Flood()
		'''
		if datetime.datetime.now().minute >= (self.lastFlood + self.floodInterval):
			print(self.lastFlood)
			print(self.floodInterval)
			print(datetime.datetime.now().minute)
			self.Flood()

		
		if (self.lightOnHour > self.lightOffHour) or (self.lightOnHour == self.lightOffHour and self.lightOnMinute > self.lightOffMinute):
			if datetime.datetime.now().hour >= self.lightOnHour and datetime.datetime.now().minute	>= self.lightOnMinute:
				self.ser.write("REFc")
				print("a")
				time.sleep(0.1)
			elif datetime.datetime.now().hour >= self.lightOffHour and datetime.datetime.now().minute >= self.lightOffMinute:
				self.ser.write("REFd")
				print("b")
				time.sleep(0.1)
			else:
				self.ser.write("REFc")
				print("c")
				time.sleep(0.1)	
		else:
			if (datetime.datetime.now().hour == self.lightOffHour and datetime.datetime.now().minute >= self.lightOffMinute) or datetime.datetime.now().hour > self.lightOffHour:
				self.ser.write("REFd")
				print("d")
				time.sleep(0.1)
			elif (datetime.datetime.now().hour == self.lightOnHour and datetime.datetime.now().minute	>= self.lightOnMinute) or datetime.datetime.now().hour > self.lightOnHour:
				self.ser.write("REFc")
				print("e")
				time.sleep(0.1)		
			else:
				self.ser.write("REFd")
				print("f")
				time.sleep(0.1)
		
		
		
		self.top.after(100, self.MainEvent)
		
if __name__ == "__main__":
	dripTest = DripTest()
