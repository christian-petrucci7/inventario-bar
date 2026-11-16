package DAOimp;

import java.sql.*;
import java.util.*;

import DAO.FornitoriDAO;
import model.Fornitori;

public class FornitoriDAOimp implements FornitoriDAO{
	
	Connection conn;
	
	public FornitoriDAOimp(Connection conn) {
		
		this.conn = conn;
		
	}

	public void createFornitori ( String nome_fornitore, String id_fornitore, 
			String ubicazione_azienda) throws SQLException {
		
		String sql = "INSERT INTO fornitori (nome_fornitore, id_fornitore, ubicazione_azienda) "
				+ "VALUES (?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
		    pstmt.setString(1, nome_fornitore);     
			pstmt.setString(2, id_fornitore);         
			pstmt.setString(3, ubicazione_azienda);    
			
			pstmt.executeUpdate();	
			
			
		}
		
	}
	
	public void getFornitoriByID ( String id_fornitore ) throws SQLException {
		
        String sql = "SELECT * FROM fornitori WHERE id_fornitore = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id_fornitore);   
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				System.out.println( rs.getString("nome_fornitore") + " - " + rs.getString("id_fornitore") + " - " + 
				rs.getString("ubicazione_azienda") );
			}
			
			
		}
		
	}
	
	public void deleteFornitoriByID ( String id_fornitore ) throws SQLException {
		
        String sql = "DELETE from fornitori WHERE id_fornitore = ?";
		
		try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
			       
			  pstmt.setString(1, id_fornitore);    
			
			  pstmt.executeUpdate();	
			    
			  
		}
		
	}
	
	public List<Fornitori> getAllFornitori() throws SQLException {

	    List<Fornitori> listaFornitori = new ArrayList<>();

	    String sql = "SELECT * FROM fornitori ORDER BY CAST(RIGHT(id_fornitore, 2) AS INTEGER)";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Fornitori fornitori = new Fornitori(
	                rs.getString("nome_fornitore"),
	                rs.getString("id_fornitore"),
	                rs.getString("ubicazione_azienda")
	                );

	            listaFornitori.add(fornitori);
	        }
	    }

	    return listaFornitori;
	}
	
	
}
