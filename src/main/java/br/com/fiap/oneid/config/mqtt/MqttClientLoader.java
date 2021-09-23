package br.com.fiap.oneid.config.mqtt;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MqttClientLoader implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        MqttConnection.getInstance();
    }
}