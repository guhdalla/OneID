package br.com.fiap.oneid.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_empresa")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
