package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.IOException;
import java.util.ArrayList;
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
import cu.cujae.pweb.los_tankes.domain.CantAutosXMarca;
import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Modelo;
import cu.cujae.pweb.los_tankes.service.ModeloService;
import cu.cujae.pweb.los_tankes.service.ModeloServiceImpl;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "autoBean")
@ELBeanName(value = "autoBean")
public class AutoBean {

	@Autowired
	private RestService restService;

	private List<Auto> autoList;
	private Auto auto;
	private Auto selectedAuto;
	private String marca;

	//binding view
	private TabView tabAutos;
	private int activeIndexTab = 0;


	public AutoBean() {		
		auto = new Auto();
		selectedAuto = new Auto();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabAutos.getChildren().remove(event.getTab());
	}

	public Auto getAuto() {
		return auto;
	}

	public void setAuto(Auto auto) {
		this.auto = auto;
	}


	public void newAuto() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabAutos, JsfUtils.getStringValueFromBundle("label_newAuto"), "/pages/classes/car/createCar.xhtml", "autoTabView");

	}

	public void editAuto() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabAutos, JsfUtils.getStringValueFromBundle("label_editAuto"), "/pages/classes/car/editCar.xhtml", "autoTabView");
	}

	//methods for save, update or delete the Auto	
	public void save() {
		try {
			String response = (String)restService.POST("/api/v8/auto", auto, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabAutos);

			if(response.equalsIgnoreCase("500 null"))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_autoPlacaExistente")));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			String response = (String)restService.PUT("/api/v8/auto", params, selectedAuto, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabAutos);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			String id = selectedAuto.getPlaca().toString();
			String response = (String)restService.DELETE("/api/v8/auto/"+ id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Auto> getAutoList() throws IOException {
		autoList = new ArrayList<>();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Auto> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v8/auto", params, String.class).getBody();
		return apiRestMapper.mapList(response, Auto.class);

	}

	public List<Auto> obtenerAutosDisponibles() throws IOException 
	{
		List<Auto> autoList = getAutoList();
		List<Auto> autosDisponibles = new ArrayList<Auto>();

		Iterator<Auto> i = autoList.iterator();

		while(i.hasNext()) 
		{
			Auto a = i.next();

			if(a.getEstado().getId() == 3)
				autosDisponibles.add(a);
		}

		return autosDisponibles;
	}

	public List<Auto> completeAuto (String query) throws IOException {
		List<Auto> allAutos = obtenerAutosDisponibles();
		List<Auto> filteredAutos = new ArrayList<Auto>();

		for (int i = 0; i < allAutos.size(); i++) {
			Auto a = allAutos.get(i);
			if(a.getModelo().getMarca().getNombre().toLowerCase().startsWith(query)) {
				filteredAutos.add(a);
			}
		}

		return filteredAutos;
	}

	public List<CantAutosXMarca> getCantAutosXMarca () throws IOException
	{
		List<CantAutosXMarca> finalList = new ArrayList<CantAutosXMarca>();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 	
		ApiRestMapper<Modelo> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v7/modelo", params, String.class).getBody();

		List<Modelo> modelos = apiRestMapper.mapList(response, Modelo.class);
		Iterator<Modelo> i = modelos.iterator();

		while (i.hasNext()) {
			Modelo modelo = (Modelo) i.next();
			finalList.add(new CantAutosXMarca(modelo.getMarca().getNombre() + " " + modelo.getNombre(), findAutosByMarca(modelo).size()));

		}
		return finalList;
	}

	public List<Auto> findAutosByMarca (Modelo modelo) throws IOException
	{
		List<Auto> autosXMarca = new ArrayList<Auto>();
		Iterator<Auto> i = getAutoList().iterator();

		while(i.hasNext())
		{
			Auto a = i.next();

			if(a.getModelo().equals(modelo))
				autosXMarca.add(a);
		}

		return autosXMarca;
	}
	public void setAutoList(List<Auto> autoList) {
		this.autoList = autoList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabAutos() {
		return tabAutos;
	}

	public void setTabAutos(TabView tabAutos) {
		this.tabAutos = tabAutos;
	}

	public Auto getSelectedAuto() {
		return selectedAuto;
	}

	public void setSelectedAuto(Auto selectedAuto) {
		this.selectedAuto = selectedAuto;
	}


}
