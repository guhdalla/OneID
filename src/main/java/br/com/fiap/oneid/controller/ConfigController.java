package br.com.fiap.oneid.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.service.ConfigService;
import br.com.fiap.oneid.service.UsuarioJuridicoService;

@Controller
@RequestMapping("/config")
public class ConfigController {

	private UsuarioJuridicoService serviceJuridico;

	private ConfigService serviceConfig;

	@Autowired
	public ConfigController(UsuarioJuridicoService serviceJuridico, ConfigService serviceConfig) {
		this.serviceJuridico = serviceJuridico;
		this.serviceConfig = serviceConfig;
	}

	@GetMapping
	public ModelAndView index(Authentication auth) {
		ModelAndView modelAndView = new ModelAndView("config");
		Usuario usuario = (Usuario) auth.getPrincipal();
		Optional<UsuarioJuridico> usuarioJuridico = serviceJuridico.findById(usuario.getIdUsuario());
		modelAndView.addObject("usuarioJuridico", usuarioJuridico.get());
		return modelAndView;
	}

}
