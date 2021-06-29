package cu.cujae.pweb.los_tankes.jsf.bean;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "languageBean")
@ELBeanName(value = "languageBean")
public class LanguageBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

	public Locale getLocale() {
		locale.getLanguage();
		
		return locale;
	}
	
	public String getLanguage() {
		return locale.getLanguage();
	}
	
	public void changeLanguage(String language) {
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
		
	}
	
	
	
}
