package br.com.fiap.oneid.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TB_GNT_TRANSACAO")
@SequenceGenerator(name = "item_transacao", sequenceName = "SQ_T_GNT_TRANSACAO", allocationSize = 1)
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_transacao")
    @Column(name="id_transacao")
    private Long id;
    @Column(name="id_empresa")
    private Long idEmpresa;
    @Column(name="id_usuario")
    private Long idUsuario;
    @Column(name="dt_movimentacao")
    @Temporal(TemporalType.DATE)
    private Calendar dataMovimentacao;
    @Column(name="vl_transacao")
    private Double valorTransacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transacao transacao = (Transacao) o;

        return Objects.equals(id, transacao.id);
    }

    @Override
    public int hashCode() {
        return 1221207467;
    }
}
