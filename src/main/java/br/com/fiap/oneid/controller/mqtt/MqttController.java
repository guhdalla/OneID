package br.com.fiap.oneid.controller.mqtt;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.service.AtividadeService;

public class MqttController {

	public static void whatToDo(String msg) {
		MqttRequest request = jsonToRequest(msg);
		System.out.println(request);
		switch (request.getServico()) {
		case "1":
			System.out.println("Atividade");
			startAtividade(request);
			break;
		case "2":
			System.out.println("Transacao");
			break;
		default:
			break;
		}
	}

	public static MqttRequest jsonToRequest(String json) {
		Gson gson = new Gson();
		MqttRequest request = gson.fromJson(json, MqttRequest.class);
		return request;
	}
	
	public static void startAtividade(MqttRequest request) {
		AtividadeService.create(request);
	}
}
