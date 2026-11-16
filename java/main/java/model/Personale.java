package model;

import java.sql.Date;

public class Personale {

	private String id_personale;
	private String sesso;
	private String codice_fiscale;
	private String nome;
	private String cognome;
	private String responsabilita;
	private Date data_assunzione;
	private String indirizzo;
	private int civico;
	private int CAP;
	
	public Personale (String id_personale, String sesso, String codice_fiscale, String nome, 
			String cognome, String responsabilita, Date data_assunzione, String indirizzo, int civico, int CAP) {
		
		this.id_personale = id_personale;
		this.sesso = sesso;
		this.codice_fiscale = codice_fiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.responsabilita = responsabilita;
		this.data_assunzione = data_assunzione;
		this.indirizzo = indirizzo;
		this.civico = civico;
		this.CAP = CAP;
		
	}
	
	public String getId_personale() {
		return id_personale;
	}
	public void setId_personale(String id_personale) {
		this.id_personale = id_personale;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getResponsabilita() {
		return responsabilita;
	}
	public void setResponsabilita(String responsabilita) {
		this.responsabilita = responsabilita;
	}
	public Date getData_assunzione() {
		return data_assunzione;
	}
	public void setData_assunzione(Date data_assunzione) {
		this.data_assunzione = data_assunzione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public int getCivico() {
		return civico;
	}
	public void setCivico(int civico) {
		this.civico = civico;
	}
	public int getCAP() {
		return CAP;
	}
	public void setCAP(int cAP) {
		CAP = cAP;
	}
	
}
