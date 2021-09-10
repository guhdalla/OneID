package br.com.fiap.oneid.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Inheritance(strategy = InheritanceType.JOINED)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_GNT_USUARIO")
@SequenceGenerator(name = "usuario", sequenceName = "SQ_T_GNT_USUARIO", allocationSize = 1)
public class Usuario {

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
	private Long idUsuario;

	@Column(name = "nm_primeiro_nome", length = 20, nullable = false)
	@NotBlank
	@Size(max = 20)
	private String primeiroNome;

	@Column(name = "nm_ultimo_nome", length = 50, nullable = false)
	@NotBlank
	@Size(max = 50)
	private String sobrenome;

	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "dt_nascimento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento;

	@Column(name = "ds_email", length = 50, nullable = false, unique = true)
	@Email
	@NotBlank
	@Size(max = 50)
	private String email;

	@Column(name = "ds_telefone", length = 11, nullable = false, unique = true)
	@Size(max = 11, min = 11)
	@NotBlank
	private String telefone;

	@Column(name = "ds_senha", length = 20, nullable = false)
	@NotBlank
	@Size(max = 20)
	private String senha;
	
	@Column(name = "ft_perfil")
	@NotBlank
	private String fotoPerfil;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Tag> tag;

}