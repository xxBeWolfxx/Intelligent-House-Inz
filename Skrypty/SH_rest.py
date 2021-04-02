import requests


class SH_object:
    baseURL = ""
    URLsensor = f"{baseURL}sensors/"
    URLoutputs = f"{baseURL}outputs/"
    adminLogin = "RPi"
    adminPassword = "rpi"

    def __init__(self, URL):
        self.baseURL = URL

    @classmethod
    def UserRequest(self, method, payload, login=adminLogin):
        if method == "GET":
            try:
                data = requests.get(
                    f"{self.baseURL}userlogin/{login}/?format=json").json()
            except requests.HTTPError():
                return "Error"
        elif method == "POST":
            requests.post(
                f"{self.baseURL}userlogin/{login}/?format=json", json=payload)
            return "Done"

    @classmethod
    def ESPOut(self, method, payload, idESP):
        if method == 'GET':
            try:
                data = requests.get(
                    f"{self.URLoutputs}").json()
            except requests.HTTPError():
                return "Error"
        elif method == 'POST':
            requests.post(
                f"http://localhost:8000/sh/espoutdetail/{idESP}/", json=payload)
            data=payload
            return "Done"

    @classmethod
    def ESPSensors(self, method, payload, login=adminLogin, idESP=""):
        if method == 'GET':
            try:
                data = requests.get(
                    f"{self.URLsensor}").json()
            except requests.HTTPError():
                return "Error"
        elif method == 'POST':
            requests.post(
                f"http://localhost:8000/sh/espsensordetail/{idESP}/", json=payload)
            data = payload
            return "Done"
