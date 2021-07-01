package cu.cujae.pweb.los_tankes.domain;

public class CantContratosXChofer {

	private String chofer;
	private String nombre;
	private int cantContratos;
	
	public CantContratosXChofer(String chofer, int cantContratos , String nombre) {
		
		this.nombre = nombre;
		this.chofer = chofer;
		this.cantContratos = cantContratos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getChofer() {
		return chofer;
	}

	public void setChofer(String chofer) {
		this.chofer = chofer;
	}

	public int getCantContratos() {
		return cantContratos;
	}

	public void setCantContratos(int cantContratos) {
		this.cantContratos = cantContratos;
	}


	
	
	
	
}
