package br.com.fiap.oneid.model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "T_GNT_TRANSACAO")
@SequenceGenerator(name = "item_transacao", sequenceName = "SQ_T_GNT_TRANSACAO", allocationSize = 1)
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_transacao")
	@Column(name = "id_transacao")
	private Long id;

	@Column(name = "dt_movimentacao", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataMovimentacao;

	@Column(name = "vl_transacao", nullable = false)
	private float valorTransacao;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	private UsuarioFisico usuarioFisico;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresa", nullable = false)
	private UsuarioJuridico usuarioJuridico;
}
