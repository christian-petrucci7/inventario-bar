package gui;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import controller.InventarioController;

public class NuovoOrdinePanel extends JPanel {
	
	int currentIndex = 0, totale_scorte = 0;
	List<String> list_nomiScorte = null;

	private static final long serialVersionUID = 1L;

	public NuovoOrdinePanel(MainFrame parent, InventarioController inventario) {
		
		setLayout( new BorderLayout());
		
	    // Creiamo una lista per le future quantità che aggiungeremo
		
	    List<Integer> quantitaInserite = new ArrayList<>();
		
	    // Estraggo dal DBMS la lista dei nomi delle scorte, ricordiamo che possiamo ordinare solo scorte che trattiamo
	    
		try {
			
            list_nomiScorte = inventario.controllerGetNomiScorteTrattate();
            
        } catch(SQLException exc) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
                    JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace(); 
        }
		
		// Creo il pannello dove inseriremo tutto
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        if (list_nomiScorte == null || list_nomiScorte.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lista scorte vuota!", "Errore", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        // Label per il nome della scorta che cambierà ogni volta che l'indice aumenta, così da mostrare tutti i prodotti ordinabili
        
        JLabel nome_prodotto = new JLabel(list_nomiScorte.get(currentIndex));
        nome_prodotto.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo per inserire la quantità
        
        JTextField quantitaField = new JTextField();
        quantitaField.setMaximumSize(new Dimension(200, 25));
        quantitaField.setPreferredSize(new Dimension(200, 25));
        quantitaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Bottone per andare alla prossima scorta

        JButton aggiungi = new JButton("AGGIUNGI");
        aggiungi.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel quantitaInserimento = new JLabel("Quantita:");
        quantitaInserimento.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Aggiungo tutti questi componenti al mainPanel
        
        mainPanel.add(nome_prodotto);
        mainPanel.add(quantitaInserimento);
        mainPanel.add(quantitaField);
        mainPanel.add(aggiungi);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10))); 

        // Listener per il bottone aggiungi
        
        aggiungi.addActionListener(e -> {
        	
            String input = quantitaField.getText();
            
            // Controllo che la quantità non sia vuota
            
            if (input.isEmpty()) {
            	
                JOptionPane.showMessageDialog(null, "Inserisci una quantità!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
            	
            	// Converto in intero la quantita siccome ho preso l'input in String
            	
                int quantita = Integer.parseInt(input);
                
                // Aggiungo la quantità alla lista che contiene per ogni scorta la quantità ordinata
                
                quantitaInserite.add(quantita);

                // E aggiorno l'indice
                
                currentIndex++;
                
                // Se l'indice supera la quantità di scorte presenti si ferma e mi permette di selezionare la data
                
                if (currentIndex >= list_nomiScorte.size()) {
                	
                	JTextField aggiungiData = new JTextField();
                    JButton inviaData = new JButton("IMPOSTA DATA");
                    aggiungiData.setMaximumSize(new Dimension(200, 25));
                    aggiungiData.setPreferredSize(new Dimension(200, 25));
                    aggiungiData.setAlignmentX(Component.CENTER_ALIGNMENT);
                    inviaData.setAlignmentX(Component.CENTER_ALIGNMENT);
                    mainPanel.add(aggiungiData);
                    mainPanel.add(inviaData);
                    
                    // Listener per l'invio della data, che detta la fine del nuovo ordine
                	
                    inviaData.addActionListener(e2 -> {
                    	
                        String data = aggiungiData.getText();
                        
                        if (data != null && !data.isEmpty()) {
                            
                        	try {
                        		
                        		inventario.controllerNuovoOrdine("I001", data, quantitaInserite);
                        		JOptionPane.showMessageDialog(this, "Fornitura effettuata!");
                        		
                        		
                        	} catch (SQLException exc) {
                        		
                        		JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
                                        JOptionPane.ERROR_MESSAGE);
                                exc.printStackTrace(); 
                        		
                        	}
                        	
                        } else {
                        	
                            JOptionPane.showMessageDialog(this, "Data vuota, non salvata.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                            
                        }
                    });
                	
                    JOptionPane.showMessageDialog(null, "Inserimento completato!\nQuantità: " + quantitaInserite, "Fine", JOptionPane.INFORMATION_MESSAGE);
                    aggiungi.setEnabled(false);
                    quantitaField.setEnabled(false);
                    nome_prodotto.setText("Fine ordine");
                    
                } else {
                    nome_prodotto.setText(list_nomiScorte.get(currentIndex));
                    quantitaField.setText("");
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Inserisci un numero valido!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Bottone per tornare indietro
        
        JButton bottoneIndietro = new JButton("<-");
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indietroPanel.add(bottoneIndietro);
		bottoneIndietro.addActionListener(e -> {

			parent.cardLayout.show(parent.cardPanel, "Principale");
		    
		});
        
        JPanel centratoPanel = new JPanel(new GridBagLayout());
        centratoPanel.add(mainPanel);

        this.add(indietroPanel, BorderLayout.NORTH);
        this.add(centratoPanel, BorderLayout.CENTER);
  
    }  
	
}
