'use strict';

var stompClient = null;
var lastValue = 0.0;
var temperature = 0.0;
var pressureInHPa = 0;
var deviceId = 'Device Id';
var deviceLocation = 'location';

connect();

// $('#fadeThermometer').fadeIn('slow');
// $('#fadeDateTime').fadeIn('slow');
// $('#fadeDevice').fadeIn('slow');

function connect() {
    var socket = new SockJS('/temperature');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/temperature', function (temperatureMessage) {
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
    $('#temperature').fadeOut('fast');
    $('#temperature').html(temperature);
    $('#temperature').fadeIn('fast');
    $('#oldValue').fadeOut('fast');
    $('#oldValue').html(lastValue);
    $('#oldValue').fadeIn('fast');
    $('#pressureInHPa').fadeOut('fast');
    $('#pressureInHPa').html(pressureInHPa);
    $('#pressureInHPa').fadeIn('fast');
    $('#measuredAt').fadeOut('fast');
    $('#measuredAt').html(message.measuredAt);
    $('#measuredAt').fadeIn('fast');

}
