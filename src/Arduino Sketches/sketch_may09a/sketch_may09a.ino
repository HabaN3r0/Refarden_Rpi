int light1 = 10;
int pump1   = 11; //LOW for open and HIGH for close
int light2 = 12;
int pump2   = 13;

char message;

//flooding system parameter


void setup(){
  pinMode(light1, OUTPUT);
  pinMode(pump1, OUTPUT);
  pinMode(light2, OUTPUT);
  pinMode(pump2, OUTPUT);

  Serial.begin(9600);
}
  
  
void loop(){
    digitalWrite(pump1, HIGH);
    digitalWrite(light1, HIGH);
    digitalWrite(pump2, LOW);
    digitalWrite(light2, HIGH);
}
