package datos;

import java.sql.*;

public class Conexion {

	private static String JDBC_DRIVER = "org.postgresql.Driver";
	private static String JDBC_URL = "jdbc:postgresql://localhost:5432/prueba";
	private static String JDBC_USER = "postgres";
	private static String JDBC_PASS = "postgres";
	private static Driver driver = null;

	// Para que no haya problemas al obtener la conexion de
	// manera concurrente, se usa la palabra synchronized
	public static synchronized Connection getConnection() throws SQLException {
		if (driver == null) {
			try {
				// Se registra el driver
				Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
				driver = (Driver) jdbcDriverClass.newInstance();
				DriverManager.registerDriver(driver);
			} catch (Exception e) {
				System.out.println("Fallo en cargar el driver JDBC");
				e.printStackTrace();
			}
		}
		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	// Cierre del resultSet
	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	// Cierre del PrepareStatement
	public static void close(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	// Cierre de la conexion
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}