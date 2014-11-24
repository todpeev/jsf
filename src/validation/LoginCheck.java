package validation;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
@ManagedBean(name = "check")
public class LoginCheck implements Serializable{

	@ManagedProperty(value="#{SimpleLogin}")
	private SimpleLogin login;
	
	public void setLogin(SimpleLogin login) {
		this.login = login;		
	}
	
	public void checkLogin(){
		FacesContext fc = FacesContext.getCurrentInstance();
		if(this.login.getIsAuthenticated() == false)
		{			
			ConfigurableNavigationHandler nav 
			   = (ConfigurableNavigationHandler) 
				fc.getApplication().getNavigationHandler();
			nav.performNavigation("/log_in");
		}
	}
}
