package gui;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import controller.*;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
    CardLayout cardLayout;
    JPanel cardPanel;
    
    // Unico panel come variabile d'istanza della classe perchè và aggiornato interamente 
    // ogni volta che viene chiamato, non essendoci tabelle non ho potuto creare 
    // semplicemente un metodo aggiornaTabella
    
    NuovoOrdinePanel ordinePanel;

	public MainFrame(InventarioController inventario, boolean manager) {
		
		if(manager) {
			
			setTitle("Inventario");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1000, 600);
	        setLocationRelativeTo(null);
	      
	        // Creazione del panel in cui andremo ad inserire la schermata principale con tutte le funzionalità
	        
	        JPanel principale = new JPanel();
	        principale.setLayout(new GridLayout(3, 2, 65, 65));
	        principale.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
	        
	        // Queste sono le funzionalità disponibili per un manager
	        
	        JButton verifica = new JButton ("Verifica Disponibilità");
	        JButton aggiuntaScorte = new JButton ("Aggiungi scorte MAI trattate");
	        JButton aggiuntaQuantitaOrdinata = new JButton ("Aggiungi quantità ordinata");
	        JButton rimozione = new JButton("Rimuovi quantità");
	        JButton nuovoOrdine = new JButton("Nuovo ordine");
	        JButton aggiuntaNonOrdinata = new JButton("Aggiungi quantità NON ordinate");
	        
	        principale.add(verifica);
	        principale.add(aggiuntaScorte);
	        principale.add(aggiuntaQuantitaOrdinata);
	        principale.add(rimozione);
	        principale.add(nuovoOrdine);
	        principale.add(aggiuntaNonOrdinata);
	        
	        // adessiamo creiamo il panel con il cardLayout per spostarci tra i vari pannelli
	        
	        cardLayout = new CardLayout();
	        cardPanel = new JPanel(cardLayout);
	        
	        VerificaDisponibilitaPanel verificaPanel = new VerificaDisponibilitaPanel(this, inventario);
	        AggiuntaScorteMaiTrattatePanel aggiuntaMaiPanel = new AggiuntaScorteMaiTrattatePanel(this, inventario);
	        ordinePanel = new NuovoOrdinePanel(this, inventario);
	        SpostamentoOrdiniPanel spostaPanel = new SpostamentoOrdiniPanel(this, inventario);
	        RimuoviQuantitaPanel rimuoviPanel = new RimuoviQuantitaPanel(this, inventario);
	        AggiungiQuantitaNonOrdinatePanel nonOrdinatePanel = new  AggiungiQuantitaNonOrdinatePanel(this, inventario);
	        
	        cardPanel.add(principale, "Principale");
	        cardPanel.add(verificaPanel, "Verifica");
	        cardPanel.add(aggiuntaMaiPanel, "AggiuntaMai"); // Con AggiuntaMai intendiamo l'aggiunta di scorte mai trattate
	        cardPanel.add(ordinePanel, "NuovoOrdine");
	        cardPanel.add(spostaPanel, "Sposta");
	        cardPanel.add(rimuoviPanel, "Rimuovi");
	        cardPanel.add(nonOrdinatePanel, "NonOrdinate");
	        
	        // I vari Listener per i bottoni
	        
	        verifica.addActionListener( e -> {
	        	
	        	cardLayout.show(cardPanel, "Verifica");
	        	
	        });	   
	        aggiuntaScorte.addActionListener(e -> {
	        	
	        	cardLayout.show(cardPanel, "AggiuntaMai");
	        	
	        });
	        nuovoOrdine.addActionListener(e -> {
	        	
	            cardPanel.remove(ordinePanel); // rimuove il vecchio pannello
	            ordinePanel = new NuovoOrdinePanel(this, inventario); // nuova istanza pulita
	            cardPanel.add(ordinePanel, "NuovoOrdine");
	            cardLayout.show(cardPanel, "NuovoOrdine");
	        	
	        });
	        aggiuntaQuantitaOrdinata.addActionListener(e -> {
	        	
	        	spostaPanel.aggiornaTabella(inventario); 
	        	cardLayout.show(cardPanel, "Sposta");
	        	
	        });
	        rimozione.addActionListener(e -> {
	        	
	        	cardLayout.show(cardPanel, "Rimuovi");
	        	
	        });
	        aggiuntaNonOrdinata.addActionListener(e ->{
	        	
	        	cardLayout.show(cardPanel, "NonOrdinate");
	        	
	        });
	        
	        add(cardPanel);
	        
	        SwingUtilities.invokeLater(() -> {
	        	
	            try {
	                if (inventario.allarmeScorte()) {
	                    JOptionPane.showMessageDialog(this,
	                        "Attenzione: Il totale della quantità è sotto la soglia consigliata",
	                        "Errore",
	                        JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (SQLException exc) {
	                JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore",
	                    JOptionPane.ERROR_MESSAGE);
	                exc.printStackTrace();
	                
	            }
	        });
	        
	        
		}
		
		else if(!manager) {
			
			setTitle("Inventario");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1000, 600);
	        setLocationRelativeTo(null);
	        
	     // Funzionalità disponibili per Impiegato
	        
	        JPanel principale = new JPanel();
	        principale.setLayout(new GridLayout(1, 2, 65, 65));
	        principale.setBorder(BorderFactory.createEmptyBorder(90, 90, 90, 90));
	        
	        JButton verifica = new JButton ("Verifica Disponibilità");
	        JButton rimozione = new JButton("Rimuovi quantità");
	        
	        principale.add(verifica);
	        principale.add(rimozione);
	        
	        cardLayout = new CardLayout();
	        cardPanel = new JPanel(cardLayout);
	        
	        VerificaDisponibilitaPanel verificaPanel = new VerificaDisponibilitaPanel(this, inventario);
	        RimuoviQuantitaPanel rimuoviPanel = new RimuoviQuantitaPanel(this, inventario);
	        
	        cardPanel.add(principale, "Principale");
	        cardPanel.add(verificaPanel, "Verifica");
	        cardPanel.add(rimuoviPanel, "Rimuovi");
	        
            verifica.addActionListener( e -> {
	        	
	        	cardLayout.show(cardPanel, "Verifica");
	        	
	        });	
            rimozione.addActionListener(e -> {
	        	
	        	cardLayout.show(cardPanel, "Rimuovi");
	        	
	        });
            
            add(cardPanel);
            
            SwingUtilities.invokeLater(() -> {
	        	
	            try {
	                if (inventario.allarmeScorte()) {
	                    JOptionPane.showMessageDialog(this,
	                        "Attenzione: Il totale della quantità è sotto la soglia consigliata",
	                        "Errore",
	                        JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (SQLException exc) {
	                JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore",
	                    JOptionPane.ERROR_MESSAGE);
	                exc.printStackTrace();
	                
	            }
	        });
            
		}
        
        
	}
	
}
