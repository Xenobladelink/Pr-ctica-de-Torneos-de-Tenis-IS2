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
	
	public void crearUsuario(String nombre, String apellidos, String telefono, String email, String nombreUsuario, String pwd) {
		if(actUsr.equals(vacio)) {
			if(usuarios.containsKey(nombreUsuario)) {
				System.err.println("Error: El nombre de usuario introducido ya existe, pruebe con uno distinto");
			}
			Usuario nuevoUsr = new Usuario(nombre, apellidos, telefono, email, nombreUsuario, pwd);
			this.usuarios.put(nombreUsuario, nuevoUsr);
		} else {
			System.err.println("Error: No se puede crear un usuario con una sesion iniciada");
			return;
		}
	}
	
	public void Login(String nombreUsuario, String pwd) {
		if(actUsr.equals(vacio)) {
			if (usuarios.containsKey(nombreUsuario)){
				if(usuarios.get(nombreUsuario).GetPwd().equals(pwd)) {
					this.actUsr = usuarios.get(nombreUsuario);
					System.out.println("Sesion iniciada correctamente");
				} else {
					System.err.println("Error: Contrasena erronea, intentelo de nuevo");
					return;
				}
			} else {
				System.err.println("Error: No existe un usuario con dicho nombre de usuario, intentelo de nuevo");
				return;
			}
		} else {
			System.err.println("Error: Ya tienes una sesion iniciada");
			return;
		}
	}
	
	public void Logout() {
		if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesion iniciada");
			return;
		} else {
			actUsr = vacio;
			System.out.println("Sesión cerrada con exito");
		}
	}
	
	public void modificarNombreUsr(String nuevoNombreUsr) {
		if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesion iniciada");
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
