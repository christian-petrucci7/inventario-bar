package DAOimp;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DAO.OrdiniDAO;
import model.Ordini;

public class OrdiniDAOimp implements OrdiniDAO {
	
	Connection conn;
	
	public OrdiniDAOimp(Connection conn) {
		
		this.conn = conn;
		
	}

	public void deleteOrdineByData( Date data_fornitura )  throws SQLException {
		
		
		
        String sql = "DELETE from ordini WHERE data_fornitura = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
       
			  pstmt.setDate(1, data_fornitura);    
			
			  pstmt.executeUpdate();	
			
		}
		
	}
	
	public List<Ordini> createListOrdine(String data_fornitura, List<Integer> quantita, List<Scorte> list_scorte, 
            List<Fornitori> list_fornitori, int totale_scorte) throws SQLException {
		
		List<Ordini> list_ordini = new ArrayList<>();

        Date dataFornitura = Date.valueOf(data_fornitura);

        String sql = "INSERT INTO ordini (id_ordine, id_fornitore, data_fornitura, " +
        "id_scorte, quantita, stoccaggio) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < totale_scorte; i++) {

        String id_ordine = Ordini.generaIdCasuale();

        Fornitori fornitore = list_fornitori.get(i);
        Scorte scorta = list_scorte.get(i);

        pstmt.setString(1, id_ordine);
        pstmt.setString(2, fornitore.getId_fornitore());
        pstmt.setDate(3, dataFornitura);
        pstmt.setString(4, scorta.getId_scorte());
        pstmt.setInt(5, quantita.get(i));
        pstmt.setBoolean(6, false);

        pstmt.executeUpdate();


        Ordini ordine = new Ordini(id_ordine, fornitore, dataFornitura, scorta, quantita.get(i), 
        		false, null);
        
        list_ordini.add(ordine);
        
        }
        
     }

     return list_ordini;
}

	public void getOrdineByData( String data_fornitura )  throws SQLException {
		
		Date dataFornitura = Date.valueOf(data_fornitura);
		
        String sql = "SELECT * FROM ordini WHERE data_fornitura = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
					
			pstmt.setDate(1, dataFornitura);   
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				System.out.println( rs.getString("id_ordine") + " - " + rs.getString("id_scorte") + " - " + 
				rs.getString("id_fornitore") + " - " + rs.getDate("data_fornitura") + " - " + rs.getInt("quantita"));
				
			}
			
			
		}
		
	}
	
	public Date getDataUltimoOrdine ()  throws SQLException {
	
        String sql = "SELECT data_fornitura FROM ordini";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = pstmt.executeQuery();
			
			  if (rs.next()) {
				  
		            Date data = rs.getDate("data_fornitura");
		            return data;
		            
		        }
			  
			
			return null;
			
		}
	
		
	}
	
	public boolean getStoccaggio() throws SQLException {
		
		String sql = "SELECT stoccaggio from ordini";
		
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				return rs.getBoolean("stoccaggio");	
				
			}
			
		}
        
        return false;
		
	}
	
	public void cambiaStatoStoccaggio() throws SQLException {
		
		 String sql = "UPDATE ordini SET stoccaggio = true WHERE stoccaggio = false";
			
			try(PreparedStatement pstmt = conn.prepareStatement(sql)) {    
					
				  pstmt.executeUpdate();	

			}
		
	}
	
	public List<Ordini> getTabellaOrdini() throws SQLException {
	    
	    List<Ordini> list_ordini = new ArrayList<>();

	    String sql = "SELECT o.id_ordine, o.data_fornitura, o.quantita, o.stoccaggio, o.prezzo_totale, " +
	                 "f.id_fornitore, f.nome_fornitore, f.ubicazione_azienda, " +
	                 "s.id_scorte, s.nome_produttore, s.tipo_prodotto, s.prezzo_unitario " +
	                 "FROM ordini o " +
	                 "JOIN fornitori f ON o.id_fornitore = f.id_fornitore " +
	                 "JOIN scorte s ON o.id_scorte = s.id_scorte";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {

	            
	            Fornitori fornitore = new Fornitori(
	                rs.getString("nome_fornitore"),
	                rs.getString("id_fornitore"),
	                rs.getString("ubicazione_azienda")
	            );

	            
	            Scorte scorta = new Scorte(
	                rs.getString("nome_produttore"),
	                rs.getString("tipo_prodotto"),
	                rs.getString("id_scorte"),
	                rs.getFloat("prezzo_unitario")
	            );

	            
	            Ordini ordine = new Ordini(
	                rs.getString("id_ordine"),
	                fornitore,
	                rs.getDate("data_fornitura"),
	                scorta,
	                rs.getInt("quantita"),
	                rs.getBoolean("stoccaggio"),
	                rs.getBigDecimal("prezzo_totale")
	            );

	            list_ordini.add(ordine);
	        }
	    }

	    return list_ordini;
	}
	
	
}
