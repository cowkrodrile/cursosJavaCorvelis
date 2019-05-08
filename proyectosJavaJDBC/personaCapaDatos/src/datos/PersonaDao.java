package datos;

import java.sql.SQLException;
import java.util.List;

import personas.dto.PersonaDTO;

public interface PersonaDao {
	public int insert(PersonaDTO persona) throws SQLException;
	public int update(PersonaDTO persona) throws SQLException;
	public int delete(PersonaDTO persona) throws SQLException;
	public List<PersonaDTO> select() throws SQLException;
	public int cambiarNombres(PersonaDTO persona) ;
}
