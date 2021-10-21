package br.com.fiap.oneid.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Entity
@Data
@Table(name = "T_GNT_ATIVIDADE")
@SequenceGenerator(allocationSize = 1, name = "atividade", sequenceName = "SQ_T_GNT_ATIVIDADE")
public class Atividade {

	@Id
	@Column(name = "id_atividade")
	@GeneratedValue(generator = "atividade", strategy = GenerationType.SEQUENCE)
	private Long idAtividade;

	@DateTimeFormat
	@Column(name = "dt_check", nullable = false)
	private Date dtCheck;

	@Column(name = "nr_check", nullable = false)
	private int nrCheck; // 0 = check-out // 1 = check-in

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioFisico usuarioFisico;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empresa", nullable = false)
    private UsuarioJuridico usuarioJuridico;
	
	
}
