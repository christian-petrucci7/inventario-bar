package controller;
import java.sql.*;

import DAOimp.AccountDAOimp;
import DAOimp.PersonaleDAOimp;
import model.Account;


public class LoginController {
	
	AccountDAOimp accountDAOimp;
	PersonaleDAOimp personaleDAOimp;
	
	public LoginController(Connection conn){
		
		this.accountDAOimp = new AccountDAOimp(conn);
		this.personaleDAOimp = new PersonaleDAOimp(conn);
		
	}

	public boolean controllerLogin ( String password, String id_personale ) throws SQLException {
		
		Account account = accountDAOimp.getAccountByPassword( password, id_personale );
		
		if(account == null) {
			
			return false;
			
		} else return true;
		
	}
	
	public boolean isManager ( String id_personale ) throws SQLException {
		
		if (personaleDAOimp.getResponsabilitaByID(id_personale)) {
			
			return true;
			
		} else { 
		
			return false;
			
		}
		
	}
	
}
