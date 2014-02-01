package com.krissh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnectionTest  {

public static void main(String arg[]){
	testConnect();
}

public static void testConnect() {
String dbUrl = "jdbc:mysql://localhost/xbrlpro_new_copy";
String dbClass = "com.mysql.jdbc.Driver";
String query = "Select distinct(table_name) from INFORMATION_SCHEMA.TABLES";
String username = "root";
String password = "1234";
try {
Class.forName(dbClass);
Connection connection = DriverManager.getConnection(dbUrl,
username, password);
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(query);
while (resultSet.next()) {
String tableName = resultSet.getString(1);
System.out.println("Table name : " + tableName);
}
connection.close();
} catch (ClassNotFoundException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
}
}