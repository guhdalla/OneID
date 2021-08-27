package br.com.fiap.oneid.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GNT_CARTEIRA")
@SequenceGenerator(name = "carteira", sequenceName = "SQ_T_GNT_CARTEIRA", allocationSize = 1)
public class Carteira {

	@Id
	@Column(name="id_carteira")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carteira")
	private Long id;
	
	@Column(name="vl_saldo", length = 10, nullable = false)
	private float saldo;
}