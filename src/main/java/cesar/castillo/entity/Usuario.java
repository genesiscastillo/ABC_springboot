package cesar.castillo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import cesar.castillo.vo.EstadoUsuario;

@Entity
@Table(name="TUSUARIO" ,schema="APP")
@NamedQuery(name="Usuario.findByEmail" , query ="SELECT t FROM Usuario t WHERE t.email = :email")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -4720934813401620215L;

	@Id
	@Column(name="EMAIL" , length=200)
	private String email;

	@Column(name="ID" , length=255)
	private String id;
	
	@Column(name="ESTADO_USUARIO" , length=10)
	@Enumerated(EnumType.STRING)
	private EstadoUsuario estadoUsuario;

	@Column(name="FECHA_CREACION" , length=10)
	private String fechaCreacion;

	@Column(name="FECHA_MODIFICACION" , length=10)
	private String fechaModificacion;

	@Column(name="LAST_LOGIN" , length=10)
	private String lastLogin;

	@Column(name="NAME" , length=100)
	private String name;

	@Column(name="PASSWORD" , length=50)
	private String password;

	@Column(name="TOKEN" , length=300)
	private String token;

	@PrePersist
	public void setearFechaCreacion() {
		LocalDate localDate = LocalDate.now();
		setFechaCreacion(localDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
	}

	@PreUpdate
	public void setearFechaModificacion() {
		LocalDate localDate = LocalDate.now();
		setFechaModificacion(localDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EstadoUsuario getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
