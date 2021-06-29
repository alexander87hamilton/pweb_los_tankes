package cu.cujae.pweb.los_tankes.domain;

public class CantAutosXMarca {
	
	public String marcaModelo;
	private int cantAutos;
	
	public CantAutosXMarca(String marcaModelo, int cantAutos) {

		this.marcaModelo = marcaModelo;
		this.cantAutos = cantAutos;
	}

	public String getMarcaModelo() {
		return marcaModelo;
	}

	public void setMarcaModelo(String marcaModelo) {
		this.marcaModelo = marcaModelo;
	}

	public int getCantAutos() {
		return cantAutos;
	}

	public void setCantAutos(int cantAutos) {
		this.cantAutos = cantAutos;
	}
	
	
	

}
