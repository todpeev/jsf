package administration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.sql.rowset.CachedRowSet;

@ManagedBean( name="subscribersBean" )
public class AdminPanel {

		   private String firstName;
		   private String lastName;
		   private String email;
		   private String team;
		   private String activity;
			   
		   public String getActivity()
		   {
			   return activity;
		   }
		   
		   public String getFirstName()
		   {
		      return firstName;
		   } 
		   
		   public String getLastName()
		   {
			   return lastName;
		   }
		  
		   public String getEmail()
		   {
		      return email;
		   } 
		   
		   public String getTeam()
		   {
		      return team;
		   } 
 		   
		@SuppressWarnings("resource")
		public ArrayList<Subscriber> getSubscribers() throws SQLException, ClassNotFoundException
		{
	
			   	   Connection connection = null;
			   	   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				   connection = DriverManager.getConnection("jdbc:sqlserver://DCSRV1.nwtraders.msft;user=application_login;password=1234567;database=AdventureWorks2008");
			
			   
			   		ArrayList<Subscriber> subs = new ArrayList<>();
				    CachedRowSet rowSet = null;
			      	try {
						PreparedStatement get = connection.prepareStatement("select * from Wellness.dbo.Subscribers order by Activity, First_Name");
			      		rowSet = new com.sun.rowset.CachedRowSetImpl();
			      		rowSet.populate(get.executeQuery());
			      		
			      		while(rowSet.next())
			      		{
			      			subs.add(new Subscriber(rowSet.getString("First_Name"), rowSet.getString("Last_Name"),  rowSet.getString("Email"),rowSet.getString("Team"),rowSet.getString("Activity")));
			      			
			      		}
			      		
			      		return subs;
					} catch (SQLException e) {
						System.out.println("Unable to connect to database");
						return null;
					} 
		   	}
		   
		  public String deleteSubscriber(Subscriber subscriber) throws SQLException, ClassNotFoundException
		  {
			   Connection connection = null;
		   	   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			   connection = DriverManager.getConnection("jdbc:sqlserver://DCSRV1.nwtraders.msft;user=application_login;password=1234567;database=AdventureWorks2008");

			   
		      	Statement sta;
				try {
					sta = connection.createStatement();
					String Sql = "delete from Wellness.dbo.Subscribers where Email = " + 
					"'" +
					subscriber.getEmail() +
					"'";
					sta.executeUpdate(Sql);
					
					Sql = "update Wellness.dbo.Places " +
							"set [Places Left] = " + "(" + "select [Places Left] from Wellness.dbo.Places where Activity =" +
							"'" + subscriber.getActivity() + "'" + ") + 1 " +
							"where Activity = " + "'" + subscriber.getActivity() + "';";
					sta.executeUpdate(Sql);
				} 			
				finally
				{
					connection.close();
				}
			  
			  
			  return "admin_panel";
		  }
		  

	}
