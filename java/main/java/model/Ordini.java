package model;

import java.math.BigDecimal;
import java.util.*;

public class Ordini {

	private String id_ordine;
	private Fornitori f;
	private Date data_fornitura;
	private Scorte s;
	private int quantita;
	private boolean stoccaggio;
	private BigDecimal prezzo_totale;
	
	public Ordini (String id_ordine, Fornitori f, Date data_fornitura, Scorte s, int quantita,
			boolean stoccaggio, BigDecimal prezzo_totale) {
		
		this.id_ordine = id_ordine;
		this.f = f;
		this.data_fornitura = data_fornitura;
		this.quantita = quantita;
		this.s = s;
		this.stoccaggio = stoccaggio;
		this.prezzo_totale = prezzo_totale;
	}
	
    public static String generaIdCasuale() {
		
		Random random = new Random();
	    int numero = random.nextInt(10000); // 
	    return "ORDINE" + String.format("%04d", numero); 
	    
	}
	
	public String getId_ordine() {
		return id_ordine;
	}
	public void setId_ordine(String id_ordine) {
		this.id_ordine = id_ordine;
	}
	public Date getData_fornitura() {
		return data_fornitura;
	}
	public void setData_fornitura(Date data_fornitura) {
		this.data_fornitura = data_fornitura;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public boolean isStoccaggio() {
		return stoccaggio;
	}

	public void setStoccaggio(boolean stoccaggio) {
		this.stoccaggio = stoccaggio;
	}

	public BigDecimal getPrezzo_totale() {
		return prezzo_totale;
	}

	public void setPrezzo_totale(BigDecimal prezzo_totale) {
		this.prezzo_totale = prezzo_totale;
	}

	public Fornitori getFornitori() {
		return f;
	}

	public void setFornitori(Fornitori f) {
		this.f = f;
	}

	public Scorte getScorte() {
		return s;
	}

	public void setScorte(Scorte s) {
		this.s = s;
	}
	
	public String toString() {
	    return "Ordine{id=" + id_ordine + 
	           ", fornitore=" + (f != null ? f.getId_fornitore() : "null") + 
	           ", scorte=" + (s != null ? s.getId_scorte() : "null") + 
	           ", data_fornitura=" + data_fornitura + 
	           ", quantita=" + quantita + 
	           ", stoccaggio=" + stoccaggio + 
	           ", prezzo_totale=" + prezzo_totale + "}";
	}
	
}
