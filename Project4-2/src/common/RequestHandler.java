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
	
	public TableModel databaseQuery(String query, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
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
		
		return resultSetToTableModel(resultSet);
	}
	
	public TableModel resultSetToTableModel(ResultSet rs) {
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
            e.printStackTrace();

            return null;
        }
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		StringBuilder outputData = new StringBuilder("<table><tr>");

		TableModel queryTable;
		response.setContentType("text/html");
		String input = request.getParameter("input");

		try {
			queryTable = databaseQuery(input, request, response);
			
			for(int cols =0; cols < queryTable.getColumnCount(); cols++)
			{
				outputData.append("<th>" + queryTable.getColumnName(cols) + "</th>");			}
			
			outputData.append("</tr></table>");
			//queryData.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: " + e.getMessage());
		}
		
		request.setAttribute("output", outputData);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	 }

}
