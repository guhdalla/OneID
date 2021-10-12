package br.com.fiap.oneid.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_empresa")

@Data
@Entity
@Table(name = "T_GNT_USUARIO_JURIDICO")
public class UsuarioJuridico extends Usuario {

	private static final long serialVersionUID = 1L;

	@Column(name = "nm_fantasia", length = 50, nullable = false)
	@Size(max = 50)
	@NotBlank
	private String nomeFantasia;

	@Column(name = "bool_authenticateID", nullable = false)
	private boolean authenticateID;

	@Column(name = "bool_entracedID", nullable = false)
	private boolean entranceID;

	// Modificação para 20 , nao necessita de máscara. 
	@Column(name = "ds_cnpj", length = 14, nullable = false, unique = true)
	@Size(min = 14, max = 14)
	@NotBlank
	private String cnpj;

	@Column(name = "nm_razao_social", length = 50, nullable = false, unique = true)
	@Size(max = 50)
	@NotBlank
	private String razaoSocial;

	@Column(name = "cd_api", nullable = false, unique = true)
	@NotBlank
	private String tokenApi;
	
	@Column(name = "nr_estabelecimento")
	@Min(0)
	private int totalEstabelecimento;

	@OneToOne
	@JoinColumn(name = "id_carteira", nullable = false)
	private Carteira carteira;

	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_endereco", nullable = false)
	private Endereco endereco;

	@OneToMany(mappedBy = "usuarioJuridico", fetch = FetchType.LAZY)
	private List<Atividade> atividades;

	@OneToMany(mappedBy = "usuarioJuridico", fetch = FetchType.LAZY)
	private List<Transacao> transacoes;

	@OneToMany(mappedBy = "usuarioJuridico", fetch = FetchType.LAZY)
	private List<Dispositivo> dispositivos;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioJuridico other = (UsuarioJuridico) obj;
		if (atividades == null) {
			if (other.atividades != null)
				return false;
		} else if (!atividades.equals(other.atividades))
			return false;
		if (authenticateID != other.authenticateID)
			return false;
		if (carteira == null) {
			if (other.carteira != null)
				return false;
		} else if (!carteira.equals(other.carteira))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (dispositivos == null) {
			if (other.dispositivos != null)
				return false;
		} else if (!dispositivos.equals(other.dispositivos))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (entranceID != other.entranceID)
			return false;
		if (nomeFantasia == null) {
			if (other.nomeFantasia != null)
				return false;
		} else if (!nomeFantasia.equals(other.nomeFantasia))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (tokenApi == null) {
			if (other.tokenApi != null)
				return false;
		} else if (!tokenApi.equals(other.tokenApi))
			return false;
		if (totalEstabelecimento != other.totalEstabelecimento)
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
		result = prime * result + (authenticateID ? 1231 : 1237);
		result = prime * result + ((carteira == null) ? 0 : carteira.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((dispositivos == null) ? 0 : dispositivos.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + (entranceID ? 1231 : 1237);
		result = prime * result + ((nomeFantasia == null) ? 0 : nomeFantasia.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((tokenApi == null) ? 0 : tokenApi.hashCode());
		result = prime * result + totalEstabelecimento;
		result = prime * result + ((transacoes == null) ? 0 : transacoes.hashCode());
		return result;
	}
	
	
}
