package gui;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import controller.*;

public class LoginFrame extends JFrame {

	LoginController login;
	InventarioController inventario;
	private static final long serialVersionUID = 1L;

	public LoginFrame(LoginController login, InventarioController inventario) {


		this.login = login;
		this.inventario = inventario;
		
		setTitle("Log-In inventario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		
		// Creazione di un messaggio vuoto da riempire in caso di errore
		
		JLabel messaggio = new JLabel(" ");
		messaggio.setForeground(Color.RED);
		messaggio.setPreferredSize(new Dimension(300, 40));
		
		// ID personale
		
		JLabel idLabel = new JLabel("ID personale");
		JTextField idText = new JTextField();
		idText.setPreferredSize(new Dimension(200, 30));
		
        // email
		
		JLabel emailLabel = new JLabel("Email");
		JTextField emailText = new JTextField();
		emailText.setPreferredSize(new Dimension(200, 30));
		
		// password
		
		JLabel passwordLabel = new JLabel("Password");
		JPasswordField passwordText = new JPasswordField();
		passwordText.setPreferredSize(new Dimension(200, 30));
		
		//bottone
		
		JButton bottoneInvia = new JButton("INVIA");
        bottoneInvia.setPreferredSize(new Dimension(200, 30));
		
		// Vincoli del layout GridBag
        
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 15, 10);
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(idLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(idText, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 15, 10);
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(emailLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(emailText, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 15, 10);
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(passwordLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(passwordText, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridwidth = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(bottoneInvia, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridwidth = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		add(messaggio, gbc);
		
		//creazione bottone listener 
	
		
		bottoneInvia.addActionListener( e -> {
			
			// Converto in stringhe tutti i campi compilati dall'utente
			
			String password = new String(passwordText.getPassword());   
			String id = idText.getText();
			String email = emailText.getText();
			
			// Controllo che tutti i campi siano stati riempiti
			
			if (id.isEmpty() || email.isEmpty() || password.isEmpty()) {
				
			    messaggio.setText("Tutti i campi sono obbligatori");
			    
			} else {
				
				try {
					
					// Controllo le credenziali e faccio una distinzione tra Manager e Impiegato
					
					boolean esistente = login.controllerLogin(password, id);
					
					if ( esistente && login.isManager(id) ) {
						
		                // Caso in cui è un manager
						
						dispose();
						
						SwingUtilities.invokeLater(() -> {
	
							boolean manager = true;
				            MainFrame mainFrame = new MainFrame(inventario, manager);
				            mainFrame.setVisible(true);
				            
				        });
						
		            } 
					else if( esistente && !login.isManager(id) ) {
						
						// Caso in cui è un impiegato
						
                        dispose();
						
						SwingUtilities.invokeLater(() -> {
	
							boolean manager = false;
				            MainFrame mainFrame = new MainFrame(inventario, manager);
				            mainFrame.setVisible(true);
				            
				        });
					}
					
					else {
		                messaggio.setText("Errore: Controlla che tutti i campi siano corretti");
		            } 
				} catch (SQLException ex) {
		 
		                JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
		                		JOptionPane.ERROR_MESSAGE);
		                ex.printStackTrace(); 
		            }
				
			}
			
		});
		
		
	}
	
}
