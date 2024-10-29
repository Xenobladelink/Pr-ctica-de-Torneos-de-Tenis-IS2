import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
	final static String NULL_SENTINEL_VARCHAR = "NULL";
	final static int NULL_SENTINEL_INT = Integer.MIN_VALUE;
	final static java.sql.Date NULL_SENTINEL_DATE = java.sql.Date.valueOf("1900-01-01");
	
	private Connection conn = null;
	private String user;
	private String pass;
	private String url;
	
	public DBConnection(String server, int port, String user, String pass, String database){
		this.user = user;
		this.pass = pass;
		this.url = "jdbc:mysql://"+server+":"+port+"/"+database; //creamos la url de la base de datos
	}
	
	public boolean connect() {
		try {
			if(conn !=null && !conn.isClosed()) { //comprobamos si la conexion ya esta abierta
				return true;
			}
			else {
				conn = DriverManager.getConnection(url, user, pass); // realizamos la conexion
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn != null && !conn.isClosed()) { //comprobamos si la conexion se ha realizado exitosamente
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean close(){
		try {
			conn.close(); //cerramos la conexion
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int update(String sql) {
		Statement st = null;
		int res;
		try {
			st = conn.createStatement();
			res = st.executeUpdate(sql); //ejecutamos el comando SQL dado
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				st.close(); //cerramos el st
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		return res; //devolvemos el numero de filas afectadas
	}
	
	public int update(String sql, ArrayList<Object> a) {
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = conn.prepareStatement(sql);
			for(int i=0; i<a.size(); i++) {
				String classType = a.get(i).getClass().getName(); //comprobamos la clase del objeto dado en los parametros
				int tipo;
				Object n = null;
				if(classType.equals("java.lang.String")) { //si es un string
					tipo = Types.VARCHAR;
					n = NULL_SENTINEL_VARCHAR;
				}
				else if (classType.equals("java.lang.Integer")) { //si es un integer
					tipo = Types.INTEGER;
					n = NULL_SENTINEL_INT;
				}
				else { //si no es ninguno de los dos, en cuyo caso se trata de un date, puesto que no tenemos mas clases de parametros
					tipo = Types.DATE;
					n = NULL_SENTINEL_DATE;
				}
				if(a.get(i).equals(n)) { //si el objeto dado es null
					pst.setNull(i+1, tipo);
				}
				else {
					pst.setObject(i+1, a.get(i), tipo);
				}
			}
			res = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				pst.close(); //cerramos el pst
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		return res; //numero de filas afectadas
	}
	
	public ResultSet query(String sql) {
		Statement st;
		ResultSet rs;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql); //ejecutamos el comando de lectura
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rs;
	}
	
	public ResultSet query(String sql, ArrayList<Object> a) {
		PreparedStatement pst;
		ResultSet rs;
		try {
			pst = conn.prepareStatement(sql);
			for(int i=0; i<a.size(); i++) {
				String classType = a.get(i).getClass().getName(); //comprobamos la clase de los objetos dados en los parametros
				int tipo;
				Object n;
				if(classType.equals("java.lang.String")) { //si es un string
					tipo = Types.VARCHAR;
					n = NULL_SENTINEL_VARCHAR;
				}
				else if (classType.equals("java.lang.Integer")) { //si es un integer
					tipo = Types.INTEGER;
					n = NULL_SENTINEL_INT;
				}
				else { //si no es ninguno de los anteriores, en cuyo caso sera un date como se ha explicado anteriormente
					tipo = Types.DATE;
					n = NULL_SENTINEL_DATE;
				}
				if(a.get(i).equals(n)) { //si el objeto del parametro es null
					pst.setNull(i+1, tipo);
				}
				else {
					pst.setObject(i+1, a.get(i), tipo);
				}
			}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rs;
	}
	
	public boolean tableExists(String tableName) {
		ResultSet rs = this.query("SHOW TABLES"); //comprobamos por medio de este comando si existe la tabla
		boolean res = false;
		if(rs!=null) {
			try {
		          while (rs.next() && !res) {
		              String table = rs.getString(1);
		              res = table.equals(tableName);
		          }
		          rs.close();
		      } catch (SQLException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		      } finally {
					try {
					rs.close(); //cerramos el rs
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return res;
	}

}
