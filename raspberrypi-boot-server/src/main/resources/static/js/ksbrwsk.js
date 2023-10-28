'use strict';

var stompClient = null;
var lastValue = 0.0;
var temperature = 0.0;
var pressureInHPa = 0;
var deviceId = 'Device Id';
var deviceLocation = 'location';

connect();


function connect() {
    var socket = new SockJS('/temperature');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {

        stompClient.subscribe('/topic/temperature', function (temperatureMessage) {
            console.log("Method subscribe");
            var temperature = JSON.parse(temperatureMessage.body);
            showTemperature(temperature);
        });
    });
}

function showTemperature(message) {

    lastValue = temperature;
    temperature = parseFloat(message.degreesInCelsius).toFixed(1);
    pressureInHPa = parseFloat(message.pressureInHPa).toFixed(0);
    deviceId = message.deviceId;
    deviceLocation = message.deviceLocation;

    $('#deviceId').html(deviceId);
    $('#deviceLocation').html(deviceLocation);
    $('#temperature').html(temperature);
    $('#oldValue').html(lastValue);
    $('#pressureInHPa').html(pressureInHPa);
    $('#measuredAt').html(message.measuredAt);

}
