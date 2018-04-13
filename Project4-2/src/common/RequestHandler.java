/**
 * 
 */
package common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Austin
 *
 */
public class RequestHandler extends HttpServlet{
	
	private static final long serialVersionUID = 3004342931828496260L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 response.setContentType("text/html");
		 
		 String input = request.getParameter("input");
		 
		 request.setAttribute("output", input);
		 request.getRequestDispatcher("/index.jsp").forward(request, response);
	 }

}
