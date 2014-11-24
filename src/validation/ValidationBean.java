
package validation;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;

@SuppressWarnings("serial")
@ManagedBean( name="validationBean" )
public class ValidationBean implements Serializable
{
   private String firstName;
   private String lastName;
   private String email;
   private String team;
   private String activity;
   private Connection connection;
   private static Map<String,Object> activities;
   
   static
   {
	    activities = new LinkedHashMap<String,Object>();
	    activities.put("Football", "Football"); 
	    activities.put("Swimming", "Swimming");
	    activities.put("Volleyball", "Volleyball");
	    activities.put("Basketball", "Basketball");
	    activities.put("Fitness", "Fitness");
	    activities.put("Running", "Running");
	    activities.put("Tracking", "Tracking");
	}
   
   public ValidationBean()
   {
	   try 
	   {
		   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		   connection = DriverManager.getConnection("jdbc:sqlserver://DCSRV1.nwtraders.msft;user=application_login;password=1234567;database=AdventureWorks2008");
	   } 
	   catch (ClassNotFoundException e)
	   {		
		   e.printStackTrace();
	   } 
	   catch (SQLException e) 
	   {
		   e.printStackTrace();
	   }		
   }

   
   public String getActivity()
   {
	   return activity;
   }
   
   
   public void setActivity(String activity)
   {
	   this.activity = activity;
   }
   
   
   public String getFirstName()
   {
      return firstName;
   } 
   
   
   public void setFirstName( String FirstName )
   {
      this.firstName = FirstName;
   } 
   
   public String getLastName()
   {
	   return lastName;
   }
   
   public void setLastName(String LastName)
   {
	   this.lastName = LastName;
   }   
  
   public String getEmail()
   {
      return email;
   } 

   public void setEmail( String email )
   {
      this.email = email;
   } 
   
   public String getTeam()
   {
      return team;
   } 

   public void setTeam( String team )
   {
      this.team = team;
   } 
   
   
   public String saveSubscriber() throws SQLException
   {		   
	      	Statement sta;
	      	String Sql;
			try {
				sta = connection.createStatement();
				Sql = "select [Places Left] from Wellness.dbo.Places where Activity = " + "'" + getActivity() + "';";
				ResultSet result = sta.executeQuery(Sql);
				result.next();
				int places = result.getInt("Places Left");
				if(places <= 0)
				{
					return "noplaces";
				}
				
				if(isUnique() == false)
				{
					return "invalid";
				}
				
				Sql = "insert into Wellness.dbo.Subscribers values" + 
				"(" + 
				"'" + getFirstName() + "'," +
				"'" + getLastName() +  "'," + 
				"'" + getEmail() +      "'," +
				"'" + getTeam() +      "'," +
				"'" + getActivity() +   "'" +
				")";
				sta.executeUpdate(Sql);
				Sql = "update Wellness.dbo.Places " +
				"set [Places Left] = " + "(" + "select [Places Left] from Wellness.dbo.Places where Activity =" +
				"'" + getActivity() + "'" + ") - 1 " +
				"where Activity = " + "'" + getActivity() + "';";
				sta.executeUpdate(Sql);
				//Mail newMailMessage = new Mail(getEmail(), getActivity());
				//newMailMessage.send();
			} 			
			finally
			{
				connection.close();
			}
			
	   return "done";
	   
   }
   
   public boolean isUnique(){
	    Statement sta = null;
		try 
		{
			sta = connection.createStatement();
		} catch (SQLException e) 
		{

		}
		
		String Sql = "Select * from Wellness.dbo.Subscribers where Email = "+ "'" + getEmail() + "';";
		
		try 
		{
			ResultSet result = sta.executeQuery(Sql);
			while(result.next())
			{
				
				if(result.getString("Email").equals(getEmail()))
				{
					return false;
				}
			}
		} catch (SQLException e) 
		{
			return true;
		}
		
		return true;	   
   }
   
   public Map<String,Object> getAvailbleActivities() throws SQLException {
	   checkAvailableActivities();
	   return activities;
   }
   
   private void checkAvailableActivities() throws SQLException
   {
	   Statement sta = connection.createStatement();
	   ArrayList<String> forDelete = new ArrayList<>();
	   
	   for (Map.Entry<String, Object> entry : activities.entrySet()) {
		
		   String Sql = "select [Places Left] from Wellness.dbo.Places where Activity = " + "'" + entry.getKey() + "';";
		   ResultSet result = sta.executeQuery(Sql);
		   result.next();
		   int places = result.getInt("Places Left");
		   if(places <= 0)
		   {
				forDelete.add(entry.getKey());
		   }	   
	   }
	   
	   for(String a: forDelete)
	   {
		   if(activities.containsKey(a))
		   {
			   activities.remove(a);
		   }
	   }	   
   }   
} 


