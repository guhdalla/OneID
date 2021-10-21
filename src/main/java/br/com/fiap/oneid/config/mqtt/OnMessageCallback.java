package br.com.fiap.oneid.config.mqtt;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.oneid.controller.mqtt.MqttController;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.repository.UsuarioRepository;

public class OnMessageCallback implements MqttCallback {
	
	@Autowired
	private UsuarioRepository repository;
	
    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        System.out.println("disconnect，you can reconnect");
        MqttConnection.getInstance();
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	
        System.out.println("Received message topic:" + topic);
        System.out.println("Received message Qos:" + message.getQos());
        System.out.println("Received message content:" + new String(message.getPayload()));
        
        Optional<Usuario> usuario = repository.findById((long) 1);
        System.out.println(usuario.get());
        MqttController.whatToDo(new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}