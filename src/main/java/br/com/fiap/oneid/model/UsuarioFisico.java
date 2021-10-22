package br.com.fiap.oneid.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_usuario")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "T_GNT_USUARIO_FISICO")
public class UsuarioFisico extends Usuario {

	private static final long serialVersionUID = 1L;

	@Size(min = 11, max = 14)
	@NotBlank
	@Column(name = "ds_cpf", length = 14, nullable = false, unique = true)
	private String cpf;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_carteira", nullable = false)
	private Carteira carteira;
}