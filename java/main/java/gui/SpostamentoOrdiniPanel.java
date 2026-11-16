package gui;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import controller.InventarioController;
import model.Fornitori;
import model.Ordini;
import model.Scorte;

public class SpostamentoOrdiniPanel extends JPanel{

	  private static final long serialVersionUID = 1L;
	  //InventarioController inventario;
	  JPanel ordiniPanel;
	  
	  // Modello per la tabella dell'ordine effettuato

	  public DefaultTableModel creaModelloTabella(List<Ordini> ordini) {

		    String[] colonne = {
		        "ID Ordine", "ID Scorte", "ID Fornitore",
		        "Data Fornitura", "Quantità", "Stoccaggio", "Prezzo Totale"
		    };

		    DefaultTableModel modello = new DefaultTableModel(colonne, 0);

		    for (Ordini o : ordini) {

		        Scorte s = o.getScorte();
		        Fornitori f = o.getFornitori();

		        Object[] riga = {
		            o.getId_ordine(),
		            s.getId_scorte(),          
		            f.getId_fornitore(),       
		            o.getData_fornitura(),
		            o.getQuantita(),
		            o.isStoccaggio(),
		            o.getPrezzo_totale()
		        };

		        modello.addRow(riga);
		    }

		    return modello;
		}
	  
	  public void aggiornaTabella (InventarioController inventario) {
		  
		  ordiniPanel.removeAll();
		  
		    try {
		        List<Ordini> list_ordini = inventario.controllerGetTabellaOrdini();
		        DefaultTableModel modello = creaModelloTabella(list_ordini);
		        JTable tabella = new JTable(modello);
		        JScrollPane scrollPane = new JScrollPane(tabella);
		        scrollPane.setPreferredSize(new Dimension(800, 400));
		        
		        ordiniPanel.add(scrollPane);
		      
		    } catch (SQLException exc) {
		        JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
		            JOptionPane.ERROR_MESSAGE);
		        exc.printStackTrace(); 
		    }
		    
		    ordiniPanel.revalidate();
	        ordiniPanel.repaint();
		    
		}
	  
	  public SpostamentoOrdiniPanel(MainFrame parent, InventarioController inventario) {
		  
		  //this.inventario = inventario;
		  
		  setLayout(new BorderLayout(10, 10));
		  
		  // Bottone per tornare indeitro
		  
		  JPanel bottonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		  JButton bottoneIndietro = new JButton("<-");
			bottoneIndietro.addActionListener(e -> {

				parent.cardLayout.show(parent.cardPanel, "Principale");
			    
			});
		  bottonePanel.add(bottoneIndietro);
			
		  // Pannello in cui aggiungo un messaggio e un bottone
			
		  JPanel topPanel = new JPanel(); 
		  topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); 
		  
		  // Messaggio
		  
		  JLabel messaggio = new JLabel("Questo è il tuo ultimo ordine,"
		  		+ " per spostarlo nel tuo inventario premi SPOSTA");
		  messaggio.setAlignmentX(Component.CENTER_ALIGNMENT);
		  messaggio.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); 
		  topPanel.add(messaggio);
		  
		  // Bottone che sposta la quantità ordinata dall panel "Nuovo Ordine" nell'inventario
		  
		  JButton spostamento = new JButton("SPOSTA");
		  spostamento.setAlignmentX(Component.CENTER_ALIGNMENT);
		  topPanel.add(spostamento);
		  
         // Pannello in cui mostreremo la tabella degli ordini con tutte le quantità e i prezzi totali
		  
		  ordiniPanel = new JPanel();
		    
		    try {
		    	
		    	List<Ordini> list_ordini = inventario.controllerGetTabellaOrdini();
		    	DefaultTableModel modello = creaModelloTabella(list_ordini);
			    JTable tabella = new JTable(modello);
	            JScrollPane scrollPane = new JScrollPane(tabella);
	            scrollPane.setPreferredSize(new Dimension(800, 400));
	            
	            ordiniPanel.removeAll();		        
		        ordiniPanel.add(scrollPane);
		        ordiniPanel.revalidate();
		        ordiniPanel.repaint();
		    	
		    } catch (SQLException exc) {
		    	
		    	JOptionPane.showMessageDialog(this, "Errore di connessione al database", "Errore", 
	            		JOptionPane.ERROR_MESSAGE);
	            exc.printStackTrace(); 
		    	
		    }
		  
		  // Listener per il bottone SPOSTA
		  
		  spostamento.addActionListener(e -> {
			  
			  try {
				  
				    // Uso l'inventario per spostare la quantità nell'inventario, il metodo restituisce un boolean che se è true
				    // significa che l'ordine è stato spostato
				    // N.B: Quando il bottone verrà ripremuto, verrà nuovamente chiamato il metodo del controller, che però restituirà false
				    // siccome il controller, prima di fare la query SQL, controlla dalla tabella se l'ordine è stato stocccato
					
					boolean risultato = inventario.controllerAggiungiQuantitaOrdinate("I001");
					
					// Controllo per non stoccare lo stesso ordine due volte
					
					if(risultato) {
						
						JOptionPane.showMessageDialog(parent, "Spostamento riuscito!");
						
						// Ricrea il modello per cambiare il valore del boolean stoccaggio, che questa volta sarà true
						// siccome l'ordine è stato spostato
						
						List<Ordini> list_ordini = inventario.controllerGetTabellaOrdini();
				    	DefaultTableModel modello = creaModelloTabella(list_ordini);
					    JTable tabella = new JTable(modello);
			            JScrollPane scrollPane = new JScrollPane(tabella);
			            scrollPane.setPreferredSize(new Dimension(800, 400));
			            
			            ordiniPanel.removeAll();		        
				        ordiniPanel.add(scrollPane);
				        ordiniPanel.revalidate();
				        ordiniPanel.repaint();
						
					} else {
						
						JOptionPane.showMessageDialog(parent, "Ordine già stoccato, se ti serve altro, fanne uno nuovo");
						
					}
					
					
					
				} catch (SQLException exc) {
					
					JOptionPane.showMessageDialog(parent, "Spostamento non riuscito, errore di connessione al database", "Errore", 
		            		JOptionPane.ERROR_MESSAGE);
		            exc.printStackTrace(); 
					
				}
			  
		  });
		  
		  add(bottonePanel, BorderLayout.PAGE_START);
		  add(topPanel, BorderLayout.CENTER);
		  add(ordiniPanel, BorderLayout.SOUTH);
		  
	  }
	  
	
}
