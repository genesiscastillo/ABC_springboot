package cesar.castillo.vo;

public class ResponseUsuario extends User{

	private String id;
	private String fechaCreacion;
	private String fechaModificacion;
	private String lastLogin;
	private String token;
	private EstadoUsuario estadoUsuario;
	
	
	public ResponseUsuario(  
			String name,String email,String password,
			String id, String fechaCreacion, String fechaModificacion, String lastLogin, String token,
			EstadoUsuario estadoUsuario) {
		super();
		this.setName(name);
		this.setEmail(email);
		this.setPassword(password);
		this.id = id;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.lastLogin = lastLogin;
		this.token = token;
		this.estadoUsuario = estadoUsuario;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public EstadoUsuario getEstadoUsuario() {
		return estadoUsuario;
	}
	public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	@Override
	public String toString() {
		return "ResponseUsuario [id=" + id + ", email=" + this.getEmail() + ", token=" + token + ", estadoUsuario="
				+ estadoUsuario + "]";
	}
	
}
