package br.com.fiap.oneid.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_GNT_TAG")
@SequenceGenerator(name = "item_tag", sequenceName = "SQ_T_GNT_TAG", allocationSize = 1)
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_tag")
	@Column(name = "id_tag")
	private Long idTag;

	@Column(name = "cd_tag", nullable = false, unique = true)
	private String codigoTag;

	@Column(name = "cd_pin", length = 10, nullable = false, unique = true)
	private String codigoPin;

	@Column(name = "nr_status", length = 1, nullable = false)
	private int numeroStatus;
	
	@ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
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
