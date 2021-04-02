
import json

from SH_rest import SH_object

a = SH_object

payload = {'pin':'25','status':'true'}
a.ESPOut('POST',payload,"5")
