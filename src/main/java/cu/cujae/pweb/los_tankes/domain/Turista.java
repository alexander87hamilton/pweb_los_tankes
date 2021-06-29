package cu.cujae.pweb.los_tankes.domain;

import java.sql.Date;

public class Turista {

	private String noPasaporte;
	private String nombre;
	private Date fechaNacimiento;
	private String sexo;
	private Pais pais;
	

	public Turista(String noPasaporte, String nombre, Date fechaNacimiento, String sexo, Pais pais) {
	
		this.noPasaporte = noPasaporte;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.pais = pais;
	}


	public Turista() {
		
	}


	public String getNoPasaporte() {
		return noPasaporte;
	}


	public void setNoPasaporte(String noPasaporte) {
		this.noPasaporte = noPasaporte;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((noPasaporte == null) ? 0 : noPasaporte.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
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
		Turista other = (Turista) obj;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (noPasaporte == null) {
			if (other.noPasaporte != null)
				return false;
		} else if (!noPasaporte.equals(other.noPasaporte))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Turista [noPasaporte=" + noPasaporte + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", sexo=" + sexo + ", pais=" + pais + "]";
	}	
	
	
}
