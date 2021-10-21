package br.com.fiap.oneid.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Inheritance(strategy = InheritanceType.JOINED)

@Data
@Entity
@Table(name = "T_GNT_USUARIO")
@SequenceGenerator(name = "usuario", sequenceName = "SQ_T_GNT_USUARIO", allocationSize = 1)
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
	private Long idUsuario;

	@Column(name = "nm_primeiro_nome", length = 20, nullable = false)
	@NotBlank
	@Size(max = 20)
	private String primeiroNome;

	@Column(name = "nm_ultimo_nome", length = 50, nullable = false)
	@NotBlank
	@Size(max = 50)
	private String sobrenome;

	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "dt_nascimento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento;

	@Column(name = "ds_email", length = 50, nullable = false, unique = true)
	@Email
	@NotBlank
	@Size(max = 50)
	private String email;

	@Column(name = "ds_telefone", length = 14, nullable = false, unique = true)
	@Size(max = 14, min = 11)
	@NotBlank
	private String telefone;

	@Column(name = "ds_senha", nullable = false)
	@NotBlank
	@Size(min = 4)
	private String password;
	
	@Column(name = "ft_perfil")
	@Lob
	private byte[] fotoPerfil;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Tag> tag;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}

}