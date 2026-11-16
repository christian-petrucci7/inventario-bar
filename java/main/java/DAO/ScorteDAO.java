package DAO;

import java.sql.*;
import java.util.List;

import model.Scorte;

public interface ScorteDAO {

	public Scorte createScorte( String nome_prodotto, String tipo_prodotto, String id_scorte, 
			float prezzo_unitario ) throws SQLException;
	
	public Scorte getScorteByID ( String id_scorte ) throws SQLException;
	
	public void updateScortePrezzo ( String id_scorte, 
			float prezzo_unitario ) throws SQLException;
	
	public void deleteScorteByID ( String id_scorte ) throws SQLException;
	
	public int getTotaleScorteTrattate () throws SQLException;
	
	public List<String> getNomiScorteTrattate() throws SQLException;
	
	public List<Scorte> getAllScorte () throws SQLException;
	
}
