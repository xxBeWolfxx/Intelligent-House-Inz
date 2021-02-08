
#include <WiFi.h>
#include <HTTPClient.h>
#include "ArduinoJson.h"

const char* ssid = "malinka";
const char* password = "malinka312";

char serverName[43] = {"http://192.168.4.1:8000/sh/espoutdetail/"};
int IDs[]={1,2,6,7,5};
int IDcount=sizeof(IDs)/sizeof(int);

unsigned long lastTime = 0;
unsigned long timerDelay = 1000;

class aktory{
  protected:
    char serverPath[46];
  public:
    bool active;
    int ID;
    int pin;
    void init(int ID){
      this->ID=ID;
      snprintf(serverPath,46,"%s%i/",serverName,ID);
      HTTPClient http;
      http.begin(serverPath);
      StaticJsonDocument<300> jsonDoc;
      
      int httpResponseCode = http.GET();
      
      if (httpResponseCode>0) {
        Serial.print("HTTP Response code: ");
        Serial.println(httpResponseCode);
        String payload = http.getString();
        
        deserializeJson(jsonDoc,payload);

        pin=jsonDoc["pin"];
      }
      else {
        Serial.print("Error code: ");
        Serial.println(httpResponseCode);
      }
      http.end();
    }
    void update_value(){
      HTTPClient http;
      http.begin(serverPath);
      StaticJsonDocument<300> jsonDoc;
      
      int httpResponseCode = http.GET();
      
      if (httpResponseCode>0) {
        Serial.print("HTTP Response code: ");
        Serial.println(httpResponseCode);
        String payload = http.getString();
        Serial.print("Payload: ");
        Serial.println(payload);
        
        deserializeJson(jsonDoc,payload);

        active=jsonDoc["status"];
          digitalWrite(pin,active);
        
      }
      else {
        Serial.print("Error code: ");
        Serial.println(httpResponseCode);
      }
      http.end();
    }
};
    

aktory aktor[6];

void setup() {
  Serial.begin(115200); 
  pinMode(12,OUTPUT);
  pinMode(14,OUTPUT);
  pinMode(25,OUTPUT);
  pinMode(26,OUTPUT);
  pinMode(27,OUTPUT);
  pinMode(5,OUTPUT);
  pinMode(17,OUTPUT);
  digitalWrite(17,HIGH);
  pinMode(16,OUTPUT);
  digitalWrite(16,LOW);
  
  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  for(int i=0;i<IDcount;i++){
    aktor[i].init(IDs[i]);
  }
  
}

void loop() {
  
  if ((millis() - lastTime) > timerDelay) {
    if(WiFi.status()== WL_CONNECTED){
      HTTPClient http;

      for(int i=0;i<IDcount;i++){
        aktor[i].update_value();
      }
    }
    else {
      Serial.println("WiFi Disconnected");
    }
    lastTime = millis();
  }
}
