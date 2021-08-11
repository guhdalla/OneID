package br.com.fiap.oneid.model;

import javax.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GNT_ENDERECO")
@SequenceGenerator(name="endereco", sequenceName = "SQ_T_GNT_ENDERECO", allocationSize = 1)
public class Endereco {
 
	@Id
	@Column(name="id_endereco")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
	private Long idEndereco;
	
	@Column(name="ds_rua", length = 50, nullable = false)
	private String rua;
	
	@Column(name="ds_cep", length = 7, nullable = false)
	private String cep;
	
	@Column(name="ds_bairro", length = 20, nullable = false)
	private String bairro;
	
	@Column(name="ds_cidade", length = 20, nullable = false)
	private String cidade;
	
	@Column(name="ds_uf", length = 20, nullable = false)
	private String uf;
	
	@Column(name="nr_numero", length = 20, nullable = false)
	private String numero;
	
	@Column(name="ds_complemento", length = 30)
	private String complemento;
}