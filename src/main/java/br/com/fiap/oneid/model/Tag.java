package br.com.fiap.oneid.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TB_GNT_TAG")
@SequenceGenerator(name = "item_tag", sequenceName = "SQ_T_GNT_TAG", allocationSize = 1)
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_tag")
    @Column(name="id_tag")
    private Long id;
    @Column(name="id_usuario")
    private Long idUsuario;
    @Column(name="cd_tag", nullable = false, unique = true)
    private String codigoTag;
    @Column(name="cd_pin", nullable = false, unique = true)
    private String codigoPin;
    @Column(name="nr_status", nullable = false)
    private Integer numeroStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;

        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return 50257180;
    }
}
