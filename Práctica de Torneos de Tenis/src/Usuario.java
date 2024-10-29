import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario extends DBTable {

	private int id_usuario;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	private String nombreUsuario;
	private String pwd;
	
	public Usuario(int id_usuario, DBConnection conn, boolean DBSync) {
		super(conn,DBSync);
		this.id_usuario = id_usuario;
		this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
		this.apellidos = DBConnection.NULL_SENTINEL_VARCHAR;
		this.telefono = DBConnection.NULL_SENTINEL_VARCHAR;
		this.email = DBConnection.NULL_SENTINEL_VARCHAR;
		this.nombreUsuario = DBConnection.NULL_SENTINEL_VARCHAR;
		this.pwd = DBConnection.NULL_SENTINEL_VARCHAR;
		if(!conn.connect()) {
			this.id_usuario = DBConnection.NULL_SENTINEL_INT;
			super.setSync(false);
		}
		if(DBSync) { //si esta sincronizado, creamos la tabla si no existe e insertamos los valores, si no se puede cerramos la sincronizacion y ponemos los valores a null
			if(!conn.tableExists("Usuarios")) {
				this.createTable();
			}
			if(!this.insertEntry()) {
				this.id_usuario = DBConnection.NULL_SENTINEL_INT;
				super.setSync(false);
			}
		}
	}
	
	public Usuario(int id_usuario, DBConnection conn, boolean DBSync, String nombre, String apellidos, String telefono,
			String email, String nombreUsuario, String pwd) {
		super(conn,DBSync);
		this.id_usuario = id_usuario;
		if(nombre == null) {
			this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
		} else {
			this.nombre = nombre;
		}
		if(apellidos == null) {
			this.apellidos = DBConnection.NULL_SENTINEL_VARCHAR;
		} else {
			this.apellidos = apellidos;
		}
		if(telefono == null) {
			this.telefono = DBConnection.NULL_SENTINEL_VARCHAR;
		} else {
			this.telefono = telefono;
		}
		if(email == null) {
			this.email = DBConnection.NULL_SENTINEL_VARCHAR;
		} else {
			this.email = email;
		}
		if(nombreUsuario == null) {
			this.nombreUsuario = DBConnection.NULL_SENTINEL_VARCHAR;
		} else {
			this.nombreUsuario = nombreUsuario;
		}
		if(pwd == null) {
			this.pwd = DBConnection.NULL_SENTINEL_VARCHAR;
		} else {
			this.pwd = pwd;
		}
		if(!conn.connect()) {
			this.id_usuario = DBConnection.NULL_SENTINEL_INT;
			super.setSync(false);
		}
		if(DBSync) { //si esta sincronizado, creamos la tabla si no existe e insertamos los valores, si no se puede cerramos la sincronizacion y ponemos los valores a null
			if(!conn.tableExists("Usuarios")) {
				this.createTable();
			}
			if(!this.insertEntry()) {
				this.id_usuario = DBConnection.NULL_SENTINEL_INT;
				super.setSync(false);
			}
		}
	}
	
	public String GetNombre() {
		if(DBSync) {
			getEntryChanges();
		}
		return this.nombre;
	}
	
	public String GetApellidos() {
		if(DBSync) {
			getEntryChanges();
		}
		return this.apellidos;
	}
	
	public void SetTelefono(String telefono) {
		if(DBSync) {
			getEntryChanges();
		}
		this.telefono = telefono;
		if(DBSync) {
			updateEntry();
		}
	}
	
	public String GetTelefono() {
		if(DBSync) {
			getEntryChanges();
		}
		return this.telefono;
	}
	
	public String GetEmail() {
		if(DBSync) {
			getEntryChanges();
		}
		return this.email;
	}
	
	public void SetNombreUsr(String nombreUsuario) {
		if(DBSync) {
			getEntryChanges();
		}
		this.nombreUsuario = nombreUsuario;
		if(DBSync) {
			updateEntry();
		}
	}
	
	public String GetNombreUsuario() {
		if(DBSync) {
			getEntryChanges();
		}
		return this.nombreUsuario;
	}
	
	public void SetPwd (String pwd) {
		if(DBSync) {
			getEntryChanges();
		}
		this.pwd = pwd;
		if(DBSync) {
			updateEntry();
		}
	}
	
	public String GetPwd() {
		if(DBSync) {
			getEntryChanges();
		}
		return this.pwd;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if(DBSync) {
			deleteEntry();
			this.id_usuario = DBConnection.NULL_SENTINEL_INT;
			this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
			this.apellidos = DBConnection.NULL_SENTINEL_VARCHAR;
			this.telefono = DBConnection.NULL_SENTINEL_VARCHAR;
			this.email = DBConnection.NULL_SENTINEL_VARCHAR;
			this.nombreUsuario = DBConnection.NULL_SENTINEL_VARCHAR;
			this.pwd = DBConnection.NULL_SENTINEL_VARCHAR;
			super.setSync(false);
		}
	}

	@Override
	boolean createTable() {
		// TODO Auto-generated method stub
		if(conn.tableExists("Usuarios")) {
			return false;
		}
		int aux = conn.update("CREATE TABLE Usuarios ("
			    +"id_usuario INT PRIMARY KEY AUTO_INCREMENT,"
			    +"nombre VARCHAR(50),"
			    +"apellidos VARCHAR(50),"
			    +"telefono VARCHAR(15),"
			    +"email VARCHAR(100) UNIQUE,"
			    +"nombre_usuario VARCHAR(50) UNIQUE,"
			    +"contrasena VARCHAR(255)"
			    +");");
		return aux!=-1;
	}

	@Override
	boolean insertEntry() {
		// TODO Auto-generated method stub
		ArrayList<Object> a = new ArrayList<Object>();
		a.add(0,this.id_usuario);
		a.add(1,this.nombre);
		a.add(2,this.apellidos);
		a.add(3,this.telefono);
		a.add(4,this.email);
		a.add(5,this.nombreUsuario);
		a.add(6,this.pwd);
		int aux = conn.update("INSERT INTO Usuarios VALUES (?,?,?,?,?,?,?);", a);
		return aux!=-1;
	}

	@Override
	boolean updateEntry() {
		// TODO Auto-generated method stub
		ArrayList<Object> a = new ArrayList<Object>();
		a.add(0,this.nombre);
		a.add(1,this.apellidos);
		a.add(2,this.telefono);
		a.add(3,this.email);
		a.add(4,this.nombreUsuario);
		a.add(5,this.pwd);
		a.add(6,this.id_usuario);
		int aux = conn.update("UPDATE Usuarios "
				+ "SET nombre = ? , apellidos = ? , telefono = ? , email = ?, nombre_usuario = ?, contrasena = ?"
				+ " WHERE id_usuario = ?;",a);
		return aux!=-1;
	}

	@Override
	boolean deleteEntry() {
		// TODO Auto-generated method stub
		int aux;
		ArrayList<Object> a = new ArrayList<Object>();
		a.add(0,this.id_usuario);
		aux = conn.update("DELETE FROM Usuarios WHERE id_usuario = ? ;",a);
		return aux!=-1;
	}

	@Override
	void getEntryChanges() {
		// TODO Auto-generated method stub
		ArrayList<Object> a = new ArrayList<Object>();
		a.add(0,this.id_usuario);
		ResultSet rs = conn.query("SELECT * FROM Usuarios WHERE id_usuario = ? ;",a);
		try {
			while (rs.next()) {
				this.nombre = rs.getString(2);
				this.apellidos = rs.getString(3);
				this.telefono = rs.getString(4);
				this.email = rs.getString(5);
				this.nombreUsuario = rs.getString(6);
				this.pwd = rs.getString(7);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close(); //cerramos el rs usado
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
