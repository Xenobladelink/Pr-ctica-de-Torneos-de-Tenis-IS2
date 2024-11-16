
public class Pruebas {

	public static void main(String[] args) {
		Aplicacion app = new Aplicacion();
		
		System.out.println("/nPruebas de Crear Usuario");
		app.crearUsuario("Pedro", "Garcia", "827398749", "pedro@gmail.com", "pedrogarcia", "contrasena");
		app.crearUsuario("Juan", "Garcia", "827398749", "pedro@gmail.com", "juangarcia", "contrasena");
		app.crearUsuario("Pepe", "Garcia", "827398749", "pedro@gmail.com", "pedrogarcia", "contrasena");
		
		System.out.println("/nPruebas de Login y Logout");
		app.Login("admin", "admin");
		app.Logout();
		app.Login("pedrogarcia", "contrasena");
		app.crearUsuario("Pepe", "Garcia", "827398749", "pedro@gmail.com", "gracia", "contrasena");
		app.Login("juangarcia", "contrasena");
		app.Logout();
		app.Login("juangarcia", "nocontrasena");
		app.Logout();
		app.Login("pepegarcia", "contrasena");
		
		System.out.println("/nPruebas de Modificar Nombre Usuario");
		app.Login("pedrogarcia", "contrasena");
		app.modificarNombreUsr("garcia");
		app.modificarNombreUsr("juangarcia");
		app.Logout();
		app.modificarNombreUsr("garcia");
		
		
		
	}
}
