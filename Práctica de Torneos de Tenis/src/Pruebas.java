public class Pruebas {

	public static void main(String[] args) {
		Aplicacion app = new Aplicacion();
		
		// Pruebas Ciclo 1
		System.out.println("Pruebas de Crear Usuario");
		app.crearUsuario("Pedro", "Garcia", "827398749", "pedro@gmail.com", "pedrogarcia", "contrasena");
		app.crearUsuario("Juan", "Garcia", "827398749", "juan@gmail.com", "juangarcia", "contrasena");
		app.crearUsuario("Pepe", "Garcia", "827398749", "pepe@gmail.com", "pedrogarcia", "contrasena");
		app.crearUsuario("Pepe", "Garcia", "827398749", "pedro@gmail.com", "pepegarcia", "contrasena");
		
		System.out.println("\nPruebas de Login y Logout");
		app.Login("admin", "admin");
		app.Logout();
		app.Login("pedrogarcia", "contrasena");
		app.crearUsuario("Pepe", "Garcia", "827398749", "pedro@gmail.com", "gracia", "contrasena");
		app.Login("juangarcia", "contrasena");
		app.Logout();
		app.Login("juangarcia", "nocontrasena");
		app.Logout();
		app.Login("pepegarcia", "contrasena");
		
		System.out.println("\nPruebas de Modificar Nombre Usuario");
		app.Login("pedrogarcia", "contrasena");
		app.modificarNombreUsr("garcia");
		app.modificarNombreUsr("juangarcia");
		app.Logout();
		app.modificarNombreUsr("garcia");
		
		// Pruebas Ciclo 2
		
		System.out.println("\nPruebas de Modificar Telefono");
		app.Login("pedrogarcia", "contrasena");
		app.modificarTelefono("625347546");
		app.Logout();
		app.modificarNombreUsr("698751456");
		
		System.out.println("\nPruebas de Modificar Contrasena");
		app.Login("pedrogarcia", "contrasena");
		app.modificarContrasena("contrasena", "contrasena");
		app.modificarContrasena("algo", "erronea");
		app.modificarContrasena("algo", "contrasena");
		app.Logout();
		app.Login("pedrogarcia", "contrasena");
		app.Login("pedrogarcia", "algo");
		app.Logout();
		
	}
}
