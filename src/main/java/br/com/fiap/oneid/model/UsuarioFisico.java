package br.com.fiap.oneid.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_usuario")

@Data
@Entity
@Table(name = "T_GNT_USUARIO_FISICO")
public class UsuarioFisico extends Usuario {

	private static final long serialVersionUID = 1L;

	@Size(min= 11, max = 11)
	@NotBlank
	@Column(name = "ds_cpf", length = 11, nullable = false, unique = true)
	private String cpf;

	@OneToOne
	@JoinColumn(name = "id_carteira", nullable = false)
	private Carteira carteira;

	@JsonIgnore
	@OneToMany(mappedBy = "usuarioFisico", fetch = FetchType.LAZY)
	private List<Atividade> atividades;

	@JsonIgnore
	@OneToMany(mappedBy = "usuarioFisico", fetch = FetchType.LAZY)
	private List<Transacao> transacoes;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioFisico other = (UsuarioFisico) obj;
		if (atividades == null) {
			if (other.atividades != null)
				return false;
		} else if (!atividades.equals(other.atividades))
			return false;
		if (carteira == null) {
			if (other.carteira != null)
				return false;
		} else if (!carteira.equals(other.carteira))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (transacoes == null) {
			if (other.transacoes != null)
				return false;
		} else if (!transacoes.equals(other.transacoes))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((atividades == null) ? 0 : atividades.hashCode());
		result = prime * result + ((carteira == null) ? 0 : carteira.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((transacoes == null) ? 0 : transacoes.hashCode());
		return result;
	}
}