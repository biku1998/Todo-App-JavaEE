<%@page import="connection.ConnectionProvider"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="task.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Todo</title>
<style type="text/css">
.heading {
	font-family: ubuntu mono;
	font-size: 40px;
	color: Tomato;
}

.work {
	height: 500px;
	width: 900px;
	background-color: #2C3335;
	margin: 0 auto;
	overflow: scroll;
}

.add-task {
	height: 100px;
	width: 900px;
	background-color: #2C3335;
	margin: 0 auto;
	font-size: 30px;
	font-family: ubuntu mono;
	color: white;
}

.newTaskInput {
	height: 100px;
	width: 899px;
	background-color: #2C3335;
	margin: 0 auto;
	font-size: 50px;
	font-family: ubuntu mono;
	color: white;
}

.newTaskbtn {
	height: 50px;
	width: 500px;
	background-color: #AE1438;
	margin: 0 auto;
	font-size: 30px;
	font-family: ubuntu mono;
	color: white;
}

th, td {
	padding: 20px;
	height: 10px;
	width: 500px;
	text-align: center;
	font-size: 20px;
	font-family: ubuntu mono;
}

table {
	font-size: 30px;
	font-family: ubuntu mono;
	color: white;
}

.removebtn {
	background-color: #AE1438;
	color: white;
	height: 50px;
	width: 150px;
	font-size: 20px;
	font-family: ubuntu mono;
}
</style>
</head>
<body style="background-color: #DAE0E2;">
	<h1 class="heading" align="center">Make your life easy....</h1>

	<div class="work">
		<!-- Present task will show up here -->
		<table>
			<th>task</th>
			<th>date when added</th>
			<th>action</th>
			
				<%
				
				
				try
				{
					Connection conn = ConnectionProvider.getConnection();
					
					Statement st = conn.createStatement();
					
					ArrayList<Task> tasklist = new ArrayList<>();
					
					
					
					ResultSet rs = st.executeQuery("select * from tasks");
					
					while(rs.next())
					{
						tasklist.add(new Task(rs.getString("name"),rs.getString("time")));
					}
					
					for(Task t :tasklist){
						
						out.println("<tr>");
						out.println("<form action='delete' method='POST'>");
						out.println("<td># " + t.getName() + "</td>");
						out.println("<td>" + t.getTime() + "</td>");
						out.println("<input type ='hidden' name = 'taskToDel' value = '"+t.getName()+"'>");
						out.println("<td> <input type='submit' class = 'removebtn' value='remove'></td>");
						out.println("</form>");
						out.println("</tr>");
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				%>
			
		</table>
	</div>
	<div class="add-task">
		<!-- New task will be added here -->
		<form action="addTask" method="POST">
			<input class="newTaskInput" type="text" name="newTask" placeholder="enter new task to add.."
				required="required"><input type="submit" class="newTaskbtn"
				value="add task">
		</form>
	</div>
</body>
</html>







