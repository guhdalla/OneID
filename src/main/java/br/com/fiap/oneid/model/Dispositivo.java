package br.com.fiap.oneid.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GNT_DISPOSITIVO")
@SequenceGenerator(name = "dispositivo", allocationSize = 1, sequenceName = "SQ_T_GNT_DISPOSITIVO")
public class Dispositivo {

	@Id
	@Column(name="id_dispositivo")
	@GeneratedValue(generator = "dispositivo", strategy = GenerationType.SEQUENCE)
	private Long idDispositivo;
	
	@Column(name="cd_dispositivo", length = 10, nullable = false, unique = true)
	@NotBlank
	@Size(max = 10)
	private String cdDispositivo;
	
	@Column(name="nr_status", length = 1, nullable = false)
	@NotNull
	@Max(3)
	private int statusDispositivo;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	private UsuarioJuridico usuarioJuridico;
}
