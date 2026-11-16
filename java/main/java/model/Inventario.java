package model;
import java.sql.Date;

public class Inventario {

	private String id_inventario;
	private Scorte s;
	private int quantita;
	private Date scadenza;
	
	public Inventario( String id_inventario, Scorte s, int quantita, Date scadenza) {
		
		this.id_inventario = id_inventario;
		this.s = s;
		this.quantita = quantita;
		this.scadenza = scadenza;
		
	}
	
	public String getId_inventario() {
		return id_inventario;
	}
	public void setId_inventario(String id_inventario) {
		this.id_inventario = id_inventario;
	}
	public Scorte getScorte() {
		return this.s;
	}
	public void setScorte(Scorte s) {
		this.s = s;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public Date getScadenza() {
		return scadenza;
	}
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	
}
