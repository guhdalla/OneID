package br.com.fiap.oneid.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(allocationSize = 1, name = "atividades", sequenceName = "SQ_T_GNT_ATIVIDADES")
public class Atividades {
	
	@Id
	@Column(name="id_atividades")
	@GeneratedValue(generator = "atividades", strategy = GenerationType.SEQUENCE)
	private Long idAtividades;
	
	@Column(name="id_empresa",length = 3)
	private Long idEmpresa;
	
	@Column(name="id_usuario",length = 3)
	private Long idUsuario;
	
	@Column(name="dt_check")
	private Date dtCheck;
	
	@Column(name="nr_check")
	private int nrCheck;
	

}
