package main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import controller.*;
import dbConnection.DBConnection;
import gui.*;

public class Principale {

	public static void main(String[] args) {
		
       DBConnection.inizializzaDatabase();
	   Connection conn;
		
		try {
			
			  conn = DBConnection.connectToProjectDB();
			  InventarioController inventario = new InventarioController(conn);
		      LoginController login = new LoginController(conn);
					
			  //mostra la gui per scegliere l'user
		      
			  SwingUtilities.invokeLater(() -> {
				  
				  LoginFrame frame = new LoginFrame(login, inventario);
			      frame.setVisible(true);
			      
			  }); 
					
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			} 
		
		System.out.println("Current directory: " + System.getProperty("user.dir"));

	}
}
