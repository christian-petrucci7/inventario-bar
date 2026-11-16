package model;


public class Scorte {

	private String nome_produttore;
	private String tipo_prodotto;
	private String id_scorte;
	private float prezzo_unitario;
	
	public Scorte(String nome_produttore, String tipo_prodotto, String id_scorte, 
			float prezzo_unitario) {
		
		this.nome_produttore = nome_produttore;
		this.tipo_prodotto = tipo_prodotto;
		this.id_scorte = id_scorte;
		this.prezzo_unitario = prezzo_unitario;
		
	}
	
	public String toString() {
	    return nome_produttore + " - " + tipo_prodotto + " - " + id_scorte + " - " + prezzo_unitario ;
	}

	public String getNome_prodotto() {
		return nome_produttore;
	}

	public void setNome_prodotto(String azienda) {
		this.nome_produttore = azienda;
	}

	public String getTipo_prodotto() {
		return tipo_prodotto;
	}

	public void setTipo_prodotto(String tipo_prodotto) {
		this.tipo_prodotto = tipo_prodotto;
	}

	public String getId_scorte() {
		return id_scorte;
	}

	public void setId_scorte(String id_scorte) {
		this.id_scorte = id_scorte;
	}


	public float getPrezzo_unitario() {
		return prezzo_unitario;
	}

	public void setPrezzo_unitario(float prezzo_unitario) {
		this.prezzo_unitario = prezzo_unitario;
	}

	
}
