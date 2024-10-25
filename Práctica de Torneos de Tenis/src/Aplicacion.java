import java.util.HashMap;

public class Aplicacion {

	private HashMap<String,Usuario> usuarios = new HashMap<String,Usuario>();
	private Usuario admin;
	private Usuario actUsr;
	private Usuario vacio;
	
	public Aplicacion() {
		admin = new Usuario("admin", "admin", "admin", "admin", "admin", "admin");
		usuarios.put("admin", admin);
		vacio = new Usuario();
		actUsr = vacio;
	}
	
	public void crearUsuario(String nombre, String apellidos, String telefono, String email, String nombreUsuario, String contraseña) {
		if(actUsr.equals(vacio)) {
			if(usuarios.containsKey(nombreUsuario)) {
				System.err.println("Error: El nombre de usuario introducido ya existe, pruebe con uno distinto");
			}
			Usuario nuevoUsr = new Usuario(nombre, apellidos, telefono, email, nombreUsuario, contraseña);
			this.usuarios.put(nombreUsuario, nuevoUsr);
		} else {
			System.err.println("Error: No se puede crear un usuario con una sesión iniciada");
			return;
		}
	}
	
	public void Login(String nombreUsuario, String contraseña) {
		if(actUsr.equals(vacio)) {
			if (usuarios.containsKey(nombreUsuario)){
				if(usuarios.get(nombreUsuario).GetContraseña().equals(contraseña)) {
					this.actUsr = usuarios.get(nombreUsuario);
					System.out.println("Sesión iniciada correctamente");
				} else {
					System.err.println("Error: Contraseña errónea, inténtelo de nuevo");
					return;
				}
			} else {
				System.err.println("Error: No existe un usuario con dicho nombre de usuario, inténtelo de nuevo");
				return;
			}
		} else {
			System.err.println("Error: Ya tienes una sesión iniciada");
			return;
		}
	}
	
	public void Logout() {
		if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesión iniciada");
			return;
		} else {
			actUsr = vacio;
			System.out.println("Sesión cerrada con éxito");
		}
	}
	
	public void modificarNombreUsr(String nuevoNombreUsr) {
		if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesión iniciada");
			return;
		} else {
			if(this.usuarios.containsKey(nuevoNombreUsr)) {
				System.err.println("Error: El nombre de usuario introducido ya existe, pruebe con uno distinto");
				return;
			}
			this.usuarios.remove(actUsr.GetNombre());
			actUsr.SetNombreUsr(nuevoNombreUsr);
			this.usuarios.put(nuevoNombreUsr, actUsr);
			System.out.println("Nombre de usuario modificado correctamente");
		}
	}
}
