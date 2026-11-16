package DAO;

import java.sql.SQLException;
import java.util.List;

import model.Scorte;
import model.dettaglioInventario;

public interface InventarioDAO {

	public List <dettaglioInventario> verificaDisponibilita ( String id_inventario ) throws SQLException;
	
	public void updateQuantitaAggiunta ( int quantita_da_aggiungere, String id_scorte) throws SQLException;
	
	public void updateQuantitaRimozione ( int quantita_da_rimuovere, String nome_produttore) throws SQLException;
	
	public void addScorte ( String id_inventario, Scorte s, int quantita, 
			String scadenza) throws SQLException;
	
	
	public void deleteFromInventario( String id_scorte ) throws SQLException;
	
	public int getQuantitaByNome (String id_scorte) throws SQLException;
	
	public List <dettaglioInventario> getScorteByName (String nome_prodotto) throws SQLException;
	
	public List <dettaglioInventario> getScorteByTipo (String tipo_prodotto) throws SQLException;
	
	public List<String> getNomiProdotti () throws SQLException;
	
}
