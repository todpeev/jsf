package administration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

@ManagedBean( name="search" )
public class SearchSubscribers {

	private String lastName;
	private static ArrayList<Subscriber> searchResults = new ArrayList<>();
	
	public void setLastName(String lastname)
	{		
		lastName = lastname;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public ArrayList<Subscriber> getSearchResults()
	{
		return searchResults;
	}
	
	public String search() throws ClassNotFoundException
	{
		searchResults.clear();
		Connection connection = null;
	   	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		try {
			connection = DriverManager.getConnection("jdbc:sqlserver://DCSRV1.nwtraders.msft;user=application_login;password=1234567;database=AdventureWorks2008");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement sta;
		
		try {
			sta = connection.createStatement();
			String Sql = "select * from Wellness.dbo.Subscribers where Last_Name = " + 
			"'" +
			getLastName() +
			"'";
			System.out.println(Sql);
			ResultSet rs = sta.executeQuery(Sql);
			
			while(rs.next())
			{
				searchResults.add(new Subscriber(rs.getString("First_Name"),rs.getString("Last_Name"),rs.getString("Email"),rs.getString("Team"), rs.getString("Activity")));
				
			}
			
		} catch (SQLException e) {
			return "nores";
		} 			
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return "search";
	}
}
