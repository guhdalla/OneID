package br.com.fiap.oneid.model.mqtt;

import lombok.Data;

@Data
public class MqttRequest {
	private String idTag;
	private String idDispositivo;
	private String servico;
}
