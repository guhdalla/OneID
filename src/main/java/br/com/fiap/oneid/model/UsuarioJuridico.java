package br.com.fiap.oneid.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_empresa")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
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

	@Column(name = "ds_cnpj", length = 20, nullable = false, unique = true)
	@Size(min = 14, max = 20)
	@NotBlank
	private String cnpj;

	@Column(name = "nm_razao_social", length = 50, nullable = false, unique = true)
	@Size(max = 50)
	@NotBlank
	private String razaoSocial;

	@Column(name = "nr_estabelecimento")
	@Min(0)
	private int totalEstabelecimento;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_carteira", nullable = false)
	private Carteira carteira;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco", nullable = false)
	private Endereco endereco;

}
