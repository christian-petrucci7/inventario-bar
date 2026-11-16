package DAO;

import java.sql.Date;
import java.sql.SQLException;

import model.Personale;

public interface PersonaleDAO {

	public void createPersonale ( String id_personale, String sesso, String codice_fiscale,
			String nome, String cognome, String responsabilita, 
			Date data_assunzione, String indirizzo, int civico, int CAP ) throws SQLException;
	
	public Personale getPersonaleByID ( String id_personale ) throws SQLException;
	
	public void deletePersonaleByID ( String id_personale ) throws SQLException;
	
}
