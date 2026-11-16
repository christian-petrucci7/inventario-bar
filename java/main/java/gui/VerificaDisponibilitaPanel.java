package gui;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.InventarioController;
import model.Inventario;
import model.Scorte;
import model.dettaglioInventario;

public class VerificaDisponibilitaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	public DefaultTableModel creaModelloTabella(List<dettaglioInventario> risultati) {
		
	    String[] colonne = { "ID Inventario", "ID Scorte", "Produttore", 
	                         "Tipo", "Quantità", "Scadenza", "Prezzo" };
	    
	    DefaultTableModel modello = new DefaultTableModel(colonne, 0);

	    for (dettaglioInventario d : risultati) {

	        Inventario i = d.getInventario();
	        Scorte s = d.getScorte(); 

	        Object[] riga = {
	            i.getId_inventario(),
	            s.getId_scorte(),
	            s.getNome_prodotto(),
	            s.getTipo_prodotto(),
	            i.getQuantita(),
	            i.getScadenza(),
	            s.getPrezzo_unitario()
	        };

	        modello.addRow(riga);
	    }

	    return modello;
	}
	
	public VerificaDisponibilitaPanel(MainFrame parent, InventarioController inventario) {
		
		setLayout(new BorderLayout(10, 10));
		
		// Pannello in cui aggiungerò il bottone, da inserire nella parte superiore
		
		JPanel panelBottone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		// Creazione del bottone per tornare indietro
		
		JButton bottoneIndietro = new JButton("<-");
		panelBottone.add(bottoneIndietro);
		
		bottoneIndietro.addActionListener(e -> {

		    parent.cardLayout.show(parent.cardPanel, "Principale");
			
		});
		
		// Pannello per effettuare la ricerca, da inserire nella parte superiore
		
		JPanel panelRicerca = new JPanel();
		panelRicerca.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		JLabel labelRicerca = new JLabel("Cerca prodotto:");
	    JTextField campoRicerca = new JTextField();
	    campoRicerca.setPreferredSize(new Dimension(200, 30));
	    JButton bottoneRicerca = new JButton("Cerca");
	    
	    panelRicerca.add(labelRicerca);
	    panelRicerca.add(campoRicerca);
	    panelRicerca.add(bottoneRicerca);
	    
	    // Pannello con tutta la parte superiore
	   
	    JPanel panelTop = new JPanel();
	    panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
	    panelTop.add(panelRicerca);
	    
	    // Messaggio con avvertimenti
	    
	    JLabel message = new JLabel("Scrivere i nomi con le opportune maiuscole e non usare spazi, "
	    		+ "per verificare l'intero inventario usare l'ID inventario");
	    
	    panelTop.add(message);
	    
	    // Pannello per i risultati della ricerca, che saranno visionati successivamente nella tabella
	    
	    JPanel panelRisultati = new JPanel();
	    
	    // Listener per bottone cerca
	    
	    bottoneRicerca.addActionListener(e -> {
	    	
	    	// Estraggo la stringa dal campo di ricerca
	    	
	    	String ricerca = campoRicerca.getText();
	    	
	    	// Aggiungo vincoli per campo vuoto e nel caso in cui ci siano spazi
	    	
	    	if(ricerca.isEmpty()) {
				
	    		JOptionPane.showMessageDialog(this, "Riempi il campo prima di effettuare una ricerca", "Errore", 
                		JOptionPane.ERROR_MESSAGE);
				
			} 
			
			else if(ricerca.contains(" ")) {
				
				JOptionPane.showMessageDialog(this, "Il campo non può contenere spazi", "Errore", 
                		JOptionPane.ERROR_MESSAGE);
				
			} 
	    	
	    	// Caso in cui l'utente vuole l'intero inventario
	    	
			else if(ricerca.equals("I001")) {
				
				try {
				
				// Creazione tabella 
					
				List<dettaglioInventario> risultatoInventario = inventario.controllerVerificaDisponibilitaScorte(ricerca);
				DefaultTableModel modello = creaModelloTabella(risultatoInventario);
			    JTable tabella = new JTable(modello);
	            JScrollPane scrollPane = new JScrollPane(tabella);
	            scrollPane.setPreferredSize(new Dimension(800, 400));
			        
		        panelRisultati.removeAll();		        
		        panelRisultati.add(scrollPane);
			    panelRisultati.revalidate();
			    panelRisultati.repaint();
				
				} catch (SQLException ex) {
					
					JOptionPane.showMessageDialog(parent, "Errore di connessione al database", "Errore", 
	                		JOptionPane.ERROR_MESSAGE);
	                ex.printStackTrace(); 
					
				}
				
				
			} else {
				
				try {
					
					// Caso in cui cercano un prodotto tramite tipo o nome
					
					List<dettaglioInventario> risultatoNome = inventario.controllerGetScorteByNome(ricerca);
					List<dettaglioInventario> risultatoTipo = inventario.controllerGetScorteByTipo(ricerca);

					List<dettaglioInventario> risultato = new ArrayList<>();
					risultato.addAll(risultatoNome);
					risultato.addAll(risultatoTipo);
					
					if (risultato.isEmpty()) {
						
						JOptionPane.showMessageDialog(this, "Nessun risultato trovato", "Errore", 
		                		JOptionPane.ERROR_MESSAGE);
				              
				    } else {
				    	
				        DefaultTableModel modello = creaModelloTabella(risultato);
				        JTable tabella = new JTable(modello);
				        JScrollPane scrollPane = new JScrollPane(tabella);
				        scrollPane.setPreferredSize(new Dimension(800, 400));
				        
				        panelRisultati.removeAll();
				        panelRisultati.add(scrollPane);
				        panelRisultati.revalidate();
				        panelRisultati.repaint();
				        
				    }
					
				} catch (SQLException ex) {
					
					JOptionPane.showMessageDialog(parent, "Errore di connessione al database", "Errore", 
	                		JOptionPane.ERROR_MESSAGE);
	                ex.printStackTrace(); 
					
				}
				
			}
	    	
	    });
		
		this.add(panelBottone, BorderLayout.PAGE_START);
	    this.add(panelTop, BorderLayout.CENTER);
	    this.add(panelRisultati, BorderLayout.SOUTH);
		
	}
	
	
}
