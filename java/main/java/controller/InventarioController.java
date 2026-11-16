package controller;

import java.util.*;

import java.sql.*;
import java.sql.Date;

import model.Fornitori;
import model.Ordini;
import model.Scorte;
import model.dettaglioInventario;
import DAOimp.FornitoriDAOimp;
import DAOimp.InventarioDAOimp;
import DAOimp.OrdiniDAOimp;
import DAOimp.ScorteDAOimp;


public class InventarioController {
	
	private InventarioDAOimp inventarioDAOimp;
	private ScorteDAOimp scorteDAOimp;
	private OrdiniDAOimp ordiniDAOimp;
	private FornitoriDAOimp fornitoriDAOimp;
	
	public InventarioController(Connection conn) {
        this.inventarioDAOimp = new InventarioDAOimp(conn);
        this.scorteDAOimp = new ScorteDAOimp(conn);
        this.ordiniDAOimp = new OrdiniDAOimp(conn);
        this.fornitoriDAOimp = new FornitoriDAOimp(conn);
    }

	public List <dettaglioInventario> controllerVerificaDisponibilitaScorte(String id_inventario) throws SQLException {
		
        if(id_inventario == null || id_inventario.length() != 4 || !id_inventario.equals("I001")) {
			
			throw new IllegalArgumentException("L'ID inventario errato oppure lunghezza sbagliata");
			
		}
			
		List <dettaglioInventario> inventario = inventarioDAOimp.verificaDisponibilita (id_inventario);
			
		return inventario;
		
	}
	
	public void controllerAggiungiScorteMaiTrattate ( String id_inventario, String nome_prodotto, 
			String tipo_prodotto, String id_scorte, float prezzo_unitario, int quantita, 
			String scadenza) throws SQLException {
		
		if(id_inventario == null || id_inventario.length() != 4  || !id_inventario.equals("I001")) {
			
			throw new IllegalArgumentException("ID inventario errato oppure lunghezza sbagliata");
			
		}
		
		if (id_scorte == null || !id_scorte.matches("^SCORTE\\d{4}$")) {
			
		    throw new IllegalArgumentException("L'ID scorte deve iniziare con 'SCORTE' seguito da 4 cifre (es: SCORTE1234).");
		    
		}
		
		if(prezzo_unitario <= 0) {
			throw new IllegalArgumentException("Il prezzo deve essere maggiore di 0");
		}
		
		if(quantita <= 0) {
			throw new IllegalArgumentException("La quantità deve essere maggiore di 0");
		}
		
			
		Scorte s = scorteDAOimp.createScorte(nome_prodotto, tipo_prodotto, id_scorte, prezzo_unitario);
		inventarioDAOimp.addScorte(id_inventario, s, quantita, scadenza);
		
		
	}
	
	public boolean controllerAggiungiQuantitaOrdinate(String id_inventario) throws SQLException {
		
        if(id_inventario == null || id_inventario.length() != 4 || !id_inventario.equals("I001")) {
			
			throw new IllegalArgumentException("ID inventario errato oppure lunghezza sbagliata.");
			
		}
		
			
		if(!ordiniDAOimp.getStoccaggio()) {
			
			inventarioDAOimp.updateQuantitaByOrdine();
			ordiniDAOimp.cambiaStatoStoccaggio();
			
			return true;
		        
		} else {
			return false;
		}
		
		
	}
	
	public boolean controllerRimuoviQuantita(int quantita_da_rimuovere, String nome_prodotto) throws SQLException{
			
		 if(quantita_da_rimuovere <= 0) {
			 
			 throw new IllegalArgumentException("La quantità deve essere maggiore di 0");
			 
			}
		
			
		 int quantita_inventario = inventarioDAOimp.getQuantitaByNome(nome_prodotto);
			 
		 if(quantita_inventario == 0) {
				 
			 return false;
			 
				 
		 } else {
				 
			 inventarioDAOimp.updateQuantitaRimozione(quantita_da_rimuovere, nome_prodotto);
			 return true;
				 
		 }
				
		
	}
	
	public void controllerDeleteFromInventario(String id_inventario, String id_scorte)throws SQLException {
		
		 if(id_inventario == null || id_inventario.length() != 4 || !id_inventario.equals("I001")) {
				
				throw new IllegalArgumentException("ID inventario errato oppure lunghezza sbagliata");
				
			}
			
		 if(id_scorte == null || !id_scorte.matches("^SCORTE\\d{4}$")) {
				
			    throw new IllegalArgumentException("L'ID scorte deve iniziare con 'SCORTE' seguito da 4 cifre (es: SCORTE1234).");
			    
		    }
		
			
		 inventarioDAOimp.deleteFromInventario(id_scorte);
			
	}
	
