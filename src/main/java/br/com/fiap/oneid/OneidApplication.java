package br.com.fiap.oneid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import br.com.fiap.oneid.service.mqtt.MqttService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
public class OneidApplication extends SpringBootServletInitializer {
	
	@Autowired
	private MqttService service;

	public static void main(String[] args) {
		SpringApplication.run(OneidApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(OneidApplication.class);
	}
	
	@Bean
	public IntegrationFlow mqttInbound() {
		return IntegrationFlows
				.from(new MqttPahoMessageDrivenChannelAdapter("tcp://broker.hivemq.com:1883", "client-oneid", "bgmbnewgen8462/oneid/empresa/request"))
				.handle(m -> service.whatToDo(m.getPayload())).get();
	}
}
