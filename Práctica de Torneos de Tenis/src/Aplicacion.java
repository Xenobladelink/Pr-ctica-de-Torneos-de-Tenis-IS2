import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.HashMap;

public class Aplicacion {

	private static final String URL = "jdbc:mysql://localhost:3306/TenisUPM";
	private static final String USER = "root";
	private static final String PASSWORD = "2cebad49";
//	private DBConnection conn = new DBConnection("localhost",3306,"root","","tenisupm");
//	private HashMap<String,Usuario> usuarios = new HashMap<String,Usuario>();
	private String admin;
	private String actUsr;
	private String vacio;
	
	public Aplicacion() {
		admin = "admin";
//		usuarios.put("admin", admin);
		vacio = "";
		actUsr = vacio;
	}
	
	public boolean existeUsuario(String nombreUsuario, String correo) {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE nombre_usuario = ? OR email = ?";
        boolean existe = false;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombreUsuario);
            statement.setString(2, correo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                existe = (count > 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    // M�todo para crear un nuevo usuario
    public void crearUsuario(String nombre, String apellidos, String telefono, String email, String nombreUsuario, String pwd) {
        // Verifica si no hay sesi�n activa
        if (actUsr.equals(vacio)) {

            // Comprueba si el usuario o el correo ya existen en la base de datos
            if (existeUsuario(nombreUsuario, email)) {
                System.err.println("Error: El nombre de usuario o el correo ya existen, pruebe con otros distintos.");
                return;
            }

            // Si no existe, inserta el nuevo usuario en la base de datos
            String sqlInsert = "INSERT INTO Usuarios (nombre, apellidos, telefono, email, nombre_usuario, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(sqlInsert)) {

                // Asigna los valores a la consulta de inserci�n
                statement.setString(1, nombre);
                statement.setString(2, apellidos);
                statement.setString(3, telefono);
                statement.setString(4, email);
                statement.setString(5, nombreUsuario);
                statement.setString(6, pwd);

                // Ejecuta la inserci�n
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Usuario creado exitosamente en la base de datos.");
                } else {
                    System.err.println("Error: No se pudo crear el usuario en la base de datos.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.err.println("Error: No se puede crear un usuario con una sesi�n iniciada.");
        }
    }
    
    public void Login(String nombreUsuario, String pwd) {
		if(actUsr.equals(vacio)) {
			
			if(nombreUsuario.equals(admin)) {
				if(pwd.equals("admin")) {
					this.actUsr = admin;
					System.out.println("Inicio de sesi�n exitoso. Usuario activo: " + actUsr);
					return;
				} else {
					System.err.println("Error: La contrase�a es incorrecta.");
					return;
				}
			}
			String sql = "SELECT contrasena FROM Usuarios WHERE nombre_usuario = ?";
	        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            // Asigna el nombre de usuario al par�metro de la consulta
	            statement.setString(1, nombreUsuario);
	            ResultSet resultSet = statement.executeQuery();

	            // Comprueba si existe el usuario y obtiene la contrase�a almacenada
	            if (resultSet.next()) {
	                String contrasenaAlmacenada = resultSet.getString("contrasena");

	                // Compara la contrasena almacenada con la proporcionada
	                if (contrasenaAlmacenada.equals(pwd)) {
	                    actUsr = nombreUsuario;  // Establece el usuario como activo
	                    System.out.println("Inicio de sesi�n exitoso. Usuario activo: " + actUsr);
	                } else {
	                    System.err.println("Error: La contrase�a es incorrecta.");
	                }
	            } else {
	                System.err.println("Error: El nombre de usuario no existe.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
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
			System.out.println("Sesion cerrada con exito");
		}
	}
    
    public void modificarNombreUsr(String nuevoNombreUsr) {
		if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesion iniciada");
			return;
		} else {
			if(existeUsuario(nuevoNombreUsr, "")) {
				System.err.println("Error: Ya existe un usuario con ese nombre de usuario.");
				return;
			}
			String sql = "UPDATE Usuarios SET nombre_usuario = ? WHERE nombre_usuario = ?";

	        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            // Asigna los par�metros a la consulta de actualizaci�n
	            statement.setString(1, nuevoNombreUsr);       // Nuevo nombre del usuario
	            statement.setString(2, actUsr);     // Confirma que es el usuario activo

	            // Ejecuta la actualizaci�n
	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Nombre del usuario cambiado exitosamente a: " + nuevoNombreUsr);
	                actUsr = nuevoNombreUsr;
	            } else {
	                System.err.println("Error: No se pudo cambiar el nombre del usuario.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
	}
	
//	public void crearUsuario(String nombre, String apellidos, String telefono, String email, String nombreUsuario, String pwd) {
//		if(!conn.connect()) {
//			System.err.println("Error: No se ha podido conectar con la base de datos");
//			return;
//		}
//		if(actUsr.equals(vacio)) {
//			if(usuarios.containsKey(nombreUsuario)) {
//				System.err.println("Error: El nombre de usuario introducido ya existe, pruebe con uno distinto");
//			}
//			Usuario nuevoUsr = new Usuario(nombre, apellidos, telefono, email, nombreUsuario, pwd);
//			this.usuarios.put(nombreUsuario, nuevoUsr);
//		} else {
//			System.err.println("Error: No se puede crear un usuario con una sesion iniciada");
//			return;
//		}
//		if(!conn.close()) {
//			System.err.println("Error: No se ha podido desconectar con la base de datos");
//			return;
//		}
//	}
//	
//	public void Login(String nombreUsuario, String pwd) {
//		if(actUsr.equals(vacio)) {
//			if (usuarios.containsKey(nombreUsuario)){
//				if(usuarios.get(nombreUsuario).GetPwd().equals(pwd)) {
//					this.actUsr = usuarios.get(nombreUsuario);
//					System.out.println("Sesion iniciada correctamente");
//				} else {
//					System.err.println("Error: Contrasena erronea, intentelo de nuevo");
//					return;
//				}
//			} else {
//				System.err.println("Error: No existe un usuario con dicho nombre de usuario, intentelo de nuevo");
//				return;
//			}
//		} else {
//			System.err.println("Error: Ya tienes una sesion iniciada");
//			return;
//		}
//	}
//	
//	public void Logout() {
//		if(actUsr.equals(vacio)) {
//			System.err.println("Error: No tienes una sesion iniciada");
//			return;
//		} else {
//			actUsr = vacio;
//			System.out.println("Sesion cerrada con exito");
//		}
//	}
//	
//	public void modificarNombreUsr(String nuevoNombreUsr) {
//		if(actUsr.equals(vacio)) {
//			System.err.println("Error: No tienes una sesion iniciada");
//			return;
//		} else {
//			if(this.usuarios.containsKey(nuevoNombreUsr)) {
//				System.err.println("Error: El nombre de usuario introducido ya existe, pruebe con uno distinto");
//				return;
//			}
//			this.usuarios.remove(actUsr.GetNombre());
//			actUsr.SetNombreUsr(nuevoNombreUsr);
//			this.usuarios.put(nuevoNombreUsr, actUsr);
//			System.out.println("Nombre de usuario modificado correctamente");
//		}
//	}
}
