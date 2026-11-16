package gui;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import controller.InventarioController;

public class RimuoviQuantitaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public RimuoviQuantitaPanel(MainFrame parent, InventarioController inventario) {
		
		setLayout(new BorderLayout(10, 10));
		
		 try {
			 
			   JPanel panelTop = new JPanel();
		       panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
		       
		       // Prendo la lista dei nomi delle scorte presenti in inventario, dunque quelle pronte all'uso e rimuovibili
		    	
		       List<String> lista_nomi_inventario = inventario.controllerGetListaNomiInInventario();
		       
		       // Le inserisco nel menu a tendina che sarebbe JComboBox
		       
		       JComboBox<String> comboBox = new JComboBox<>(lista_nomi_inventario.toArray(new String[0])); 
		       
		       // Faccio la conversione in array perchè lista_nomi_inventario è una lista di stringhe, e JComboBox accetta solo array
		       
		       comboBox.setPreferredSize(new Dimension(250, 60));
		       comboBox.setMaximumSize(new Dimension(250, 60));
		       comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		       
		       // Inserisco la label per il prodotto da rimuovere
		    	
		       JLabel prodotto = new JLabel("Prodotto");
		       prodotto.setAlignmentX(Component.CENTER_ALIGNMENT);
		    	
		       panelTop.add(prodotto);
		       panelTop.add(comboBox);
		        
		       // Inserisco la label per la quantità da rimuovere
		       
		       JLabel quantita = new JLabel("Quantità");
		       JTextField quantitaField = new JTextField();
		       quantitaField.setMaximumSize(new Dimension(200, 25));
		       quantitaField.setPreferredSize(new Dimension(200, 25));
		       quantitaField.setAlignmentX(Component.CENTER_ALIGNMENT);
		       quantita.setAlignmentX(Component.CENTER_ALIGNMENT);
		        
		       panelTop.add(quantita);
		       panelTop.add(quantitaField);
		       
		       // Bottone per confermare la rimozione
		        
		       JButton buttonRimozione = new JButton("RIMUOVI");
		       buttonRimozione.setAlignmentX(Component.CENTER_ALIGNMENT);
		       panelTop.add(buttonRimozione);
		       
		       // Listener per bottone rimuovi
		       
		       buttonRimozione.addActionListener(e -> {
		    	   
		    	// Prendo il prodotto selezionato nel menu a tendina   
		    	   
		    	String prodottoSelezionato = (String) comboBox.getSelectedItem();
		   		
		   		try {
		       		
		   			// Siccome getText non può restituire un intero, uso parseInt per avere la quantita selezionata in int
		   			
		       	    int quantitaSelezionata = Integer.parseInt(quantitaField.getText());
		       	    
		       	    // Controllo che ci sia almeno un prodotto selezionato
		       	    
		       	    if(prodottoSelezionato != null) {
		       			
		       			try {
		       				
		       				// Uso il controller per rimuovere quantità, mi restituisce un boolean che detta il risultato dell'operazione
		       				
		       				boolean risultato = inventario.controllerRimuoviQuantita(quantitaSelezionata, prodottoSelezionato);
		       				
		       				if(risultato) {
		       					
		       					JOptionPane.showMessageDialog(parent, "Rimozione riuscita!");
		       					
		       				} else {
		       					
		       					JOptionPane.showMessageDialog(parent, "Scorte di questo prodotto finite");
		       					
		       				}
		       							
		       				
		       			} catch (SQLException exc) {
		       				
		       				JOptionPane.showMessageDialog(parent, "Errore di connessione al database", "Errore", 
		       	            		JOptionPane.ERROR_MESSAGE);
		       	            exc.printStackTrace(); 
		       				
		       			}
		       	    
		       	    
		       	     } 
		       	    
		       	}
		   		catch (NumberFormatException num) {
		       		
		       	    JOptionPane.showMessageDialog(parent, "Inserisci una quantità numerica valido!");
		       	    
		       	}
		    	   
		       });
			    
			   // Bottone per tornare indietro 
			   
			   JButton bottoneIndietro = new JButton("<-");
			   bottoneIndietro.addActionListener(e -> {

				   parent.cardLayout.show(parent.cardPanel, "Principale");
				    
				});
				
			   JPanel bottonePanel = new JPanel (new FlowLayout(FlowLayout.LEFT));
			   bottonePanel.add(bottoneIndietro);

		       this.add(bottonePanel, BorderLayout.NORTH);
		       this.add(panelTop, BorderLayout.CENTER);
		    	
		    	
		    } catch (SQLException exc) {
		    	
		    	JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
	            		JOptionPane.ERROR_MESSAGE);
	        exc.printStackTrace(); 
		    	
		}
		
	}

}
