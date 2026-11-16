package DAOimp;

import DAO.ScorteDAO;
import model.Scorte;
import java.sql.*;
import java.util.*;

public class ScorteDAOimp implements ScorteDAO {

	Connection conn;
	
	public ScorteDAOimp(Connection conn) {
		
		this.conn = conn;
		
	}
	
	public Scorte createScorte( String nome_prodotto, String tipo_prodotto, String id_scorte, 
			float prezzo_unitario ) throws SQLException {
		
		String sql = "INSERT INTO scorte (id_scorte, nome_produttore, tipo_prodotto, "
				+ "prezzo_unitario) VALUES (?, ?, ?, ?)";
		
		Scorte scorta = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
		    pstmt.setString(1, id_scorte);     
			pstmt.setString(2, nome_prodotto);         
			pstmt.setString(3, tipo_prodotto);   
			pstmt.setFloat(4, prezzo_unitario);    
			
			pstmt.executeUpdate();	
			
			scorta = new Scorte(nome_prodotto, tipo_prodotto, id_scorte, prezzo_unitario);
			
			
		}
				
		return scorta;
	}
	
	public Scorte getScorteByID( String id_scorte ) throws SQLException {
		
		String sql = "SELECT * FROM scorte WHERE id_scorte = ?";

		Scorte scorta = null;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id_scorte);   
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				scorta = new Scorte(rs.getString("nome_produttore"), rs.getString("tipo_prodotto"), rs.getString("id_scorte"), 
						rs.getFloat("prezzo_unitario"));
				
			} 
			
		return scorta;
			
		}
	}
	
	public void updateScortePrezzo ( String id_scorte, 
			float prezzo_unitario ) throws SQLException {
		
		String sql = "UPDATE scorte SET prezzo_unitario = ? WHERE id_scorte = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
		
		      pstmt.setFloat(1, prezzo_unitario);       
			  pstmt.setString(2, id_scorte);    
			
			  pstmt.executeUpdate();	

		}	
		
	}
	
	public void deleteScorteByID ( String id_scorte ) throws SQLException {
		
		String sql = "DELETE from scorte WHERE id_scorte = ?";
		
		try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
			       
			  pstmt.setString(1, id_scorte);    
			
			  pstmt.executeUpdate();	
			  
		}
		
	}
	
	public int getTotaleScorteTrattate () throws SQLException {
		
		int totale_scorte = 0;
		
        String sql = "SELECT COUNT(*) AS totale_scorte FROM scorte";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				totale_scorte = rs.getInt("totale_scorte");
	
			}
			
			return totale_scorte;
			
		}
		
		
	}
	
	public List<String> getNomiScorteTrattate() throws SQLException {
		
		String sql = "SELECT nome_produttore FROM scorte ORDER BY CAST(RIGHT(id_scorte, 2) AS INTEGER)";
		
		List<String> NomiScorteList = new ArrayList<>();
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
	            String nome_prodotto = rs.getString("nome_produttore");
	            NomiScorteList.add(nome_prodotto);
	        }
			
			return NomiScorteList;
			
		}
		
		
	}
	
	
	/*public String getUltimoIdAggiungto () throws SQLException {
		
		
        String sql = "SELECT * FROM scorte ORDER BY CAST(RIGHT(id_scorte, 2) AS INTEGER) DESC LIMIT 1;";
	
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
	            return rs.getString("id_scorte");
	        }
			
			return null;
			
		}
		
	}*/
	
	public List<Scorte> getAllScorte() throws SQLException {

	    List<Scorte> listaScorte = new ArrayList<>();

	    String sql = "SELECT * FROM scorte ORDER BY CAST(RIGHT(id_scorte, 2) AS INTEGER)";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Scorte scorta = new Scorte(
	                rs.getString("tipo_prodotto"),
	                rs.getString("nome_produttore"),
	                rs.getString("id_scorte"),
	                rs.getFloat("prezzo_unitario")
	            );

	            listaScorte.add(scorta);
	        }
	    }

	    return listaScorte;
	}
	
	
}
