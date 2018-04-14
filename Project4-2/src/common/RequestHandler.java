/**
 * 
 */
package common;

import java.io.IOException;
import java.sql.Connection;
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
	//private ResultSet resultSet;

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 response.setContentType("text/html");
		 String input = request.getParameter("input");
		 
		 MysqlDataSource dataSource = new MysqlDataSource();
		 dataSource.setUser("");
		 dataSource.setPassword("");
		 //CorrectIP
		 dataSource.setURL("jdbc:mysql://:3306/project4");
		 
		 try {
			 connection = dataSource.getConnection();
			 input = "Database connected!";
		 }catch (Exception e)
		 {
			 input = "Did not Connect";
		 }
		 request.setAttribute("output", input);
		 request.getRequestDispatcher("/index.jsp").forward(request, response);
	 }

}
