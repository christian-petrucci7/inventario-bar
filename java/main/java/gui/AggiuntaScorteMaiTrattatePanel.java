package gui;

import java.awt.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import controller.InventarioController;

public class AggiuntaScorteMaiTrattatePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private float prezzo_user = 0;
	private int quantita_user = 0;
	
    private boolean validaIdScorte(String idScorte) {
		
		String regex = "^SCORTE\\d{4}$"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idScorte);
		return matcher.matches();

		
	}
	
	public  AggiuntaScorteMaiTrattatePanel (MainFrame parent, InventarioController inventario) {
		
		setLayout(new BorderLayout());
		
		// pannello dove inseriremo ogni sotto-pannello che conterrà tutti i dettagli per aggiungere scorte mai trattate
		
        JPanel panelDivisioneGriglia= new JPanel (new GridLayout(4, 2, 5, 5));
	    
        // ID Inventario
        
	    JPanel inventarioPanel = new JPanel();
	    inventarioPanel.setLayout(new BoxLayout(inventarioPanel, BoxLayout.Y_AXIS));
	    JLabel idInventario = new JLabel("ID inventario");
	    JTextField idFieldInv = new JTextField();
	    idFieldInv.setMaximumSize(new Dimension(200, 25)); 
	    idFieldInv.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel = new JLabel("Ricorda: l'ID inventario è composto da 4 caratteri");
	    sottoLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    idInventario.setAlignmentX(Component.CENTER_ALIGNMENT);
	    idFieldInv.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    inventarioPanel.add(idInventario);
	    inventarioPanel.add(idFieldInv);
	    inventarioPanel.add(sottoLabel);
	    
	    // Nome prodotto
	    
	    JPanel nomePanel = new JPanel();
	    nomePanel.setLayout(new BoxLayout(nomePanel, BoxLayout.Y_AXIS));
	    JLabel nome_prodotto = new JLabel("Nome prodotto");
	    JTextField nameField = new JTextField();
	    nameField.setMaximumSize(new Dimension(200, 25)); 
	    nameField.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel2 = new JLabel("Ricorda: rispetta le maiuscole e non usare spazi");
	    sottoLabel2.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    nome_prodotto.setAlignmentX(Component.CENTER_ALIGNMENT);
	    nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
	    nomePanel.add(nome_prodotto);
	    nomePanel.add(nameField);
	    nomePanel.add(sottoLabel2);
	    
	    // Tipo prodotto
	    
	    JPanel tipoPanel = new JPanel();
	    tipoPanel.setLayout(new BoxLayout(tipoPanel, BoxLayout.Y_AXIS));
	    JLabel tipo_prodotto = new JLabel("Tipo prodotto");
	    JTextField tipoField = new JTextField();
	    tipoField.setMaximumSize(new Dimension(200, 25)); 
	    tipoField.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel3 = new JLabel("Ricorda: rispetta le maiuscole e non usare spazi");
	    sottoLabel3.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    tipo_prodotto.setAlignmentX(Component.CENTER_ALIGNMENT);
	    tipoField.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
	    tipoPanel.add(tipo_prodotto);
	    tipoPanel.add(tipoField);
	    tipoPanel.add(sottoLabel3);
	    
	    // ID Scorte 
	    
	    JPanel scortePanel = new JPanel();
	    scortePanel.setLayout(new BoxLayout(scortePanel, BoxLayout.Y_AXIS));
	    JLabel idScorte = new JLabel("ID scorte");
	    JTextField idFieldSco = new JTextField();
	    idFieldSco.setMaximumSize(new Dimension(200, 25)); 
	    idFieldSco.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel4 = new JLabel("Ricorda: l'ID scorte è composto dal suffisso SCORTE + 4 numeri.");
	    JLabel sottoLabel8 = new JLabel("Ovviamente, non usare ID già esistenti");
	    sottoLabel4.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    idScorte.setAlignmentX(Component.CENTER_ALIGNMENT);
	    idFieldSco.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel8.setAlignmentX(Component.CENTER_ALIGNMENT);
	    scortePanel.add(idScorte);
	    scortePanel.add(idFieldSco);
	    scortePanel.add(sottoLabel4);
	    scortePanel.add(sottoLabel8);
	    
	    // Prezzo

	    JPanel prezzoPanel = new JPanel();
	    prezzoPanel.setLayout(new BoxLayout(prezzoPanel, BoxLayout.Y_AXIS));
	    JLabel prezzo = new JLabel("Prezzo");
	    JTextField prezzoField = new JTextField();
	    prezzoField.setMaximumSize(new Dimension(200, 25)); 
	    prezzoField.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel5 = new JLabel("Ricorda: inserire € con il punto al posto della virgola in caso di cent.");
	    sottoLabel5.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    prezzo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    prezzoField.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
	    prezzoPanel.add(prezzo);
	    prezzoPanel.add(prezzoField);
	    prezzoPanel.add(sottoLabel5);

	    // Quantità
	    
	    JPanel quantitaPanel = new JPanel();
	    quantitaPanel.setLayout(new BoxLayout(quantitaPanel, BoxLayout.Y_AXIS));
	    JLabel quantita = new JLabel("quantita");
	    JTextField quantitaField = new JTextField();
	    quantitaField.setMaximumSize(new Dimension(200, 25)); 
	    quantitaField.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel6 = new JLabel("Ricorda: inserire un intero");
	    sottoLabel6.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    quantita.setAlignmentX(Component.CENTER_ALIGNMENT);
	    quantitaField.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel6.setAlignmentX(Component.CENTER_ALIGNMENT);
	    quantitaPanel.add(quantita);
	    quantitaPanel.add(quantitaField);
	    quantitaPanel.add(sottoLabel6);
	    
	    // Scadenza
	    
	    JPanel scadenzaPanel = new JPanel();
	    scadenzaPanel.setLayout(new BoxLayout(scadenzaPanel, BoxLayout.Y_AXIS));
	    JLabel scadenza = new JLabel("scadenza");
	    JTextField scadenzaField = new JTextField();
	    scadenzaField.setMaximumSize(new Dimension(200, 25)); 
	    scadenzaField.setPreferredSize(new Dimension(200, 25));
	    JLabel sottoLabel7 = new JLabel("Ricorda: inserire una data in questo formato YYYY-MM-DD");
	    sottoLabel7.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); 
	    scadenza.setAlignmentX(Component.CENTER_ALIGNMENT);
	    scadenzaField.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sottoLabel7.setAlignmentX(Component.CENTER_ALIGNMENT);
	    scadenzaPanel.add(scadenza);
	    scadenzaPanel.add(scadenzaField);
	    scadenzaPanel.add(sottoLabel7);
	    
	    // Bottone
	    
	    JPanel bottoneInviaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    JButton bottoneInvia = new JButton("INVIA");
	    bottoneInviaPanel.add(bottoneInvia);
	    
	    // Aggiunta di ogni sotto-pannello al pannello che divide il tutto in griglia
	    
	    panelDivisioneGriglia.add(inventarioPanel);
	    panelDivisioneGriglia.add(nomePanel);
	    panelDivisioneGriglia.add(tipoPanel);
	    panelDivisioneGriglia.add(scortePanel);
	    panelDivisioneGriglia.add(prezzoPanel);
	    panelDivisioneGriglia.add(quantitaPanel);
	    panelDivisioneGriglia.add(scadenzaPanel);
	    panelDivisioneGriglia.add(bottoneInviaPanel);
	    
	    // Bottone per tornare indietro
	    
	    JButton bottoneIndietro = new JButton("<-");
		bottoneIndietro.addActionListener(e -> {

			parent.cardLayout.show(parent.cardPanel, "Principale");
		    
		});
		
		JPanel bottonePanel = new JPanel (new FlowLayout(FlowLayout.LEFT));
		
		bottonePanel.add(bottoneIndietro);
		
		// Listener per bottone invia
		
		bottoneInvia.addActionListener(e -> {
			
			// Converto a stringhe i campi ID Inventario, Nome prodotto, Tipo prodotto e ID Scorte
			
            String[] parametri = new String[4];
	    	
	    	parametri[0] = idFieldInv.getText();
	    	parametri[1] = nameField.getText();
	    	parametri[2] = tipoField.getText();
	    	parametri[3] = idFieldSco.getText();
	    	
	    	// Qui faccio prima il getText del campo prezzo e quantità e poi li converto da stringa a intero e float
			
            try {
	    		
	    	   prezzo_user = Float.parseFloat(prezzoField.getText());
	    	    
	    	} catch (NumberFormatException num) {
	    		
	    	    JOptionPane.showMessageDialog(this, "Inserisci un prezzo valido!");
	    	    
	    	}
            try {
	    		
	    	    quantita_user = Integer.parseInt(quantitaField.getText());
	    	    
	    	} catch (NumberFormatException num) {
	    		
	    	    JOptionPane.showMessageDialog(this, "Inserisci una quantità numerica valida!");
	    	    
	    	}
            
            String date = scadenzaField.getText().trim();
            
            // Controlli per non lasciare nessun campo vuoto e per rispettare i vari ID
            
            if(parametri[0].isEmpty() || parametri[1].isEmpty() || parametri[2].isEmpty() || 
	    			parametri[3].isEmpty() || date.isEmpty() || quantita_user == 0 || prezzo_user == 0 ) {
	    		
            	JOptionPane.showMessageDialog(this, "Non lasciare nessun campo vuoto");
	    		
	    	} 
            else if(parametri[0] == null || parametri[0].length() != 4  || !parametri[0].equals("I001")) {
			
	    		JOptionPane.showMessageDialog(this, "Controlla bene l'ID inventario");
			
		    }
            else if (!validaIdScorte(parametri[3])) {
            	
            	JOptionPane.showMessageDialog(this, "Controlla bene l'ID scorte");
            	
            } else {
            	
            	// Passo i valori ottenuti al controller 
            	
            	 try {
                 	
                 	inventario.controllerAggiungiScorteMaiTrattate(parametri[0], parametri[1], parametri[2], 
                 			parametri[3], prezzo_user, quantita_user, date);
                 	JOptionPane.showMessageDialog(this, "Perfetto, hai aggiunto all'inventario una scorta mai trattata prima d'ora");
                 	
                 	
                 } catch(SQLException exc) {
                 	
                 	JOptionPane.showMessageDialog(this, "Errore di connessione al database, forse l'ID scorte esiste già", "Errore", 
                     		JOptionPane.ERROR_MESSAGE);
                     exc.printStackTrace(); 
                 	
                 }
            	
            }
            
            
	    	
		});
	    
	    
	    add(panelDivisioneGriglia, BorderLayout.CENTER);
	    add(bottonePanel, BorderLayout.NORTH);
		
	}
	
}
