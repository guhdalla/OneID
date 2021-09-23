package br.com.fiap.oneid.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@NotBlank
	@Size(max = 50)
	private String rua;
	
	@Column(name="ds_cep", length = 8, nullable = false)
	@NotBlank
	@Size(max = 8, min = 8)
	private String cep;
	
	@Column(name="ds_bairro", length = 20, nullable = false)
	@NotBlank
	@Size(max = 20)
	private String bairro;
	
	@Column(name="ds_cidade", length = 20, nullable = false)
	@NotBlank
	@Size(max = 20)
	private String cidade;
	
	@Column(name="ds_uf", length = 2, nullable = false)
	@NotBlank
	@Size(max = 2)
	private String uf;
	
	@Column(name="nr_numero", length = 20, nullable = false)
	@NotBlank
	@Size(max = 20)
	private String numero;
	
	@Column(name="ds_complemento", length = 30)
	@Size(max = 30)
	private String complemento;
}