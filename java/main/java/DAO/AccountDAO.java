package DAO;

import java.sql.SQLException;

import model.Account;

public interface AccountDAO {

	public void createAccount ( String email, String password, String id_personale) throws SQLException;
	
	public Account getAccountByPassword ( String password, String id_personale ) throws SQLException;
	
	public void updateAccountPassword ( String ex_password, String new_password ) throws SQLException;
	
	public void deleteAccountByPassword ( String password) throws SQLException;
	
}
