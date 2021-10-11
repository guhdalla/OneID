package br.com.fiap.oneid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntranceIDController {

	@GetMapping(value = {"/", "/entranceid"})
	public String index() {
		return "entranceid";
	}
}
