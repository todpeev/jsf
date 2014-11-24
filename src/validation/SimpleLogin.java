package validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean( name="SimpleLogin" )
@SessionScoped
public class SimpleLogin{
	private String loginname;
	private String password;
	private boolean isAuthenticated = false;
	
	public SimpleLogin(){}

	public String getLoginname(){
		return loginname;
	}

	public void setLoginname(String loginname){
		this.loginname = loginname;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}
	
	public boolean getIsAuthenticated()
	{
		return isAuthenticated;
	}
	
	public String CheckValidUser(){
        if(loginname.equals("admin") && 
        		password.equals("admin")){
        	isAuthenticated = true;
        	return "Admin/admin_panel";
		}
		else{
			isAuthenticated = false;
			return "fail";
		}
	}
}
