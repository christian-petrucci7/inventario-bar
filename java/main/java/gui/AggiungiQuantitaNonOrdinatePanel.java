package gui;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import controller.InventarioController;

public class AggiungiQuantitaNonOrdinatePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AggiungiQuantitaNonOrdinatePanel(MainFrame parent, InventarioController inventario) {
		
		setLayout(new BorderLayout(10, 10));
		
		 try {
		    	
		    	JPanel panelTop = new JPanel();
		    	panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
		    	
		    	// Estraiamo la lista dei nomi delle scorte dall'inventario
		    	
		    	List<String> lista_nomi_inventario = inventario.controllerGetListaNomiInInventario();
		    	
		    	// Aggiungo il menu a tendina che sarebbe JComboBox
		    	// Faccio la conversione in array perchè lista_nomi_inventario è una lista di stringhe, e JComboBox accetta solo array   
		    	
		    	JComboBox <String> comboBox = new JComboBox<>(lista_nomi_inventario.toArray(new String[0]));
		    	comboBox.setPreferredSize(new Dimension(250, 60));
		    	comboBox.setMaximumSize(new Dimension(250, 60));
		    	comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		    	
		    	// Label per il prodotto che sceglieremo
		    	
		    	JLabel prodotto = new JLabel("Prodotto");
		    	prodotto.setAlignmentX(Component.CENTER_ALIGNMENT);
		    	
		        panelTop.add(prodotto);
		        panelTop.add(comboBox);
		        
		        // Label e TextField per quantità
		        
		     	JLabel quantita = new JLabel("Quantità");
		        JTextField quantitaField = new JTextField();
		        quantitaField.setMaximumSize(new Dimension(200, 25));
		        quantitaField.setPreferredSize(new Dimension(200, 25));
		        quantitaField.setAlignmentX(Component.CENTER_ALIGNMENT);
		        quantita.setAlignmentX(Component.CENTER_ALIGNMENT);
		        
		        panelTop.add(quantita);
		        panelTop.add(quantitaField);
		        
		        // Bottone che aggiungerà quantità
		        
		        JButton buttonAggiunta = new JButton("AGGIUNGI");
		        buttonAggiunta.setAlignmentX(Component.CENTER_ALIGNMENT);
		        panelTop.add(buttonAggiunta);
			    
		        // Listener bottone
		        
		        buttonAggiunta.addActionListener(e ->{
		        	
		        	// Estraggo dal menu a tendina il prodotto selezionato
		        	
		        	String prodottoSelezionato = (String) comboBox.getSelectedItem();
		    		
		    		try {
		        		
		    			// Converto in int la quantita selezionata dal getText
		    			
		        	    int quantitaSelezionata = Integer.parseInt(quantitaField.getText());
		        	    
		        	    // Controllo per non passare un null
		        	    
		        	    if(prodottoSelezionato != null) {
		        			
		        			try {
		        				
		        				// Passo i parametri al controller che mi aggiunge il prodotto
		        				// Il controller mi da true se l'aggiunta è riuscita
		        				
		        				boolean risultato = inventario.controllerAggiungiQuantitaNONOrdinate(prodottoSelezionato, quantitaSelezionata);
		        				
		        				if(risultato) {
		        					
		        					JOptionPane.showMessageDialog(parent, "Aggiunta riuscita!");
		        					
		        				} else {
		        					
		        					JOptionPane.showMessageDialog(parent, "Controlla il numero inserito");
		        					
		        				}
		        							
		        				
		        			} catch (SQLException exc) {
		        				
		        				JOptionPane.showMessageDialog(parent, "Errore di connessione al database", "Errore", 
		        	            		JOptionPane.ERROR_MESSAGE);
		        	            exc.printStackTrace(); 
		        				
		        			}
		        	    
		        	    
		        	     } 
		        	    
		        	}
		    		catch (NumberFormatException num) {
		        		
		        	    JOptionPane.showMessageDialog(parent, "Inserisci una quantità numerica valida!");
		        	    
		        	}
		        	
		        });
		        
		        // Bottone per tornare indietro
			    
				JButton bottoneIndietro = new JButton("<-");
				bottoneIndietro.addActionListener(e -> {

					parent.cardLayout.show(parent.cardPanel, "Principale");
				    
				});
				
				JPanel bottonePanel = new JPanel (new FlowLayout(FlowLayout.LEFT));
				bottonePanel.add(bottoneIndietro);
				
				add(bottonePanel, BorderLayout.PAGE_START);
			    add(panelTop, BorderLayout.CENTER);
		    	
		    	
		    } catch (SQLException exc) {
		    	
		    	JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
	            		JOptionPane.ERROR_MESSAGE);
	            exc.printStackTrace(); 
		    	
		    }
		 
		
	}
	
}
