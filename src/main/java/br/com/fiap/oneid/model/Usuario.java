package br.com.fiap.oneid.model;

import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GNT_USUARIO")
@SequenceGenerator(name="usuario", sequenceName = "SQ_T_GNT_USUARIO", allocationSize = 1)
public class Usuario {

	@Id
	@Column(name="id_usuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
	private long idUsuario;
	
	@Column(name="nm_primeiro_nome", length = 20, nullable = false)
	private String primeiroNome;
	
	@Column(name="nm_ultimo_nome", length = 50, nullable = false)
	private String sobrenome;
	
	@Column(name="dt_nascimento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(name="ds_email", length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(name="ds_telefone", length = 11, nullable = false, unique = true)
	private String telefone;
	
	@Column(name="ds_senha", length = 20, nullable = false)
	private String senha;
}