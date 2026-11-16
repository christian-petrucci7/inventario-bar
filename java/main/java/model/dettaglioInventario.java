package model;

public class dettaglioInventario {

	 private Inventario i;
	 private Scorte s;
	 
	public dettaglioInventario(Inventario i, Scorte s){
		
		this.i = i;
		this.s = s;
	}
	 
	public Inventario getInventario() {
		return i;
	}
	public void setInventario(Inventario i) {
		this.i = i;
	}
	public Scorte getScorte() {
		return s;
	}
	public void setScorte(Scorte s) {
		this.s = s;
	}

	 
	
}
