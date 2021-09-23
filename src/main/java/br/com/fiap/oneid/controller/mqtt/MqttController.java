package br.com.fiap.oneid.controller.mqtt;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.com.fiap.oneid.controller.AtividadeController;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.service.AtividadeService;

@Controller
public class MqttController {

	public static void whatToDo(String msg) {
		MqttRequest request = jsonToRequest(msg);
		switch (request.getServico()) {
		case "1":
			startAtividade(request);
			break;
		case "2":

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
