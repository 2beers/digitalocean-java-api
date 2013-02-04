digitalocean-java-api
=====================

DigitalOcean API for Java


Usage:
```java
//Basic usage
String clientKey="";
String apiKey="";
	
DigitalOceanAPI  api=new DigitalOceanAPI(clientKey,apiKey);
int dropletID=api.newDroplet(name, sizeID, imageID, regionID);
```


Project Dependencies

[Google Gson](http://code.google.com/p/google-gson/)

[HttpClient](http://hc.apache.org/httpcomponents-client-ga/)

[comoons-logging](http://commons.apache.org/logging/)
