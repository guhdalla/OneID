package br.com.fiap.oneid.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import br.com.fiap.oneid.controller.mqtt.MqttController;

public class OnMessageCallback implements MqttCallback {
	
    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        System.out.println("disconnect，you can reconnect");
        MqttConnection.getInstance();
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	MqttController.whatToDo(new String(message.getPayload()));
        System.out.println("Received message topic:" + topic);
        System.out.println("Received message Qos:" + message.getQos());
        System.out.println("Received message content:" + new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}