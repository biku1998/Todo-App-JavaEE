import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.ConnectionProvider;
import task.Task;

@SuppressWarnings("serial")
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		
		String taskToDelete = req.getParameter("taskToDel").toString();
		
		deleteAndRedirect(req,resp,taskToDelete);
	}

	private void deleteAndRedirect(HttpServletRequest req, HttpServletResponse resp, String taskToDelete) {
		
		try
		{
			
			
			Connection conn = ConnectionProvider.getConnection();
			
			Statement st = conn.createStatement();
			
			String sql = String.format("delete from tasks where name = '%s'", taskToDelete);
			
			st.execute(sql);
			
			ArrayList<Task> tasklist = new ArrayList<>();
			
			ResultSet rs = st.executeQuery("select * from tasks");
			
			while(rs.next())
			{
				tasklist.add(new Task(rs.getString("name"),rs.getString("time")));
			}
			
			req.setAttribute("tasklist", tasklist);
			
			HttpSession session = req.getSession();
			
			session.setAttribute("flag", "no");
			
			RequestDispatcher rd = req.getRequestDispatcher("show.jsp");
			
			rd.forward(req, resp);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
