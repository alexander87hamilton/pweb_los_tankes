package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.IOException;
import java.util.ArrayList;
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

import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.JsfUtils;
import cu.cujae.pweb.los_tankes.util.PrimeUtils;
import cu.cujae.pweb.los_tankes.util.RestService;

@Scope(value = "request")
@Component(value = "estadoBean")
@ELBeanName(value = "estadoBean")
public class EstadoBean {

	@Autowired
	private RestService restService;

	private List<Estado> estadoList;
	private Estado estado;
	private Estado selectedEstado;

	
	private TabView tabEstados;
	private int activeIndexTab = 0;


	public EstadoBean() {
		estadoList = new ArrayList<>();
		estado = new Estado();
		selectedEstado = new Estado();
	}

	@PostConstruct
	public void Init() throws IOException {

	}

	public void onTabClose(TabCloseEvent<?> event) {
		tabEstados.getChildren().remove(event.getTab());
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void newEstado() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabEstados, JsfUtils.getStringValueFromBundle("label_newEstado"), "/pages/classes/state/createState.xhtml", "estadoTabView");

	}

	public void editEstado() throws IOException {
		activeIndexTab = PrimeUtils.createTab(tabEstados, JsfUtils.getStringValueFromBundle("label_editEstado"), "/pages/classes/state/editState.xhtml", "estadoTabView");
	}

	
	public void save() {
		try {
			if(validateField(estado)) {
				PrimeUtils.deleteSecondTab(tabEstados);
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", 
								JsfUtils.getStringValueFromBundle("label_ErrorLabelEstadoNombre")));
			}
			else {
				String response = (String)restService.POST("/api/v4/estado", estado, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabEstados);
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", 
								JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}

	}

	public void update() {
		try {
			if(validateField(selectedEstado)) {
				PrimeUtils.deleteSecondTab(tabEstados);
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", 
								JsfUtils.getStringValueFromBundle("label_ErrorLabelEstadoNombre")));
			}
			else {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				String response = (String)restService.PUT("/api/v4/estado", params, selectedEstado, String.class, null).getBody();
				PrimeUtils.deleteSecondTab(tabEstados);
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", 
								JsfUtils.getStringValueFromBundle("label_operationOK")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public void delete() {
		try {
			String id = selectedEstado.getId().toString();
			String response = (String)restService.DELETE("/api/v4/estado/"+ id, null, String.class).getBody();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", JsfUtils.getStringValueFromBundle("label_operationOK")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", JsfUtils.getStringValueFromBundle("label_operationERROR")));
		}
	}

	public List<Estado> getEstadoList() throws IOException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Estado> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v4/estado", params, String.class).getBody();
		return apiRestMapper.mapList(response, Estado.class);
	}

	public boolean validateField(Estado e) throws IOException {
		boolean validate = false;
		estadoList=getEstadoList();
		for (int i = 0; i < estadoList.size() && !validate; i++) {
			if(estadoList.get(i).getNombre().equalsIgnoreCase(e.getNombre())) {
				validate = true;				
			}				
		}		
		return validate;
	}

	public Estado getEstadoById(Integer id) throws IOException {
		Iterator<Estado> i = getEstadoList().iterator();
		Estado estado = null;
		boolean found = false;

		while (i.hasNext() && !found) {
			Estado m = i.next();

			if(m.getId().intValue() == id)
			{
				estado = new Estado(m.getId(), m.getNombre());
				found = true;
			}
		}

		return estado;
	}

	public Estado getEstadoByName(String name) throws IOException {
		Iterator<Estado> i = getEstadoList().iterator();
		Estado estado = new Estado();
		boolean found = false;

		while (i.hasNext() && !found) {
			Estado m = i.next();

			if(m.getNombre().equalsIgnoreCase(name))
			{
				estado = m;
				found = true;
			}
		}
		return estado;
	}


	public void setEstadoList(List<Estado> estadoList) {
		this.estadoList = estadoList;
	}

	public int getActiveIndexTab() {
		return activeIndexTab;
	}

	public void setActiveIndexTab(int activeIndexTab) {
		this.activeIndexTab = activeIndexTab;
	}

	public TabView getTabEstados() {
		return tabEstados;
	}

	public void setTabEstados(TabView tabEstados) {
		this.tabEstados = tabEstados;
	}

	public Estado getSelectedEstado() {
		return selectedEstado;
	}

	public void setSelectedEstado(Estado selectedEstado) {
		this.selectedEstado = selectedEstado;
	}

}
