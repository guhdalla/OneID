package br.com.fiap.oneid.service.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.fiap.oneid.config.mqtt.MqttConnection;
import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.service.AtividadeService;

@Service
public class MqttService {
	
	@Autowired
	private AtividadeService serviceAtividade;

	public void whatToDo(Object msg) {
		MqttRequest request = jsonToRequest(msg.toString());
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

	public MqttRequest jsonToRequest(String json) {
		Gson gson = new Gson();
		MqttRequest request = gson.fromJson(json, MqttRequest.class);
		return request;
	}
	
	public void startAtividade(MqttRequest request) {
		Atividade atividade = serviceAtividade.create(request);
		
		MqttConnection client = MqttConnection.getInstance();
		if (atividade == null) {
			System.out.println("Atividade nao criada");
			client.pubFalse(request.getIdDispositivo());
			return;
		}
		System.out.println(atividade);
		client.pubTrue(request.getIdDispositivo());
	}
}
