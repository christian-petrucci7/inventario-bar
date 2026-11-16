package DAOimp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.PersonaleDAO;
import model.Personale;

public class PersonaleDAOimp implements PersonaleDAO {

	Connection conn;
	
	public PersonaleDAOimp(Connection conn) {
		
		this.conn = conn;
		
	}
	
	public void createPersonale ( String id_personale, String sesso, String codice_fiscale,
			String nome, String cognome, String responsabilita, 
			Date data_assunzione, String indirizzo, int civico, int CAP ) throws SQLException {
		
		String sql = "INSERT INTO personale (id_personale, sesso, codice_fiscale, "
				+ "nome, cognome, responsabilita, data_assunzione, indirizzo, civico "
				+ "CAP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setString(1, id_personale);     
			pstmt.setString(2, sesso);         
			pstmt.setString(3, codice_fiscale);   
			pstmt.setString(4, nome); 
			pstmt.setString(5, cognome);   
			pstmt.setString(6, responsabilita);
			pstmt.setDate(7, data_assunzione);   
			pstmt.setString(8, indirizzo);
			pstmt.setInt(9, civico);   
			pstmt.setInt(10, CAP);   
			
			pstmt.executeUpdate();	
			
		}
		
	}
	
	public Personale getPersonaleByID ( String id_personale ) throws SQLException {
		
        String sql = "SELECT * FROM personale WHERE id_personale = ?";
        
        Personale personale = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
			pstmt.setString(1, id_personale);   
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				personale = new Personale(rs.getString("id_personale"), rs.getString("sesso"), rs.getString("codice_fiscale"), 
						rs.getString("nome"), rs.getString("cognome"), rs.getString("responsabilita"), rs.getDate("data_assunzione"),
						 rs.getString("indirizzo"), rs.getInt("civico"), rs.getInt("CAP"));
				
			}
			
		}
		
		return personale;
		
	}
	
	public void deletePersonaleByID ( String id_personale ) throws SQLException {
		
        String sql = "DELETE from personale WHERE id_personale = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id_personale);    
			
			pstmt.executeUpdate();	
			    
		}

	}
	
	public boolean getResponsabilitaByID (String id_personale) throws SQLException {
		
		 String sql = "SELECT responsabilita FROM personale WHERE id_personale = ?";
			
	     try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    	 
	    	 pstmt.setString(1, id_personale);   
				
			 ResultSet rs = pstmt.executeQuery();
				
			 if(rs.next()) {
				 
				String responsabilita = rs.getString("responsabilita");
				 
				 return "Manager".equals(responsabilita);
					
				} else return false;
				
			}
		
	}
	
	
}
