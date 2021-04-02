#include <WiFi.h>
#include <HTTPClient.h>
#include "ArduinoJson.h"
#include <stdio.h>
#include <OneWire.h>
#include <DallasTemperature.h>

int A_IDs[] = {2, 5, 6, 8};
int K_IDs[] = {3, 4, 7};

int A_IDcount = sizeof(A_IDs) / sizeof(int);
int K_IDcount = sizeof(K_IDs) / sizeof(int);

const char *ssid = "malinka";
const char *password = "malinka312";

OneWire ds(32);
DallasTemperature sensors(&ds);

char serverName[46] = {"http://192.168.4.1:8000/sh/espsensordetail/"};

class sensory
{
protected:
  char serverPath[49];

public:
  bool active;
  int ID;
  int pin;
  float value;
  void init(int ID)
  {
    this->ID = ID;
    snprintf(serverPath, 49, "%s%i/", serverName, ID);
    HTTPClient http;
    http.begin(serverPath);
    StaticJsonDocument<200> jsonDoc;

    int httpResponseCode = http.GET();

    if (httpResponseCode > 0)
    {

      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
      String payload = http.getString();

      deserializeJson(jsonDoc, payload);

      pin = jsonDoc["pin"];
      active = jsonDoc["status"];
    }
    else
    {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
    }
    http.end();
  }
};

class A_sensory : public sensory
{
public:
  void read_value()
  {
    if (active)
    {
      int temp;

      switch (pin)
      {
      case 32:
        sensors.requestTemperatures();
        float temperatureC = sensors.getTempCByIndex(0);
        if (abs(temp - value) > 1)
        {
          value = temp;
          this->POST();
        }
        break;
      case 34: //fotorezystor
        temp = map(analogRead(pin), 410, 3685, 0, 20);
        if (abs(temp - value) > 1)
        {
          value = temp;
          this->POST();
        }
        break;
      case 35: //czujnik prądu
        temp = analogRead(pin) * (5 / 4095);
        float curent = (temp - 2.5) * 100;
        if (abs(temp - value) > 1)
        {
          value = temp;
          this->POST();
        }
        break;
      case 36: //czujnik wilgotności gleby
        temp = map(analogRead(pin), 4095, 0, 0, 100);
        if (abs(temp - value) > 1)
        {
          value = temp;
          this->POST();
        }
        break;
      case 39: //termometr
        temp = map(analogRead(pin), 186, 1241, -40, 50);
        if (abs(temp - value) > 1)
        {
          value = temp;
          this->POST();
        }
        break;
      default:
        temp = analogRead(pin);
        if (abs(temp - value) > 5)
        {
          value = temp;
          this->POST();
        }
        break;
      }
    }
  }
  void POST()
  {
    HTTPClient http;
    http.begin(serverPath);

    char payload[80];
    snprintf(payload, 80, "{\"pin\":%i,\"valueTemp\":%i,\"valueAvgDay\":\"\",\"valueAvgWeek\":\"\"}", pin, value);
    http.addHeader("Content-Type", "application/json");
    int httpResponseCode = http.POST(payload);

    Serial.print("Json: ");
    Serial.println(payload);

    if (httpResponseCode > 0)
    {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
    }
    else
    {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
    }
    http.end();
  }
};
class K_sensory : public sensory
{
public:
  void read_value()
  {
    if (active)
    {
      int temp;
      if (digitalRead(pin))
        temp = 0;
      else
        temp = 1;
      if (value != temp)
      {
        value = temp;
        this->POST();
      }
    }
  }

  void POST()
  {
    HTTPClient http;
    http.begin(serverPath);

    char payload[80];
    snprintf(payload, 80, "{\"pin\":%i,\"valueTemp\":%i}", pin, value);
    http.addHeader("Content-Type", "application/json");
    int httpResponseCode = http.POST(payload);

    Serial.print("Json: ");
    Serial.println(payload);

    if (httpResponseCode > 0)
    {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
    }
    else
    {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
    }
    http.end();
  }
};

A_sensory A_sensor[4];
K_sensory K_sensor[5];
unsigned long lastTime = 0;
unsigned long timerDelay = 2000;

void setup()
{
  Serial.begin(115200);

  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  if (WiFi.status() == WL_CONNECTED)
  {
    for (int i = 0; i < A_IDcount; i++)
    {
      A_sensor[i].init(A_IDs[i]);
    }
    for (int i = 0; i < K_IDcount; i++)
    {
      K_sensor[i].init(K_IDs[i]);
      pinMode(K_sensor[i].pin, INPUT);
    }
  }
  else
  {
    Serial.println("WiFi Disconnected");
  }
}

void loop()
{
  for (int i = 0; i < K_IDcount; i++)
  {
    K_sensor[i].read_value();
  }
  if ((millis() - lastTime) > timerDelay)
  {
    if (WiFi.status() == WL_CONNECTED)
    {

      for (int i = 0; i < A_IDcount; i++)
      {
        A_sensor[i].read_value();
      }
    }
    else
    {
      Serial.println("WiFi Disconnected");
    }
    lastTime = millis();
  }
}
