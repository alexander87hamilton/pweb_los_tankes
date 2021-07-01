package cu.cujae.pweb.los_tankes.domain;

public class ContractXTourist {

	private String tourist;
	private String nombre;
	private int cantContract;
	
	public ContractXTourist(String tourist, int cantContract, String nombre) {
		
		this.setTourist(tourist);
		this.cantContract = cantContract;
		this.nombre = nombre;
	}

	

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getCantContract() {
		return cantContract;
	}

	public void setCantContract(int cantContract) {
		this.cantContract = cantContract;
	}



	public String getTourist() {
		return tourist;
	}



	public void setTourist(String tourist) {
		this.tourist = tourist;
	}
	
	
	
	
}
