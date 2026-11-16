package DAO;

import java.sql.SQLException;
import java.util.List;

import model.Fornitori;

public interface FornitoriDAO {

	public void createFornitori ( String nome_fornitore, String id_fornitore, 
			String ubicazione_azienda) throws SQLException;
	
	public void getFornitoriByID (String id_fornitori) throws SQLException;
	
	public void deleteFornitoriByID ( String id_fornitore) throws SQLException;
	
	public List<Fornitori> getAllFornitori() throws SQLException;
	
}
