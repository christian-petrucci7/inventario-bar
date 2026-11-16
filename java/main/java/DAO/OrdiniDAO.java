package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.Fornitori;
import model.Ordini;
import model.Scorte;

public interface OrdiniDAO {

	public List <Ordini> createListOrdine ( String data_fornitura, List <Integer> quantita, List<Scorte> id_scorte, 
			List <Fornitori> id_fornitori, int totale_scorte) throws SQLException;
	
	public void getOrdineByData( String data_fornitura )  throws SQLException;
	
	public void deleteOrdineByData( Date data_fornitura )  throws SQLException;
	
	public Date getDataUltimoOrdine ()  throws SQLException ;
	
	public boolean getStoccaggio() throws SQLException;
	
	public void cambiaStatoStoccaggio() throws SQLException;
	
	public List<Ordini> getTabellaOrdini() throws SQLException;
	
}
