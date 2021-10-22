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
			System.out.println("Atividade");
			startAtividade(request);
			break;
		case "2":
			System.out.println("Transacao");
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
			System.out.println("Transacao nao criada");
			client.pubFalse(request.getIdDispositivo());
			return;
		}
		System.out.println(transacao);
		client.pubTrue(request.getIdDispositivo());
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
