package br.com.fiap.oneid.model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TB_GNT_TRANSACAO")
@SequenceGenerator(name = "item_transacao", sequenceName = "SQ_GNT_TRANSACAO", allocationSize = 1)
public class Transacao {
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
}