	public List<Ordini> controllerNuovoOrdine(String id_inventario, 
			String data_fornitura, List<Integer> lista_quantita) throws SQLException {
		
		 if(id_inventario == null || id_inventario.length() != 4 || !id_inventario.equals("I001")) {
				
				throw new IllegalArgumentException("ID inventario errato oppure lunghezza sbagliata");
				
			}
		 
		 List<Scorte> lista_scorte = scorteDAOimp.getAllScorte();
		 
		 List <Fornitori> lista_fornitori = fornitoriDAOimp.getAllFornitori();
		 
		 int totale_scorte = scorteDAOimp.getTotaleScorteTrattate();
		 
		 Date data_ultimoOrdine = ordiniDAOimp.getDataUltimoOrdine();
		 
		 ordiniDAOimp.deleteOrdineByData(data_ultimoOrdine);
			 
		 List <Ordini> lista_ordini = ordiniDAOimp.createListOrdine(data_fornitura, lista_quantita, lista_scorte, lista_fornitori, totale_scorte);
		 
		 return lista_ordini;
		
	}
	
	public boolean controllerAggiungiQuantitaNONOrdinate(String nome_prodotto, int quantita_da_aggiungere) 
			throws SQLException {
	
		 
		 if(quantita_da_aggiungere <= 0) {
			 
			return false;
			 
		 } else {
			 
			 inventarioDAOimp.updateQuantitaAggiunta(quantita_da_aggiungere, nome_prodotto);
			 return true;
			 
		 }

		
		
	}
	
	public void controllerAggiungiScorteGiàTrattate (String id_inventario, String id_scorte, String scadenza, int quantita) throws SQLException {
		
        if(id_inventario == null || id_inventario.length() != 4  || !id_inventario.equals("I001")) {
			
			throw new IllegalArgumentException("ID inventario errato oppure lunghezza sbagliata");
			
		}
		
		if (id_scorte == null || !id_scorte.matches("^SCORTE\\d{4}$")) {
			
		    throw new IllegalArgumentException("L'ID scorte deve iniziare con 'SCORTE' seguito da 4 cifre (es: SCORTE1234).");
		    
		}
		
		if(quantita <= 0) {
			throw new IllegalArgumentException("La quantità deve essere maggiore di 0");
		}
		

			
		Scorte s = scorteDAOimp.getScorteByID(id_scorte);
		inventarioDAOimp.addScorte(id_inventario, s, quantita, scadenza);
		
		
	}
	
	public boolean allarmeScorte() throws SQLException {

	    
	    int quantita_totale = inventarioDAOimp.getTotaleScorte();

	    if (quantita_totale < 100) {
	    	
	        return true;
	    
	    }
	    
	    return false;
	    
	}
	
	public List <dettaglioInventario> controllerGetScorteByNome (String nome_prodotto) throws SQLException {
		
		List <dettaglioInventario> scorteByName = inventarioDAOimp.getScorteByName (nome_prodotto);
		
		return scorteByName;
		
	}
	
    public List <dettaglioInventario> controllerGetScorteByTipo (String tipo_prodotto) throws SQLException {
		
		List <dettaglioInventario> scorteByTipo = inventarioDAOimp.getScorteByTipo (tipo_prodotto);
		
		return scorteByTipo;
		
	}
	
	public int controllerGetTotaleScorte() throws SQLException {
		
		int totale_scorte = scorteDAOimp.getTotaleScorteTrattate();
		
		return totale_scorte;
		
	}
    
	public List<String> controllerGetNomiScorteTrattate() throws SQLException {
		
		List <String> list_nomiScorte = scorteDAOimp.getNomiScorteTrattate();
		
		return list_nomiScorte;
		
	}
	
	public List<Ordini> controllerGetTabellaOrdini() throws SQLException {
		
		List<Ordini> list_ordini = ordiniDAOimp.getTabellaOrdini();
		
		return list_ordini;
		
	}
	
	public List<String> controllerGetListaNomiInInventario() throws SQLException {
		
		List<String> lista_nomi_prodotto = inventarioDAOimp.getNomiProdotti();
		
		return lista_nomi_prodotto;
		
	}
	
    
}
