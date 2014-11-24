package administration;
		 public class Subscriber {
			  private String firstName;
			  private String lastName;
			  private String team;
			  private String email;
			  private String activity;
			  
			  public Subscriber(String fn, String ln, String em,  String t, String a)
			  {
				firstName = fn;
				lastName = ln;
				email =em;
				activity = a;
				team = t;
			  }
			  
			  public String getTeam()
			  {
				  return team;
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
			  
			  public String getActivity()
			  {
				  return activity;
			  }
			  
			  
			  
		  }