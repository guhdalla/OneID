package br.com.fiap.oneid.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.TotalEstabelecimento;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.service.EntranceIDService;
import br.com.fiap.oneid.service.UsuarioJuridicoService;

@Controller
@RequestMapping("/entranceid")
public class EntranceIDController {
	
	private UsuarioJuridicoService serviceJuridico;
	
	private EntranceIDService serviceEntranceID;

	@Autowired
	public EntranceIDController(UsuarioJuridicoService serviceJuridico, EntranceIDService serviceEntranceID) {
		this.serviceJuridico = serviceJuridico;
		this.serviceEntranceID = serviceEntranceID;
	}

	@GetMapping
	public ModelAndView index(Authentication auth) {
		ModelAndView modelAndView = new ModelAndView("entranceid");
		Usuario usuario = (Usuario) auth.getPrincipal();
		Optional<UsuarioJuridico> usuarioJuridico = serviceJuridico.findById(usuario.getIdUsuario());
		
		List<Atividade> atividades = usuarioJuridico.get().getAtividades();
		modelAndView.addObject("atividades", atividades);
		
		List<Atividade> atividadesTotalEntrada = atividades.stream().filter(x -> x.getNrCheck() == 1).collect(Collectors.toList());
		modelAndView.addObject("atividadesTotalEntrada", atividadesTotalEntrada.size());
		
		List<Atividade> atividadesTotalSaida = atividades.stream().filter(x -> x.getNrCheck() == 0).collect(Collectors.toList());
		modelAndView.addObject("atividadesTotalSaida", atividadesTotalSaida.size());
		
		int atividadesTotalDentro = atividadesTotalEntrada.size() - atividadesTotalSaida.size();
		modelAndView.addObject("atividadesTotalDentro", atividadesTotalDentro);
		
		int totalEstabelecimento = usuarioJuridico.get().getTotalEstabelecimento();
		double atividadesTotalDentroPorcentage = 0;
		if (totalEstabelecimento <= atividadesTotalDentro) {
			atividadesTotalDentroPorcentage = 100;
		} else {
			atividadesTotalDentroPorcentage = (double) atividadesTotalDentro / totalEstabelecimento * 100;
		}
		
		modelAndView.addObject("atividadesTotalDentroPorcentage", atividadesTotalDentroPorcentage);
		return modelAndView;
	}
}
