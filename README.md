# Simple Spring Boot application measuring temperature with a BMP085 sensor.

Features
--------

* A [Springboot](http://projects.spring.io/spring-boot/) standalone application measuring temperature and pressure with an BMP085 sensor
* Measured data is sent via MQTT to a [RabbitMQ](http://www.rabbitmq.com/) server
* Application is using [Spring-Integration 4.1](http://projects.spring.io/spring-boot/) with annotation based configuration
* A [Springboot](http://projects.spring.io/spring-boot/) web application consuming the MQTT data and relaying it via STOMP
* pushing the data via Websocket to the clients
* A [Springboot](http://projects.spring.io/spring-boot/) standalone application consuming the MQTT data and writing it to a Google Spreadsheet

See a live demo [here](http://www.ksbrwsk.de:8080/)

Prerequisites
-------------

**What you need to run the applications:**

* Java 8
* RabbitMQ (http://www.http://www.rabbitmq.com)
* RaspberryPI with Java 8 installed and wired BMP085 sensor
* pi4j (http://pi4j.com)

Module description
------------------

### raspberrypi-boot-bmp085

This is the application meant to run on Raspberry PI, measuring data via BMP085 sensor.
In development mode there is a mock implementation generating random data.

Application properties can be configured in
```
raspberrypi-boot-bmp085/src/main/resources
```

Use

```
mvn clean install
```
to build the application and

```
java -jar target/raspberrypi-boot-bmp085-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
mvn spring-boot:run -Dspring.profiles.active=dev
```

to run it on the development machine.

### raspberrypi-boot-server

This is the web application relaying the MQTT data via stomp and pushing
it to the clients via Websocket.

Application properties can be configured in
```
raspberrypi-boot-server/src/main/resources
```

Use

```
mvn clean install
```
to build the application and

```
mvn springBoot:run -Dspring.profiles.active=dev
```

to run it.

### raspberrypi-boot-gdata

This is the application consuming the MQTT data and writing it to a
Google Data Spreadsheet.
Please make sure you have an Google Account and downloaded the
JSON Credentials file as described in [this document](https://developers.google.com/accounts/docs/OAuth2)

Application properties can be configured in
```
raspberrypi-boot-gdata/src/main/resources
```

Use

```
mvn clean install
```
to build the application and

```
mvn springBoot:run -Dspring.profiles.active=dev
```

to run it.
