
public class Usuario {

	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	private String nombreUsuario;
	private String contraseña;
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String apellidos, String telefono, String email, String nombreUsuario, String contraseña) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
	}
	
	public String GetNombre() {
		return this.nombre;
	}
	
	public String GetApellidos() {
		return this.apellidos;
	}
	
	public void SetTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String GetTelefono() {
		return this.telefono;
	}
	
	public String GetEmail() {
		return this.email;
	}
	
	public void SetNombreUsr(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String GetNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public void SetContraseña (String contraseña) {
		this.contraseña = contraseña;
	}
	
	public String GetContraseña() {
		return this.contraseña;
	}
}
