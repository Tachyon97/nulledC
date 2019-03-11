package org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAcess {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void readDataBase() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?" + "user=root&password=");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from data");
			writeResultSet(resultSet);
			preparedStatement = connect.prepareStatement("insert into data values (default, ?, ?, ?)");
			preparedStatement.setString(1, "Ha");
			preparedStatement.setString(2, "1230000");
			preparedStatement.setInt(3, 123123);
			preparedStatement.setString(4, "God");
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("SELECT username, timeran, expgained, premium from data");

			writeMetaData(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("Columns at ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column: " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			String username = resultSet.getString("username");
			String timeran = resultSet.getString("timeran");
			String expgained = resultSet.getString("expgained");
			System.out.println(username);
			System.out.println(timeran);
			System.out.println(expgained);
		}
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}

	}
}
