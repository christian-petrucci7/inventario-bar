package DAOimp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DAO.InventarioDAO;
import model.Inventario;
import model.Scorte;
import model.dettaglioInventario;

public class InventarioDAOimp implements InventarioDAO {
	
	Connection conn;

	public InventarioDAOimp (Connection conn) {
		
		this.conn = conn;
		
	}
	
	public List<dettaglioInventario> verificaDisponibilita(String id_inventario) throws SQLException {

	    List<dettaglioInventario> dettagliScorte = new ArrayList<>();

	    String sql = "SELECT DISTINCT i.id_inventario, i.id_scorte, s.nome_produttore, " +
	                 "s.tipo_prodotto, s.prezzo_unitario, i.quantita, i.scadenza " +
	                 "FROM inventario AS i " +
	                 "JOIN scorte AS s ON s.id_scorte = i.id_scorte " +
	                 "WHERE i.id_inventario = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, id_inventario);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            // Prima costruisci Scorte
	            Scorte scorte = new Scorte(
	                rs.getString("id_scorte"),
	                rs.getString("nome_produttore"),
	                rs.getString("tipo_prodotto"),
	                rs.getFloat("prezzo_unitario")
	            );

	            // Poi costruisci Inventario con l'oggetto Scorte
	            Inventario inventario = new Inventario(
	                rs.getString("id_inventario"),
	                scorte,
	                rs.getInt("quantita"),
	                rs.getDate("scadenza")
	            );

	            // Infine costruisci DettaglioInventario
	            dettaglioInventario dettaglio = new dettaglioInventario(inventario, scorte);

	            dettagliScorte.add(dettaglio);
	        }
	    }

	    return dettagliScorte;
	}
	
	
	public void updateQuantitaAggiunta ( int quantita_da_aggiungere, String nome_prodotto) throws SQLException {
		
		String sql = "UPDATE inventario i SET quantita = i.quantita + ? FROM scorte s "
	    		+ "WHERE i.id_scorte = s.id_scorte AND s.nome_produttore = ? ";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
		      pstmt.setInt(1, quantita_da_aggiungere);       
			  pstmt.setString(2, nome_prodotto);    
			
			  pstmt.executeUpdate();	
			  
		}
		
	}
	
	public void updateQuantitaRimozione ( int quantita_da_rimuovere, String nome_produttore) throws SQLException {
		
		String sql = """
			    UPDATE inventario i
			    SET quantita = CASE
			        WHEN i.quantita - ? < 0 THEN 0
			        ELSE i.quantita - ?
			    END
			    FROM scorte s
			    WHERE i.id_scorte = s.id_scorte AND s.nome_produttore = ?
			""";
			
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			  pstmt.setInt(1, quantita_da_rimuovere);  
			  pstmt.setInt(2, quantita_da_rimuovere); 
			  pstmt.setString(3, nome_produttore);    
				
			  pstmt.executeUpdate();	

		}
			
	}
	
	
	public void addScorte( String id_inventario, Scorte s, int quantita, 
			String scadenza) throws SQLException {
		
		Date dataScadenza = Date.valueOf(scadenza);
		
		String sql = "INSERT INTO inventario (id_inventario, id_scorte, quantita, scadenza )"
				+ " VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
		    pstmt.setString(1, id_inventario);     
			pstmt.setString(2, s.getId_scorte());         
			pstmt.setInt(3, quantita);   
			pstmt.setDate(4, dataScadenza);    
			
			pstmt.executeUpdate();	
			
			
		}
		
		
	}
	
	
 	public void deleteFromInventario( String id_scorte ) throws SQLException{
		
        String sql = "DELETE from inventario WHERE id_scorte = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id_scorte);    
			
		    pstmt.executeUpdate();	
			    

		}
		
	}
	
	public int getQuantitaByNome (String nome_prodotto) throws SQLException {
		
		int quantita = 0;
		
		String sql = "SELECT i.quantita FROM inventario i JOIN scorte s "
				+ "ON i.id_scorte = s.id_scorte WHERE s.nome_produttore = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
		    pstmt.setString(1, nome_prodotto);     
			

			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
               quantita = rs.getInt("quantita");   
            }
			
			return quantita;
			
		}
		
	}
	
	public void updateQuantitaByOrdine () throws SQLException {
		
		 String sql1 = "SELECT id_scorte, quantita FROM ordini";
		 String sql2 = "UPDATE inventario SET quantita = quantita + ? WHERE id_scorte = ?";

			
		 try( PreparedStatement selectStmt = conn.prepareStatement(sql1);
				 PreparedStatement updateStmt = conn.prepareStatement(sql2) ) {
			 
		      ResultSet rs = selectStmt.executeQuery();
		       
		       while (rs.next()) {

		            updateStmt.setInt(1, rs.getInt("quantita"));
		            updateStmt.setString(2, rs.getString("id_scorte"));
		            updateStmt.executeUpdate();
		            
		       }
				    
			}
			
}
	
	
	public int getTotaleScorte () throws SQLException {
		
		String sql = "SELECT SUM(quantita) AS totale_quantita FROM inventario";
		int quantita_totale = 0;
		
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){  

			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
               quantita_totale = rs.getInt("totale_quantita");   
            }
			
			return quantita_totale;
			
        }
		
	}
	
	public List<dettaglioInventario> getScorteByName(String nome_prodotto) throws SQLException {

	    List<dettaglioInventario> scorteByName = new ArrayList<>();

	    String sql = "SELECT * FROM inventario AS i " +
	                 "JOIN scorte AS s ON s.id_scorte = i.id_scorte " +
	                 "WHERE s.nome_produttore = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, nome_prodotto);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	         
	            Scorte scorte = new Scorte(
	                rs.getString("id_scorte"),
	                rs.getString("nome_produttore"),
	                rs.getString("tipo_prodotto"),
	                rs.getFloat("prezzo_unitario")
	            );

	       
	            Inventario inventario = new Inventario(
	                rs.getString("id_inventario"),
	                scorte,
	                rs.getInt("quantita"),
	                rs.getDate("scadenza")
	            );

	          
	            dettaglioInventario dettaglio = new dettaglioInventario(inventario, scorte);

	            scorteByName.add(dettaglio);
	        }
	    }

	    return scorteByName;
	}
	
	public List<dettaglioInventario> getScorteByTipo(String tipo_prodotto) throws SQLException {

	    List<dettaglioInventario> scorteByTipo = new ArrayList<>();

	    String sql = "SELECT * FROM inventario AS i " +
	                 "JOIN scorte AS s ON s.id_scorte = i.id_scorte " +
	                 "WHERE s.tipo_prodotto = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, tipo_prodotto);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            
	            Scorte scorte = new Scorte(
	                rs.getString("id_scorte"),
	                rs.getString("nome_produttore"),
	                rs.getString("tipo_prodotto"),
	                rs.getFloat("prezzo_unitario")
	            );


	            Inventario inventario = new Inventario(
	                rs.getString("id_inventario"),
	                scorte,
	                rs.getInt("quantita"),
	                rs.getDate("scadenza")
	            );

	            
	            dettaglioInventario dettaglio = new dettaglioInventario(inventario, scorte);

	            scorteByTipo.add(dettaglio);
	        }
	    }

	    return scorteByTipo;
	}
    
    public List<String> getNomiProdotti () throws SQLException {
    	
    	List <String> list_nomi = new ArrayList<>();
    	
    	String sql = "SELECT s.nome_produttore FROM inventario AS i JOIN scorte AS s "
    			+ "ON s.id_scorte = i.id_scorte ";
    	
    	try(PreparedStatement pstmt = conn.prepareStatement(sql)){
    		

			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				list_nomi.add(rs.getString("nome_produttore"));
			}
    		
    	}
    	
    	return list_nomi;
    	
    }
     
	 
}
