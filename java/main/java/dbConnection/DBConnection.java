package dbConnection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DBConnection{
	
	private static final String DB_NAME = "dbprogetto2";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "petrucci11";
    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    
    public static void eliminaDatabase ( String dbName ) {
    	
    	String url = "jdbc:postgresql://localhost:5432/dbProgetto";
    	String user = "postgres";
        String password = "petrucci11";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()){
        	
        	 // Revoca connessioni future
        	
            stmt.executeUpdate("REVOKE CONNECT ON DATABASE " + dbName + " FROM public;");

            // Termina connessioni attive
            
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT pg_terminate_backend(pid) " +
                    "FROM pg_stat_activity WHERE datname = '" + dbName + "' AND pid <> pg_backend_pid();"
            )){
            	
            	while (rs.next()) {
                    // Solo per forzare l'esecuzione, non serve leggerne il valore
                }
            }

            // Ora elimina
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + dbName);
            System.out.println("Database '" + dbName + "' eliminato con successo.");

        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del database:");
            e.printStackTrace();
        }
        
    }
    
    // Connessione al DB postgres (default)
    private static Connection connectToDefaultDB() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/postgres";
        return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
    }

    // Connessione al DB di progetto
    public static Connection connectToProjectDB() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
    }

    
    public static void eseguiScript(Connection conn, String filePath) throws Exception {
        String sql = Files.readString(Paths.get(filePath));
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Eseguito: " + filePath);
        }
    }
    
    
    // Crea il database se non esiste
    private static boolean creaDatabase() {
        try (Connection conn = connectToDefaultDB();
             Statement stmt = conn.createStatement()) {
        	
        		
            // Verifica se il DB esiste già
            ResultSet rs = stmt.executeQuery("SELECT * FROM pg_database WHERE datname = '" + DB_NAME + "'");
            if (rs.next()) {
                System.out.println(" Database '" + DB_NAME + "' esiste già.");
                return false;
            }

            // Se non esiste, lo crea
            stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
            System.out.println(" Database '" + DB_NAME + "' creato.");
            return true;

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione del database:");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Codice: " + e.getErrorCode());
            System.err.println("Messaggio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Metodo principale da chiamare nel main()
    public static void inizializzaDatabase() {
        try {
            boolean stato= creaDatabase();  // Crea DB se non esiste
            if (stato) Thread.sleep(10000);
            try (Connection conn = connectToProjectDB()) {
            	if (stato) {
                eseguiScript(conn, "src/main/java/sql/schema.sql");
                System.out.println(" Inizializzazione completata1.");
                Thread.sleep(1000);
                eseguiScript(conn, "src/main/java/sql/dati.sql");
                System.out.println(" Inizializzazione completata2.");
                Thread.sleep(1000);
                eseguiScript(conn, "src/main/java/sql/trigger.sql");
            	}
                System.out.println(" Inizializzazione completata3.");
            }
        } catch (Exception e) {
            System.err.println(" Errore durante l'inizializzazione:");
            e.printStackTrace();
        }
    }
    
}
