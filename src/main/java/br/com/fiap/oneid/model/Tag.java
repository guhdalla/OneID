package br.com.fiap.oneid.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	@ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Tag tag = (Tag) o;

		return Objects.equals(idTag, tag.idTag);
	}

	@Override
	public int hashCode() {
		return 50257180;
	}
}
