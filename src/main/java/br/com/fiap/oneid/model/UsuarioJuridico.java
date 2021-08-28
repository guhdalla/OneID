package br.com.fiap.oneid.model;

import java.util.List;

import javax.persistence.*;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_empresa")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "T_GNT_USUARIO_JURIDICO")
public class UsuarioJuridico extends Usuario {

	@Column(name = "nm_fantasia", length = 50, nullable = false)
	private String nomeFantasia;

	@Column(name = "bool_authenticateID", nullable = false)
	private boolean authenticateID;

	@Column(name = "bool_entracedID", nullable = false)
	private boolean entracedID;

	// Modificação para 20 , nao necessita de máscara. 
	@Column(name = "ds_cnpj", length = 23, nullable = false)
	private String cnpj;

	@Column(name = "nm_razao_social", length = 15, nullable = false)
	private String razaoSocial;

	@Column(name = "cd_api", nullable = false)
	private String tokenApi;

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
}
