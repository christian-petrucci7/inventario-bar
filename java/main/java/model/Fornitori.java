package model;


public class Fornitori {

	private String nome_fornitore;
	private String id_fornitore;
	private String ubicazione_azienda;
	
	public Fornitori ( String nome_fornitore, String id_fornitore, String ubicazione_azienda) {
		
		this.nome_fornitore = nome_fornitore;
		this.id_fornitore = id_fornitore;
		this.ubicazione_azienda = ubicazione_azienda;
	}
	
	public String getNome_fornitore() {
		return nome_fornitore;
	}
	public void setNome_fornitore(String nome_fornitore) {
		this.nome_fornitore = nome_fornitore;
	}
	public String getId_fornitore() {
		return id_fornitore;
	}
	public void setId_fornitore(String id_fornitore) {
		this.id_fornitore = id_fornitore;
	}
	public String getUbicazione_azienda() {
		return ubicazione_azienda;
	}
	public void setUbicazione_azienda(String ubicazione_azienda) {
		this.ubicazione_azienda = ubicazione_azienda;
	}
	
	
	
}
