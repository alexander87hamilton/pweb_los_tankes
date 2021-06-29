package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.cujae.pweb.los_tankes.domain.CantTuristasXPais;
import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.domain.Turista;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "turistaBean")
@ELBeanName(value = "turistaBean")
public class TuristaBean {

	@Autowired
	private RestService restService;

	private List<Turista> turistaList;
	private Turista turista;
	private Turista selectedTurista;
	private Date date;
	
	private TabView tabTuristas;
	private int activeIndexTab = 0;


	public TuristaBean() {
		turistaList = new ArrayList<>();
		turista = new Turista();
		selectedTurista = new Turista();
		date = new Date();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabTuristas.getChildren().remove(event.getTab());
	}

	public Turista getTurista() {
		return turista;
	}

	public void setTurista(Turista turista) {
		this.turista = turista;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void newTurista() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabTuristas, JsfUtils.getStringValueFromBundle("label_newTurista"), "/pages/classes/tourist/createTourist.xhtml", "turistaTabView");

	}

	public void editTurista() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabTuristas, JsfUtils.getStringValueFromBundle("label_editTurista"), "/pages/classes/tourist/editTourist.xhtml", "turistaTabView");
	}

		
	@SuppressWarnings("deprecation")
	public void save() {
		try {
			turista.setFechaNacimiento(new java.sql.Date(date.getYear(), date.getMonth(), date.getDate()));
			ApiRestMapper<Turista> apiRestMapper = new ApiRestMapper<>();
			String response = (String)restService.POST("/api/v9/turista", turista, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabTuristas);

			if(response.equalsIgnoreCase("500 null"))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_turistaPasaporteExistente")));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	@SuppressWarnings("deprecation")
	public void update() {
		try {
			selectedTurista.setFechaNacimiento(new java.sql.Date(date.getYear(), date.getMonth(), date.getDay()));
			ApiRestMapper<Turista> apiRestMapper = new ApiRestMapper<>();
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			
			String response = (String)restService.PUT("/api/v9/turista", params, selectedTurista, String.class, null).getBody();
			PrimeUtils.deleteSecondTab(tabTuristas);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			ApiRestMapper<Turista> apiRestMapper = new ApiRestMapper<>();
			String id = selectedTurista.getNoPasaporte();
			String response = (String)restService.DELETE("/api/v9/turista/"+ id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Turista> getTuristaList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		ApiRestMapper<Turista> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v9/turista", params, String.class).getBody();
		return apiRestMapper.mapList(response, Turista.class);
	}

	public List<Turista> completeTurista (String query) throws IOException {
		List<Turista> allTuristas = getTuristaList();
		List<Turista> filteredTuristas = new ArrayList<Turista>();

		for (int i = 0; i < allTuristas.size(); i++) {
			Turista t = allTuristas.get(i);
			if(t.getNombre().toLowerCase().startsWith(query)) {
				filteredTuristas.add(t);
			}
		}

		return filteredTuristas;
	}

	public List<CantTuristasXPais> getCantTuristasXPais () throws IOException
	{
		List<CantTuristasXPais> finalList = new ArrayList<CantTuristasXPais>();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Pais> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v2/pais", params, String.class).getBody();

		List<Pais> paises = apiRestMapper.mapList(response, Pais.class);
		Iterator<Pais> i = paises.iterator();

		while (i.hasNext()) {
			Pais pais = (Pais) i.next();
			finalList.add(new CantTuristasXPais(pais.getNombre(), findTuristasByPais(pais.getId()).size()));

		}
		return finalList;
	}

	public List<Turista> findTuristasByPais (Long idPais) throws IOException
	{
		List<Turista> turistasFromPais = new ArrayList<Turista>();
		Iterator<Turista> i = getTuristaList().iterator();

		while(i.hasNext())
		{
			Turista t = i.next();

			if(t.getPais().getId() == idPais)
				turistasFromPais.add(t);
		}

		return turistasFromPais;
	}
	
	public void setTuristaList(List<Turista> turistaList) {
		this.turistaList = turistaList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabTuristas() {
		return tabTuristas;
	}

	public void setTabTuristas(TabView tabTuristas) {
		this.tabTuristas = tabTuristas;
	}

	public Turista getSelectedTurista() {
		return selectedTurista;
	}

	public void setSelectedTurista(Turista selectedTurista) {
		this.selectedTurista = selectedTurista;
	}

}
