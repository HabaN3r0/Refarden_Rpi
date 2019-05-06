#include <OneWire.h>
#include <DallasTemperature.h>
#include <TimeLib.h>


#define ONE_WIRE_BUS 2
#define TIME_HEADER "I" // Header tag for serial time sync message
#define TIME_REQUEST 7  // ASCII bell character requests a time sync message 

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
DeviceAddress thermometer;

int ac      = 10;
int valve   = 11; //LOW for open and HIGH for close
int lights  = 12;
int pump    = 13;

int temp_sense = A5;

int count2     = 0;



//flooding system parameters
int floodState = 0;
int countFlood = 0;
long floodTimer;
long floodDelay          = 1000;
long floodFirstPumpDelay = 1000;
long floodDrainDelay     = 1000;
long floodPumpDelay      = 1000;
//long floodDelay          = 14400000;
//long floodFirstPumpDelay = 60000;
//long floodDrainDelay     = 9000;
//long floodPumpDelay      = 3000;


////////////////////////////////////////////////////////////////////////////////////////////
//  TO-DO:
//  - Setup temp sensor
//  - Change all delays to timer
//  - Change all timer to actual time
//  - Setup serial feedback to python script
/////////////////////////////////////////////////////////////////////////////////////////////

void setup() {
  // put your setup code here, to run once:
  pinMode(ac, OUTPUT);
  pinMode(valve, OUTPUT);
  pinMode(lights, OUTPUT);
  pinMode(pump, OUTPUT);

  Serial.begin(9600);

  //Initialize temperature sensor
  sensors.begin();
  sensors.getAddress(thermometer, 0);
  sensors.setResolution(thermometer, 9);

  //Initialize counters
  floodTimer = millis();
}


void loop() {
  //temp sensor feedback
    sensors.requestTemperatures();
    printTemperature(thermometer);
  
  //3 minutes of flooding every 4 hours
    Flood(floodDelay);

//    if(state == 0)
//    {
//      count2 = 0;
//      digitalWrite(lights, LOW);
//      digitalWrite(ac, LOW);
//      delay(14400000);
//      count1++;
//      if(count1 > 4){
//        state = 1;
//      }
//    }else if(state == 1)
//    {
//      count1 = 0;
//      digitalWrite(lights, HIGH);
//      digitalWrite(ac, HIGH);
//      delay(180000);    
//      count2++;  
//      if(count2 > 1){
//        state = 0;
//      }
//    }
//    Serial.println(state);
}


void Flood(int timing){
  if(floodState == 0){                        //  Start flooding the system
    if(millis() >= (floodTimer + floodDelay)){
    digitalWrite(pump,LOW);      
    floodTimer = millis();
    floodState = 1;
    Serial.println("a");
    }
  }else if(floodState == 1){                  //  Stop flooding and start of loop
    if(millis() >= (floodTimer + floodFirstPumpDelay)){
      digitalWrite(pump, HIGH);
      floodTimer = millis();
      floodState = 2;
      Serial.println("b");
    }
  }else if(floodState == 2){                 //  Flooding portion of loop
      if (countFlood >= 5){
        floodTimer = millis();
        countFlood = 0;
        floodState = 0;
        Serial.println("c");
      }
      if(millis() >= (floodTimer + floodDrainDelay)){
        digitalWrite(pump, LOW);
        floodTimer = millis();        
        floodState = 3;
        Serial.println("d");
      }
  }else if(floodState == 3){                //  Draining portion of loop
//      if(millis() >= (floodTimer + floodDrainDelay)){
        digitalWrite(pump, HIGH);
        floodTimer = millis();        
        floodState = 2;
        countFlood += 1;
        Serial.println("e");
        Serial.println(countFlood);
//      }
  }
}



void printTemperature(DeviceAddress deviceAddress)
{
  // method 1 - slower
  Serial.print("Temp C: ");
  Serial.println(sensors.getTempC(deviceAddress));
}


void printAddress(DeviceAddress deviceAddress)
{
  for (uint8_t i = 0; i < 8; i++)
  {
    if (deviceAddress[i] < 16) Serial.print("0");
    Serial.print(deviceAddress[i], HEX);
  }
}
