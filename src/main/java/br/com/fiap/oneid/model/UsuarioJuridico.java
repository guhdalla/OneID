package br.com.fiap.oneid.model;

import javax.persistence.*;

import lombok.*;

@PrimaryKeyJoinColumn(name = "id_empresa")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(sequenceName = "SQ_T_GNT_USUARIO_JURIDICO", allocationSize = 1, name = "usuarioJuridico")
public class UsuarioJuridico {
	
	@Column(name="id_carteira")
	private Long idCarteira;
	
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Column(name="id_endereco")
	private Long idEndereco;
	
	@Column(name="nm_fantasia", length = 50, nullable = false)
	private String nomeFantasia;
	
	@Column(name="bool_authenticateID", nullable = false)
	private boolean authenticateID;
	
	@Column(name="bool_entracedID", nullable = false)
	private boolean entracedID;
	
	@Column(name="ds_cnpj",length = 14, nullable = false)
	private String cnpj;
	
	@Column(name="nm_razao_social", nullable = false)
	private String razaoSocial;
	
	

}
