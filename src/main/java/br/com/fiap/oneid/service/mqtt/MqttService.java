package br.com.fiap.oneid.service.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.fiap.oneid.config.mqtt.MqttConnection;
import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.service.AtividadeService;
import br.com.fiap.oneid.service.TransacaoService;

@Service
public class MqttService {
	
	@Autowired
	private AtividadeService serviceAtividade;
	
	@Autowired
	private TransacaoService serviceTransacao;

	public void whatToDo(Object msg) {
		MqttRequest request = jsonToRequest(msg.toString());
		switch (request.getServico()) {
		case "1":
			startAtividade(request);
			break;
		case "2":
			startTransacao(request);
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

	private void startTransacao(MqttRequest request) {
		Transacao transacao = serviceTransacao.finalizar(request);
		
		MqttConnection client = MqttConnection.getInstance();
		if (transacao == null) {
			client.pubFalse(request.getIdDispositivo());
			return;
		}
		client.pubTrue(request.getIdDispositivo());
	}
	
	public void startAtividade(MqttRequest request) {
		Atividade atividade = serviceAtividade.create(request);
		
		MqttConnection client = MqttConnection.getInstance();
		if (atividade == null) {
			client.pubFalse(request.getIdDispositivo());
			return;
		}
		client.pubTrue(request.getIdDispositivo());
	}
}
