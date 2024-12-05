import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aplicacion {

	private static final String URL = "jdbc:mysql://localhost:3306/TenisUPM";
	private static final String USER = "root";
	private static final String PASSWORD = "2cebad49";
	private String admin;
	private String actUsr;
	private String vacio;
	
	public Aplicacion() {
		admin = "admin";
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

    // Metodo para crear un nuevo usuario
    public void crearUsuario(String nombre, String apellidos, String telefono, String email, String nombreUsuario, String pwd) {
        // Verifica si no hay sesion activa
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

                // Asigna los valores a la consulta de insercion
                statement.setString(1, nombre);
                statement.setString(2, apellidos);
                statement.setString(3, telefono);
                statement.setString(4, email);
                statement.setString(5, nombreUsuario);
                statement.setString(6, pwd);

                // Ejecuta la insercion
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
            System.err.println("Error: No se puede crear un usuario con una sesion iniciada.");
        }
    }
    
    public void Login(String nombreUsuario, String pwd) {
		if(actUsr.equals(vacio)) {
			
			if(nombreUsuario.equals(admin)) {
				if(pwd.equals("admin")) {
					this.actUsr = admin;
					System.out.println("Inicio de sesion exitoso. Usuario activo: " + actUsr);
					return;
				} else {
					System.err.println("Error: La contrasena es incorrecta.");
					return;
				}
			}
			String sql = "SELECT contrasena FROM Usuarios WHERE nombre_usuario = ?";
	        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            // Asigna el nombre de usuario al parimetro de la consulta
	            statement.setString(1, nombreUsuario);
	            ResultSet resultSet = statement.executeQuery();

	            // Comprueba si existe el usuario y obtiene la contrasena almacenada
	            if (resultSet.next()) {
	                String contrasenaAlmacenada = resultSet.getString("contrasena");

	                // Compara la contrasena almacenada con la proporcionada
	                if (contrasenaAlmacenada.equals(pwd)) {
	                    actUsr = nombreUsuario;  // Establece el usuario como activo
	                    System.out.println("Inicio de sesion exitoso. Usuario activo: " + actUsr);
	                } else {
	                    System.err.println("Error: La contrasena es incorrecta.");
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

	            // Asigna los parimetros a la consulta de actualizacion
	            statement.setString(1, nuevoNombreUsr);       // Nuevo nombre del usuario
	            statement.setString(2, actUsr);     // Confirma que es el usuario activo

	            // Ejecuta la actualizacion
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
    
    public void modificarTelefono(String nuevoTelefono) {
    	if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesion iniciada");
			return;
		} else {
			
			String sql = "UPDATE Usuarios SET telefono = ? WHERE nombre_usuario = ?";

	        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            // Asigna los parimetros a la consulta de actualizacion
	            statement.setString(1, nuevoTelefono);       // Nuevo nombre del usuario
	            statement.setString(2, actUsr);     // Confirma que es el usuario activo

	            // Ejecuta la actualizacion
	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Telefono cambiado exitosamente a: " + nuevoTelefono);
	            } else {
	                System.err.println("Error: No se pudo cambiar el telefono.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
    }
    
    public void modificarContrasena (String nuevaContrasena, String contrasenaActual) {
    	if(actUsr.equals(vacio)) {
			System.err.println("Error: No tienes una sesion iniciada");
			return;
		} else {
			String comprobarSql = "SELECT contrasena FROM Usuarios WHERE nombre_usuario = ?";
			String sql = "UPDATE Usuarios SET contrasena = ? WHERE nombre_usuario = ?";

	        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        		PreparedStatement statement1 = connection.prepareStatement(comprobarSql)) {

	            // Asigna el nombre de usuario al parimetro de la consulta
	            statement1.setString(1, actUsr);
	            ResultSet resultSet = statement1.executeQuery();

	            // Comprueba si existe el usuario y obtiene la contrasena almacenada
	            if (resultSet.next()) {
	                String contrasenaAlmacenada = resultSet.getString("contrasena");

	                // Compara la contrasena almacenada con la proporcionada
	                if (contrasenaAlmacenada.equals(contrasenaActual)) {
	                	if(nuevaContrasena.equals(contrasenaActual)) {
	        				System.err.println("Error: No puedes introducir la misma contrasena que la actual.");
	        				return;
	        			}
	                	PreparedStatement statement2 = connection.prepareStatement(sql); {

	        	            // Asigna los parimetros a la consulta de actualizacion
	        	            statement2.setString(1, nuevaContrasena);       // Nuevo nombre del usuario
	        	            statement2.setString(2, actUsr);     // Confirma que es el usuario activo

	        	            // Ejecuta la actualizacion
	        	            int rowsAffected = statement2.executeUpdate();
	        	            if (rowsAffected > 0) {
	        	                System.out.println("Contrasena cambiada exitosamente.");
	        	            } else {
	        	                System.err.println("Error: No se pudo cambiar la contrasena.");
	        	            }
	                	}
	                } else {
	                    System.err.println("Error: La contrasena antigua es incorrecta.");
	                }
	            } else {
	                System.err.println("Error: El nombre de usuario no existe.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
    }
}
