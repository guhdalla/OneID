package br.com.fiap.oneid.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "T_GNT_CARTEIRA")
@SequenceGenerator(name = "carteira", sequenceName = "SQ_T_GNT_CARTEIRA", allocationSize = 1)
public class Carteira {
	
	public Carteira(@Size(max = 10) @NotBlank @Min(0) float saldo) {
		super();
		this.saldo = saldo;
	}

	@Id
	@Column(name="id_carteira")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carteira")
	private Long id;
	
	@Column(name="vl_saldo", length = 10, nullable = false)
	@NotNull
	@Min(0)
	private float saldo;
}