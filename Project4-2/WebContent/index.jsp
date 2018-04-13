<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<link href="project4.css" rel="stylesheet" type="text/css">
		<title>CNT4714 Remote Database Management System</title>
	</head>
	<body>
		<h1>Welcome to the Project 4 Remote Database Management System</h1>
		
		<p>You are connected to the Project 4 database.<br>
		Please enter any valid SQL query or update statement.<br>
		If no query/update command is given the Execute button will display all supplier information in the database.<br>
		All execution results will appear below.
		</p>
		<form action="RequestHandler" method="post">
			<textarea rows="8" cols="50"></textarea><br>
			<div id="buttons">
				<input type="submit" value="Execute Command"/>
				<input type="reset" value="Clear Form"/>
			</div>
		</form>
	</body>
	<footer>
		<h3>Database Results:</h3>
		<div id=results>
		
		</div>
	</footer>
</html>