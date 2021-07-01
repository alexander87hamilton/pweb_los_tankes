package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.cujae.pweb.los_tankes.domain.Auto;
import cu.cujae.pweb.los_tankes.domain.CantContratosXChofer;
import cu.cujae.pweb.los_tankes.domain.Chofer;
import cu.cujae.pweb.los_tankes.domain.ContractXTourist;
import cu.cujae.pweb.los_tankes.domain.Contrato;
import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Modelo;
import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.domain.Turista;
import cu.cujae.pweb.los_tankes.service.ModeloService;
import cu.cujae.pweb.los_tankes.service.ModeloServiceImpl;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "contratoBean")
@ELBeanName(value = "contratoBean")
public class ContratoBean {

	@Autowired
	private RestService restService;

	private List<Contrato> contratoList;
	private Contrato contrato;
	private Contrato selectedContrato;
	private Date date;
	private Date date2;
	private Date date3;
	
	private TabView tabContratos;
	private int activeIndexTab = 0;


	public ContratoBean() {
		contratoList = new ArrayList<>();
		contrato = new Contrato();
		selectedContrato = new Contrato();
		date = new Date();
		date2 = new Date();
		date3 = new Date();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabContratos.getChildren().remove(event.getTab());
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public void newContrato() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabContratos, JsfUtils.getStringValueFromBundle("label_newContrato"), "/pages/classes/contract/createContract.xhtml", "contratoTabView");

	}

	public void editContrato() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabContratos, JsfUtils.getStringValueFromBundle("label_editContrato"), "/pages/classes/contract/editContract.xhtml", "contratoTabView");
	}


	public boolean validarFecha () 
	{	
		return (date2.after(date));
	}

	public int cantDiasAlquiler (Date fechaI, Date fechaF)
	{
		return (int) ((fechaF.getTime() - fechaI.getTime())/86400000);
	}

	public double calcularTarifa (double tarifa, Date fechaI, Date fechaF) 
	{
		return cantDiasAlquiler(fechaI, fechaF) * tarifa;
	}

	
	public void modificarEstadoAutoAlquilado (Auto auto) 
	{
		Estado estado = new Estado((long) 2,"Alquilado");
		auto.setEstado(estado);
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String response = (String)restService.PUT("/api/v8/auto", params, auto, String.class, null).getBody();
		} catch (Exception e) {
			
		}

	}

	
	public void modificarEstadoAutoDisponible () 
	{
		Estado estado = new Estado((long) 3,"Disponible");
		selectedContrato.getAuto().setEstado(estado);
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String response = (String)restService.PUT("/api/v8/auto", params, selectedContrato.getAuto(), String.class, null).getBody();
		} catch (Exception e) {
			
		}
	}

	
	@SuppressWarnings("deprecation")
	public void save() {
		if(!validarFecha())
		{	
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_contratoFechaMal")));
		} else {
			try {
				contrato.setFechaInicio(new java.sql.Date(date.getYear(), date.getMonth(), date.getDate()));
				contrato.setFechaFin(new java.sql.Date(date2.getYear(), date2.getMonth(), date2.getDate()));

				
				contrato.setTarifa(calcularTarifa(contrato.getAuto().getModelo().getTarifa(), date, date2));

				
				modificarEstadoAutoAlquilado(contrato.getAuto());

				if(!(date3 == null))
					contrato.setDiasProrroga(new java.sql.Date(date3.getYear(), date3.getMonth(), date3.getDate()));

				String response = (String)restService.POST("/api/v10/contrato", contrato, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabContratos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
			}
		}

	}

	@SuppressWarnings("deprecation")
	public void update() {
		if(!validarFecha() && !date3.after(date2))
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_contratoFechaMal")));
		}
		else {
			try {
				selectedContrato.setFechaInicio(new java.sql.Date(date.getYear(), date.getMonth(), date.getDate()));
				selectedContrato.setFechaFin(new java.sql.Date(date2.getYear(), date2.getMonth(), date2.getDate()));

				
				selectedContrato.setTarifa(calcularTarifa(selectedContrato.getAuto().getModelo().getTarifa(), date, date2));

				
				modificarEstadoAutoAlquilado(selectedContrato.getAuto());

				if(!(date3 == null))
				{
					selectedContrato.setDiasProrroga(new java.sql.Date(date3.getYear(), date3.getMonth(), date3.getDate()));
					double tarifaProrroga = (((10 * selectedContrato.getAuto().getModelo().getTarifa())/100) * cantDiasAlquiler(date2, date3)) + 
							selectedContrato.getAuto().getModelo().getTarifa() * cantDiasAlquiler(date, date2);
					selectedContrato.setTarifa(tarifaProrroga);
				}


				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				String response = (String)restService.PUT("/api/v10/contrato", params, selectedContrato, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabContratos);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
			}
		}
	}

	public void delete() {
		try {
			if(hayMismoAutoEnOtroContrato()) {
				selectedContrato.getAuto().setEstado(new Estado((long)3,"Disponible"));
				actualizarEstadoAuto(selectedContrato.getAuto());
			}
			String response = (String)restService.DELETE("/api/v10/contrato/"+ selectedContrato.getId(), null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Contrato> getContratoList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Contrato> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v10/contrato", params, String.class).getBody();
		return apiRestMapper.mapList(response, Contrato.class);
	}

	public void actualizarEstadoAuto(Auto a) {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String response = (String)restService.PUT("/api/v8/auto", params, a, String.class, null).getBody();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}
	public boolean hayMismoAutoEnOtroContrato() throws IOException {
		boolean enc=false;

		contratoList=getContratoList();
		for(int i = 0; i< contratoList.size() && !enc;i++) {
			if(selectedContrato.getAuto().equals(contratoList.get(i).getAuto())) {
				enc = true;
			}
		}

		return enc;
	}
	
	public List<ContractXTourist> getContractXTourist () throws IOException
	{
		List<ContractXTourist> finalList = new ArrayList<ContractXTourist>();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Turista> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v9/turista", params, String.class).getBody();

		List<Turista> tourist = apiRestMapper.mapList(response, Turista.class);
		Iterator<Turista> i = tourist.iterator();

		while (i.hasNext()) {
			Turista turista = (Turista) i.next();
		    finalList.add(new ContractXTourist(turista.getNoPasaporte(), findContractByTourist(turista.getNoPasaporte()).size(),turista.getNombre()));

		}
		return finalList;
	}
	
	public List<Contrato> findContractByTourist (String idtourist) throws IOException
	{
		List<Contrato> ContractByTourist = new ArrayList<Contrato>();
		Iterator<Contrato> i = getContratoList().iterator();

		while(i.hasNext())
		{
			Contrato t = i.next();

			if(t.getTurista().getNoPasaporte().equals(idtourist))
			{
				ContractByTourist.add(t);
			}
		}

		
		return ContractByTourist;
	}
	public List<CantContratosXChofer> getCantContratosXChofer() throws IOException
	{
		List<CantContratosXChofer> finalList = new ArrayList<CantContratosXChofer>();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Chofer> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v6/chofer", params, String.class).getBody();

		List<Chofer> driver = apiRestMapper.mapList(response, Chofer.class);
		Iterator<Chofer> i = driver.iterator();

		while (i.hasNext()) {
			Chofer chofer = (Chofer) i.next();
			finalList.add(new CantContratosXChofer(chofer.getId(), findContractByDriver(chofer.getId()).size(), chofer.getNombre()));

		}
		return finalList;
	}
	
	 public List<Contrato> findContractByDriver(String idDriver) throws IOException
	{
		List<Contrato> ContractByDriver = new ArrayList<Contrato>();
		Iterator<Contrato> i = getContratoList().iterator();

		while(i.hasNext())
		{
			Contrato t = i.next();

			if(t.getChofer().getId().equals(idDriver) )
				ContractByDriver.add(t);
		}

		return ContractByDriver;
	}

	public void setContratoList(List<Contrato> contratoList) {
		this.contratoList = contratoList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabContratos() {
		return tabContratos;
	}

	public void setTabContratos(TabView tabContratos) {
		this.tabContratos = tabContratos;
	}

	public Contrato getSelectedContrato() {
		return selectedContrato;
	}

	public void setSelectedContrato(Contrato selectedContrato) {
		this.selectedContrato = selectedContrato;
	}


}
