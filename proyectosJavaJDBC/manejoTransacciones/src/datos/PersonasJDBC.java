package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Persona;

public class PersonasJDBC {
	// Variable que almacena una conexion como referencia
	// esta opcion se recibe en el constructor de esta clase
	// y permite reutilizar la misma conexion para ejecutar
	// varios queries de esta clase, opcionalmente se puede
	// utilizar para el uso de una transaccion en SQL
	private java.sql.Connection userConn;

	private final String SQL_INSERT = "INSERT INTO persona(id_persona,nombre, apellido_paterno) VALUES(?,?,?)";

	private final String SQL_UPDATE = "UPDATE persona SET nombre=?, apellido_paterno=? WHERE id_persona=?";

	private final String SQL_DELETE = "DELETE FROM persona WHERE id_persona = ?";

	private final String SQL_SELECT = "SELECT id_persona, nombre, apellido_paterno FROM persona ORDER BY id_persona";

	private final String SQL_CAMBIAR_NOMBRES = "UPDATE persona SET nombre=?";

	/*
	 * Agregamos el constructor vacio
	 */
	public PersonasJDBC() {
	}

	public PersonasJDBC(Connection conn) {
		this.userConn = conn;
	}

	/**
	 * Metodo que inserta un registro en la tabla de Persona
	 *
	 * @param nombre
	 * @param apellido
	 */
	public int insert(int idPersona, String nombre, String apellido) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;// no se utiliza en este ejercicio
		int rows = 0; // registros afectados
		try {
			conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			int index = 1;// contador de columnas
			stmt.setInt(index++, idPersona);// param 1 => ?
			stmt.setString(index++, nombre);// param 1 => ?
			stmt.setString(index++, apellido);// param 2 => ?
			System.out.println("Ejecutando query:" + SQL_INSERT);
			rows = stmt.executeUpdate();// no. registros afectados
			System.out.println("Registros afectados:" + rows);

		} catch (SQLException e) {
			throw e;
		} finally {
			Conexion.close(stmt);
			// Unicamente cerramos la conexi�n si fue creada en este metodo
			if (this.userConn == null) {
				Conexion.close(conn);
			}
		}
		return rows;
	}

	/**
	 * Metodo que actualiza un registro existente
	 *
	 * @param id_persona Es la llave primaria
	 * @param nombre     Nuevo Valor
	 * @param apellido   Nuevo Valor
	 * @return int No. de registros modificados
	 */
	public int update(int id_persona, String nombre, String apellido) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
			System.out.println("Ejecutando query:" + SQL_UPDATE);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setString(index++, nombre);
			stmt.setString(index++, apellido);
			stmt.setInt(index, id_persona);
			rows = stmt.executeUpdate();
			System.out.println("Registros actualizados:" + rows);
		} catch (SQLException e) {
			throw e;
		} finally {
			Conexion.close(stmt);
			if (this.userConn == null) {
				Conexion.close(conn);
			}
		}
		return rows;
	}

	/**
	 * Metodo que elimina un registro existente
	 *
	 * @param id_persona Es la llave primaria
	 * @return int No. registros afectados
	 */
	public int delete(int id_persona) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
			System.out.println("Ejecutando query:" + SQL_DELETE);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, id_persona);
			rows = stmt.executeUpdate();
			System.out.println("Registros eliminados:" + rows);
		} catch (SQLException e) {
			throw e;
		} finally {
			Conexion.close(stmt);
			if (this.userConn == null) {
				Conexion.close(conn);
			}
		}
		return rows;
	}

	/**
	 * Metodo que regresa el contenido de la tabla de personas
	 */
	public List<Persona> select() throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Persona persona = null;
		List<Persona> personas = new ArrayList<Persona>();
		try {
			conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id_persona = rs.getInt(1);
				String nombre = rs.getString(2);
				String apellido = rs.getString(3);
				/*
				 * System.out.print(" " + id_persona); System.out.print(" " + nombre);
				 * System.out.print(" " + apellido); System.out.println();
				 */
				persona = new Persona();
				persona.setIdPersona(id_persona);
				persona.setNombre(nombre);
				persona.setApellido(apellido);
				personas.add(persona);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			Conexion.close(rs);
			Conexion.close(stmt);
			if (this.userConn == null) {
				Conexion.close(conn);
			}
		}
		return personas;
	}

	public int cambiarNombres(String nombre) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
			System.out.println("Ejecutando query:" + SQL_CAMBIAR_NOMBRES);
			stmt = conn.prepareStatement(SQL_CAMBIAR_NOMBRES);
			int index = 1;
			stmt.setString(index, nombre);
			rows = stmt.executeUpdate();
			System.out.println("Registros actualizados:" + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.close(stmt);
			if (this.userConn == null) {
				Conexion.close(conn);
			}
		}
		return rows;
	}
}
