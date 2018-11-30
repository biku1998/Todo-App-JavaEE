package check;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import connection.ConnectionProvider;

public class CheckForTask {

	public static boolean checkForTask(String task)
	{
		
		try
		{
			Connection conn = ConnectionProvider.getConnection();
			
			Statement st = conn.createStatement();
			
			String sql = String.format("select * from tasks where name = '%s'", task);
			
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next())
			{
				return true;
			}
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
}
