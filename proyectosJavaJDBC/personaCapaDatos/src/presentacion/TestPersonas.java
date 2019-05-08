package presentacion;

import java.sql.SQLException;
import java.util.List;

import datos.PersonaDao;
import datos.PersonaDaoJDBC;
import personas.dto.PersonaDTO;

public class TestPersonas {

	public static void main(String[] args) {
	    PersonaDao personaDao = new PersonaDaoJDBC();
        PersonaDTO persona = new PersonaDTO();
        persona.setNombre("mario");
        persona.setApellido("lopez01");
        try {
            List<PersonaDTO> personas = personaDao.select();
            for (PersonaDTO personaDTO : personas) {
                System.out.print( personaDTO );
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Excepcion en la capa de prueba");
            e.printStackTrace();
        }
	}

}
