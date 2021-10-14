package br.com.fiap.oneid.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.oneid.model.TotalEstabelecimento;
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
		
		modelAndView.addObject("totalEstabelecimento", new TotalEstabelecimento(usuarioJuridico.get().getTotalEstabelecimento()));
		return modelAndView;
	}
	
	@PostMapping("/total-estabelecimento")
	public String saveTotalEstabelecimento(@Valid TotalEstabelecimento totalEstabelecimento, BindingResult result, RedirectAttributes redirect, Authentication auth) {
		if(result.hasErrors()) return "/config";
		Usuario usuario = (Usuario) auth.getPrincipal();
		serviceJuridico.updateTotalEstabeleciemnto(usuario, totalEstabelecimento.getNumero());
		redirect.addFlashAttribute("success", "O numero total do estabelecimento foi alterado com sucesso!");
		return "redirect:/config";
	}
}
