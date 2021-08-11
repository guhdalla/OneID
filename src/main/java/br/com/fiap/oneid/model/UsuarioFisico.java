package br.com.fiap.oneid.model;

import java.util.List;

import javax.persistence.*;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_usuario")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "T_GNT_USUARIO_FISICO")
public class UsuarioFisico extends Usuario {

	@Column(name = "ds_cpf", length = 11, nullable = false, unique = true)
	private String cpf;

	@OneToOne
	@JoinColumn(name = "id_carteira", nullable = false)
	private Carteira carteira;

	@OneToMany(mappedBy = "usuarioFisico", fetch = FetchType.LAZY)
	private List<Atividade> atividades;

	@OneToMany(mappedBy = "usuarioFisico", fetch = FetchType.LAZY)
	private List<Transacao> transacoes;
}