/* Name: Austin Lowe
Course: CNT 4714 – Spring 2018 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 1, 2018
*/
package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author Austin
 *
 */
public class RequestHandler extends HttpServlet{
	
	private static final long serialVersionUID = 3004342931828496260L;
	private Connection connection;
	
	public ResultSet databaseQuery(String query, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet resultSet = null;
		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("client");
		dataSource.setPassword("Password1");
		dataSource.setURL("jdbc:mysql://localhost:3306/project4");
		try {
			connection = dataSource.getConnection();

			}catch (Exception e){
				request.setAttribute("output",e.getMessage());
				request.getRequestDispatcher("/index.jsp").forward(request, response);System.err.println(e.getMessage());
			}
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
				request.setAttribute("output",e.getMessage());
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		
		return resultSet;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 response.setContentType("text/html");
		 String input = request.getParameter("input");
		 
		 request.setAttribute("output",databaseQuery(input, request, response));
		 request.getRequestDispatcher("/index.jsp").forward(request, response);
	 }

}
