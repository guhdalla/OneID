package br.com.fiap.oneid.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GNT_USUARIO_FISICO")
@SequenceGenerator(name = "usuarioFisico", sequenceName = "SQ_T_GNT_USUARIO_FISICO", allocationSize = 1)
public class UsuarioFisico {

	private long idUsuario;

	private long idCarteira;

	@Column(name = "ds_cpf", length = 11, nullable = false, unique = true)
	private String cpf;
}