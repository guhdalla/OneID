package br.com.fiap.oneid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
@Table(name = "T_GNT_ROLE")
@SequenceGenerator(name = "role", sequenceName = "SQ_T_GNT_ROLE", allocationSize = 1)
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_role")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role")
	private Long id;
	
	@Column(name="nm_role")
	@NotBlank
	private String name;
	
	@Override
	public String getAuthority() {
		return null;
	}
}
