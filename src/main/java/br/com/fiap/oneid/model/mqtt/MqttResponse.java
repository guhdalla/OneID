package br.com.fiap.oneid.model.mqtt;

import lombok.Data;

@Data
public class MqttResponse {
	private String codDispositivo;
	private int resultado;
}
