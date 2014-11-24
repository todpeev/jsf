package validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;

@ManagedBean( name="places" )
public class PlacesLeft {

	 private Connection connection;
	 
	 
	 public PlacesLeft() throws ClassNotFoundException, SQLException
	 {
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		 connection = DriverManager.getConnection("jdbc:sqlserver://DCSRV1.nwtraders.msft;user=application_login;password=1234567;database=AdventureWorks2008");
	 }
	 
	 public int returnPlaces(String activity) throws SQLException
	 {
		 int places = 0;
		 Statement sta = connection.createStatement();;
	     String Sql = "select [Places Left] from Wellness.dbo.Places where Activity = " + "'" + activity + "';";;
	     ResultSet result = sta.executeQuery(Sql);
		 if(result.next())
		 {
			 places = result.getInt("Places Left");	 
		 }
		 
		 return places;
	 }
}
