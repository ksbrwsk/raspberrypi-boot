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
> gradlew clean build

How to run
----------

raspberrypi-boot-bmp085:

on your development machine:

> java -jar build/libs/raspberrypi-boot-bmp085-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
> gradle bootRun --spring.profiles.active=dev

on your raspberry pi:

> java -jar build/libs/raspberrypi-boot-bmp085-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
> gradle bootRun --spring.profiles.active=prod

raspberrypi-boot-server:

> java -jar build/libs/raspberrypi-boot-server-0.0.1-SNAPSHOT.jar
> gradle bootRun

IDE integration
---------------
You can create an IDEA project for the full dolphin code by running
- gradlew idea

or for eclipse via
- gradlew eclipse
