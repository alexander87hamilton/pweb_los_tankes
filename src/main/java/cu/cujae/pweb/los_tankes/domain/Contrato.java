package cu.cujae.pweb.los_tankes.domain;

import java.sql.Date;

public class Contrato {
	
	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private FormaPago formaPago;
	private double tarifa;
	private Chofer chofer;
	private Auto auto;
	private Turista turista;
	private Date diasProrroga;
	
	
	public Contrato(Long id, Date fechaInicio, Date fechaFin, FormaPago formaPago, double tarifa, Chofer chofer,
			Auto auto, Turista turista, Date diasProrroga) {
		
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.formaPago = formaPago;
		this.tarifa = tarifa;
		this.chofer = chofer;
		this.auto = auto;
		this.turista = turista;
		this.diasProrroga = diasProrroga;
	}


	public Contrato() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public FormaPago getFormaPago() {
		return formaPago;
	}


	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}


	public double getTarifa() {
		return tarifa;
	}


	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}


	public Chofer getChofer() {
		return chofer;
	}


	public void setChofer(Chofer chofer) {
		this.chofer = chofer;
	}


	public Auto getAuto() {
		return auto;
	}


	public void setAuto(Auto auto) {
		this.auto = auto;
	}


	public Turista getTurista() {
		return turista;
	}


	public void setTurista(Turista turista) {
		this.turista = turista;
	}


	public Date getDiasProrroga() {
		return diasProrroga;
	}


	public void setDiasProrroga(Date diasProrroga) {
		this.diasProrroga = diasProrroga;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auto == null) ? 0 : auto.hashCode());
		result = prime * result + ((chofer == null) ? 0 : chofer.hashCode());
		result = prime * result + ((diasProrroga == null) ? 0 : diasProrroga.hashCode());
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((formaPago == null) ? 0 : formaPago.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tarifa);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((turista == null) ? 0 : turista.hashCode());
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
		Contrato other = (Contrato) obj;
		if (auto == null) {
			if (other.auto != null)
				return false;
		} else if (!auto.equals(other.auto))
			return false;
		if (chofer == null) {
			if (other.chofer != null)
				return false;
		} else if (!chofer.equals(other.chofer))
			return false;
		if (diasProrroga == null) {
			if (other.diasProrroga != null)
				return false;
		} else if (!diasProrroga.equals(other.diasProrroga))
			return false;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!fechaFin.equals(other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (formaPago == null) {
			if (other.formaPago != null)
				return false;
		} else if (!formaPago.equals(other.formaPago))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(tarifa) != Double.doubleToLongBits(other.tarifa))
			return false;
		if (turista == null) {
			if (other.turista != null)
				return false;
		} else if (!turista.equals(other.turista))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Contrato [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", formaPago="
				+ formaPago + ", tarifa=" + tarifa + ", chofer=" + chofer + ", auto=" + auto + ", turista=" + turista
				+ ", diasProrroga=" + diasProrroga + "]";
	}
	
	
	

}
