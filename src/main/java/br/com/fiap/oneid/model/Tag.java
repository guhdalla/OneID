package br.com.fiap.oneid.model;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "T_GNT_TAG")
@SequenceGenerator(name = "tag", sequenceName = "SQ_T_GNT_TAG", allocationSize = 1)
public class Tag {

	@Id
	@Column(name = "id_tag")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag")
	private Long idTag;

	@Column(name = "cd_tag", nullable = false, unique = true)
	@NotBlank
	private String codigoTag;

	@Column(name = "cd_pin", length = 10, nullable = false, unique = true)
	@NotBlank
	@Size(min = 10, max = 10)
	private String codigoPin;

	@Column(name = "nr_status", length = 1, nullable = false)
	@NotNull
	private int numeroStatus;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
