package DAOimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.AccountDAO;
import model.Account;
import model.Personale;

public class AccountDAOimp implements AccountDAO {
	
	Connection conn;

	public AccountDAOimp (Connection conn) {
		
		this.conn = conn;
		
	}
	
	public void createAccount ( String email, String password, String id_personale) throws SQLException {
		
		String sql = "INSERT INTO account (email, password, id_personale) VALUES (?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
		    pstmt.setString(1, email);     
			pstmt.setString(2, password);         
			pstmt.setString(3, id_personale);      
			
			pstmt.executeUpdate();	
			
		}
		
	}
	
	public Account getAccountByPassword(String password, String id_personale) throws SQLException {
	    
	    String sql = "SELECT * FROM account WHERE password = ? AND id_personale = ?";
	    Account account = null;

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, password);
	        pstmt.setString(2, id_personale);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	
	            PersonaleDAOimp personaleDAO = new PersonaleDAOimp(conn);
	            Personale personale = personaleDAO.getPersonaleByID(rs.getString("id_personale"));

	            
	            account = new Account(personale, rs.getString("email"), rs.getString("password"));
	            
	        }
	    }

	    return account;
	}
	
	public void updateAccountPassword ( String new_password, String ex_password ) throws SQLException {
		
        String sql = "UPDATE account SET password = ? WHERE password = ?";
		
		try(	  PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
		      pstmt.setString(1, new_password);       
			  pstmt.setString(2, ex_password);    
			
			  pstmt.executeUpdate();	
			    
		}
		
	}
	
	public void deleteAccountByPassword (String password ) throws SQLException {
		
        String sql = "DELETE from account WHERE password = ?";
		
		try(  PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			  pstmt.setString(1, password);    
			
			  pstmt.executeUpdate();	
			    
		}

		
	}
	
	
}
