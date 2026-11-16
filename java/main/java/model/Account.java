package model;

public class Account {

	private Personale p;
	private String email;
	private String password;
	
	
	public Account( Personale p, String email, String password) {
		
		this.p = p;
		this.email = email;
		this.password = password;
		
	}
	
	public Personale get_personale() {
		return this.p;
	}
	public void set_personale(Personale p) {
		this.p = p;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
