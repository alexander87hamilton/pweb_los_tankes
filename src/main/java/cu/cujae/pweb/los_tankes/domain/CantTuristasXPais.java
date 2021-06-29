package cu.cujae.pweb.los_tankes.domain;

public class CantTuristasXPais {

	private String pais;
	private int cantTuristas;
	
	public CantTuristasXPais(String pais, int cantTuristas) {
		
		this.pais = pais;
		this.cantTuristas = cantTuristas;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public int getCantTuristas() {
		return cantTuristas;
	}

	public void setCantTuristas(int cantTuristas) {
		this.cantTuristas = cantTuristas;
	}
	
	
	
	
}
