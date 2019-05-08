package manejoTransacciones;

import java.sql.Connection;
import java.sql.SQLException;

import datos.Conexion;
import datos.PersonasJDBC;

public class ManejoTransacciones {
	public static void main(String[] args) {
		PersonasJDBC personasAutoCommit = new PersonasJDBC();

		// Creamos un objeto conexion, se va a compartir
		// para todos los queries que ejecutemos
		Connection conexionSinAutoCommit = null;

		try {
			conexionSinAutoCommit = Conexion.getConnection();
			// Revisamos si la conexion esta en modo autocommit
			// por default es autocommit == true
			if (conexionSinAutoCommit.getAutoCommit()) {
				conexionSinAutoCommit.setAutoCommit(false);
			}
			// Creamos el objeto PersonasJDBC
			// proporcionamos la conexion creada
			PersonasJDBC personasRollBack = new PersonasJDBC(conexionSinAutoCommit);
			// empezamos a ejecutar sentencias
			// recordar que una transaccion agrupa varias
			// sentencias SQL
			// si algo falla no se realizan los cambios en
			// la BD
			// cambio correcto
			personasRollBack.cambiarNombres("nombre");
			personasAutoCommit.insert(0, "nombre", "apellido");
			// Provocamos un error
			personasRollBack.insert(1234123, "Miguel", "Ayala");
			// "Ayala2");
			// guardamos los cambios
			conexionSinAutoCommit.commit();
		} catch (SQLException e) {
			// Imprimimos la excepcion a la consola
			e.printStackTrace(System.out);
			try {
				System.out.println("Entramos al rollback");
				conexionSinAutoCommit.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
			}

		}
	}
}
