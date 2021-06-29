package cu.cujae.pweb.los_tankes.domain;

public class Auto {

	private String placa;
	private Modelo modelo;
	private Estado estado;
	private double cantKm;
	private String color;


	public Auto(String placa, Modelo modelo, Estado estado, double cantKm, String color) {

		this.placa = placa;
		this.modelo = modelo;
		this.estado = estado;
		this.cantKm = cantKm;
		this.color = color;
	}
	public Auto() {

	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public double getCantKm() {
		return cantKm;
	}

	public void setCantKm(double cantKm) {
		this.cantKm = cantKm;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cantKm);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auto other = (Auto) obj;
		if (Double.doubleToLongBits(cantKm) != Double.doubleToLongBits(other.cantKm))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Auto [placa=" + placa + ", modelo=" + modelo + ", estado=" + estado + ", cantKm=" + cantKm + ", color="
				+ color + "]";
	}
}
