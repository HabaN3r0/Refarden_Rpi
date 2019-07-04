#include <OneWire.h>
#include <DallasTemperature.h>
#include <TimeLib.h>


#define ONE_WIRE_BUS 2
#define TIME_HEADER "T" // Header tag for serial time sync message
#define TIME_REQUEST 7  // ASCII bell character requests a time sync message 

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
DeviceAddress thermometer;

int light1 = 10;
int pump1   = 11; //LOW for open and HIGH for close
int light2 = 12;
int pump2   = 13;

char message;

//flooding system parameters
int floodState = 0;
int countFlood = 0;
long floodTimer;
boolean flooder = true;
//long floodDelay          = 1000;
//long floodFirstPumpDelay = 1000;
//long floodDrainDelay     = 1000;
//long floodPumpDelay      = 1000;
long floodFirstPumpDelay = 3000;
long floodDrainDelay     = 1000;
long floodPumpDelay      = 1000;

void setup(){
  pinMode(light1, OUTPUT);
  pinMode(pump1, OUTPUT);
  pinMode(light2, OUTPUT);
  pinMode(pump2, OUTPUT);

  Serial.begin(9600);

  //Initialize temperature sensor
  sensors.begin();
  sensors.getAddress(thermometer, 0);
  sensors.setResolution(thermometer, 9);
  
    digitalWrite(pump1, HIGH);
    digitalWrite(light1, HIGH);
    digitalWrite(pump2, HIGH);
    digitalWrite(light2, HIGH);
}


void loop(){
    sensors.requestTemperatures();
//    printTemperature(thermometer);
    

    //Message list
    //a = Turn on light1
    while(Serial.available()){
      if(Serial.find("REF")){
        message = Serial.read();
        
        if(message == 'a'){             //pump1 flood
          digitalWrite(pump1, LOW);
         Serial.println("Flood!");
        }
        else if(message == 'b'){        //pump1 Drain
          digitalWrite(pump1, HIGH);
         Serial.println("Drain!");
        }
        else if(message == 'c'){        //light1 on
          digitalWrite(light1, LOW);
        }
        else if(message == 'd'){        //light1 off
          digitalWrite(light1, HIGH);
        }
        
      }

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
/*
void Flood(){
  while(flooder){
  if(floodState == 0){                        //  Start flooding the system
    digitalWrite(pump1,LOW);      
    if(millis() >= (floodTimer + floodFirstPumpDelay))
      floodState = 1;
    Serial.println("a");
    }
    else if(floodState == 1){                  //  Drain  a bit
    digitalWrite(pump1, HIGH);

    if(millis() >= (floodTimer + floodFirstPumpDelay + (countFlood + 1) * floodDrainDelay + countFlood * floodPumpDelay))
      floodState = 2;
      Serial.println("b");
    }
    else if(floodState == 2){                  //  Flood a bit
    digitalWrite(pump1, LOW);
    if(millis() >= (floodTimer + floodFirstPumpDelay + (countFlood + 1) * floodDrainDelay + (countFlood + 1) * floodPumpDelay))
      floodState = 1;
      countFlood += 1;
     if(countFlood >= 10){
       digitalWrite(pump1, HIGH);
       floodState = 0;
       flooder = false;
     }
      Serial.println("c");
    }
  }
}
*/
