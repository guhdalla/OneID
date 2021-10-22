package br.com.fiap.oneid.config.mqtt;

import org.springframework.beans.factory.annotation.Value;

public abstract class MqttVariables {
	
	@Value("${mqtt.broker}")
	static String BROKER;
	
	@Value("${mqtt.client}")
	static String CLIENT;
	
	@Value("${mqtt.topic.request}")
	static String TOPIC_REQUEST;
}
