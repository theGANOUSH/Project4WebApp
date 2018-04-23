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
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author Austin
 *
 */
public class RequestHandler extends HttpServlet{
	
	private static final long serialVersionUID = 3004342931828496260L;
	private Connection connection;
	
	private TableModel databaseQuery(String query, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
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
		
		return resultSetToTableModel(resultSet, request, response);
	}
	
	private TableModel resultSetToTableModel(ResultSet rs, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<String>();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

            while (rs.next()) {
                Vector<Object> newRow = new Vector<Object>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
            
        } catch (Exception e) {
        	request.setAttribute("output", e.getMessage());
			request.getRequestDispatcher("/index.jsp").forward(request, response);

            return null;
        }
    }
	
	private StringBuilder getOutput(TableModel databaseInput)
	{
		StringBuilder output = new StringBuilder("<table><tr>");
		for(int cols =0; cols < databaseInput.getColumnCount(); cols++)
		{
			output.append("<th>" + databaseInput.getColumnName(cols) + "</th>");	
		}
		output.append("</tr>");
		//print table rows
		for(int row = 0; row < databaseInput.getRowCount(); row++)
		{
			output.append("<tr>");
			for(int col = 0;col < databaseInput.getColumnCount(); col++)
			{
				output.append("<td>" + databaseInput.getValueAt(row, col) + "</td>");
			}
			output.append("</tr>");
		}
		
		output.append("</table>");
		
		return output;
	}

	private String executeStatement(String input, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException 
	{ 
		StringBuilder output = new StringBuilder();
		int rowsUpdated = 0;
		
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
			rowsUpdated = statement.executeUpdate(input);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
				request.setAttribute("output",e.getMessage());
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		output.append("The statement Executed successfully.<br>" + rowsUpdated + " Row(s) Affected.");
		output.append("<br>Business Logic Detected! - Updating Supplier Status");
		
		
	
		return output.toString();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		StringBuilder outputData = new StringBuilder();
		TableModel queryTable;
		
		response.setContentType("text/html");
		String input = request.getParameter("input");
		
		if(null != request.getParameter("reset"))
		{
			outputData.setLength(0);
			request.setAttribute("output", outputData);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		if(null != request.getParameter("execute")) {
			try {
				if(input.contains("select") || input.contains("SELECT"))
				{
					queryTable = databaseQuery(input, request, response);
					
					outputData = getOutput(queryTable);
				}
				else 
				{
					outputData.append(executeStatement(input, request,response));
	
					outputData.append("<br>Business Logic updated");
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.setAttribute("output", e.getMessage());
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			request.setAttribute("output", outputData);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		 }
		
	}
}
