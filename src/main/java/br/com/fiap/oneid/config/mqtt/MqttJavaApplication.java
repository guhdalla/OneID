package br.com.fiap.oneid.config.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import br.com.fiap.oneid.service.mqtt.MqttService;

@SpringBootApplication
public class MqttJavaApplication {

	@Autowired
	private MqttService service;

	public static void main(String[] args) {
		new SpringApplicationBuilder(MqttJavaApplication.class).run(args);
	}

	@Bean
	public IntegrationFlow mqttInbound() {
		return IntegrationFlows
				.from(new MqttPahoMessageDrivenChannelAdapter("tcp://broker.hivemq.com:1883", "client-oneid", "bgmbnewgen8462/oneid/empresa/request"))
				.handle(m -> service.whatToDo(m.getPayload())).get();
	}

}
