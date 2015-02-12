# Simple Spring Boot application measuring temperature with a BMP085 sensor.

Prerequisites
-------------

What you need to run the applications:

> Java 8

> RabbitMQ (http://www.http://www.rabbitmq.com)

> RaspberryPI with Java 8 installed

> pi4j (http://pi4j.com)

How to build
------------
```
gradlew clean build
```

How to run
----------

raspberrypi-boot-bmp085:

on your development machine:

```
java -jar build/libs/raspberrypi-boot-bmp085-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
gradlew bootRun -Dspring.profiles.active=dev
```

on your raspberry pi:

```
java -jar build/libs/raspberrypi-boot-bmp085-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
gradlew bootRun -Dspring.profiles.active=prod
```

raspberrypi-boot-server:

```
java -jar build/libs/raspberrypi-boot-server-0.0.1-SNAPSHOT.jar
gradlew bootRun -Dspring.profiles.active=dev
```

raspberrypi-boot-gdata:

```
java -jar build/libs/raspberrypi-boot-gdata-0.0.1-SNAPSHOT.jar
gradlew bootRun -Dspring.profiles.active=dev
```

IDE integration
---------------
You can create an IDEA project code by running
- gradlew idea

or for eclipse via
- gradlew eclipse
