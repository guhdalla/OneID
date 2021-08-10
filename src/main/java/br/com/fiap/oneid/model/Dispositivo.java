package br.com.fiap.oneid.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "dispositivo", allocationSize = 1, sequenceName = "SQ_T_GNT_DISPOSITIVO")
public class Dispositivo {

	@Id
	@Column(name="id_dispositivo", length = 3)
	@GeneratedValue(generator = "dispositivo", strategy = GenerationType.SEQUENCE)
	private Long idDispositivo;
	
	@Column(name="cd_dispositivo", length = 10)
	private String cdDispositivo;
	
	private UsuarioJuridico idEmpresa;
}
