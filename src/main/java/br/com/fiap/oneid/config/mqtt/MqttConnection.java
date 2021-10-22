package br.com.fiap.oneid.config.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.fiap.oneid.model.mqtt.MqttResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
public class MqttConnection {

	private String broker = "tcp://broker.hivemq.com:1883";
	private String pubTopic = "bgmbnewgen8462/oneid/empresa/response";
	public MqttAsyncClient client;
	private static MqttConnection instance;

	public void connectMqtt() {

		MemoryPersistence persistence = new MemoryPersistence();

		try {
			client = new MqttAsyncClient(broker, UUID.randomUUID().toString(), persistence);

			IMqttToken token = client.connect();
			token.waitForCompletion();
			
			System.out.println("Connected MQTT");

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	public void pub(String pubTopic, String content, int qos) {
		try {
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			client.publish(pubTopic, message);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
	
	public void pubFalse(String codDispositivo) {
		try {
			MqttResponse response = new MqttResponse();
			response.setCodDispositivo(codDispositivo);
			response.setResultado(0);
			
			Gson gson = new Gson();
			String content = gson.toJson( response );
			
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(1);
			client.publish(pubTopic, message);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
	
	public void pubTrue(String codDispositivo) {
		try {
			MqttResponse response = new MqttResponse();
			response.setCodDispositivo(codDispositivo);
			response.setResultado(1);
			
			Gson gson = new Gson();
			String content = gson.toJson( response );
			
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(1);
			client.publish(pubTopic, message);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	public void sub(String subTopic, int qos) {
		try {
			client.subscribe(subTopic, qos);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			client.disconnect();
			System.out.println("Disconnected");
			client.close();
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
	
	public static MqttConnection getInstance() {
        if (instance == null) {
            instance = new MqttConnection();
            instance.connectMqtt();
        }
        return instance;
    }
}
