import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import check.CheckForTask;
import connection.ConnectionProvider;
import task.Task;

@SuppressWarnings("serial")
@WebServlet("/addTask")
public class AddNewTask extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		
		// new task created.

		Task task = new Task(req.getParameter("newTask"), new Date().toString());

		// check if the record is already there or not.

		if (CheckForTask.checkForTask(req.getParameter("newTask"))) {

			// send a flag that will let me Show jsp know that the record is present.
			
			session.setAttribute("flag", "yes");
			// update the page
			updateThePage(req, resp);
			
		} else {

			// send task to database.

			addTaskToDB(task);
			
			// send flag for no duplicate.
			
			session.setAttribute("flag", "no");
			// update the page
			
			updateThePage(req, resp);
		}
	}

	private void updateThePage(HttpServletRequest req, HttpServletResponse resp) {

		try {

			Connection conn = ConnectionProvider.getConnection();

			Statement st = conn.createStatement();

			ArrayList<Task> tasklist = new ArrayList<>();

			ResultSet rs = st.executeQuery("select * from tasks");

			while (rs.next()) {
				tasklist.add(new Task(rs.getString("name"), rs.getString("time")));
			}

			req.setAttribute("tasklist", tasklist);
			

			RequestDispatcher rd = req.getRequestDispatcher("show.jsp");

			rd.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addTaskToDB(Task task) {

		try {
			Connection conn = ConnectionProvider.getConnection();

			Statement st = conn.createStatement();

			String sql = "insert into tasks values('" + task.getName() + "','" + task.getTime() + "')";

			st.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
