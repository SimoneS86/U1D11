package app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entità.Student;

public class Application {
	private static final String URL = "jdbc:postgresql://localhost:5432/d11?useSSL";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "1234";

	public static void main(String[] args) {
		try {

			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// INSERT
			Student newStudent = new Student("Wanna", "Marchi", "f", "2008-06-02", 9.1, 9.2, 9.3);
			insertStudent(connection, newStudent);

			// UPDATE
			int studentIdToUpdate = 1;
			Map<String, Object> updateFields = new HashMap<>();
			updateFields.put("avg", 2.0);
			updateFields.put("max_vote", 2.5);
			updateStudent(connection, studentIdToUpdate, updateFields);

			// DELETE
			int studentIdToDelete = 5;
			deleteStudent(connection, studentIdToDelete);

			// BEST STUDENT
			Student bestStudent = getBest(connection);
			System.out.println("Best Student: " + bestStudent.toString());

			// STUDENTS IN RANGE
			int minVote = 6;
			int maxVote = 9;
			List<Student> studentsInRange = getVoteRange(connection, minVote, maxVote);
			System.out.println("Students in Vote Range:");
			for (Student student : studentsInRange) {
				System.out.println(student.toString());
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertStudent(Connection connection, Student student) throws SQLException {
		String query = "INSERT INTO school_students (name, lastname, gender, birthday, avg, min_vote, max_vote) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, student.getName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getGender());
			statement.setDate(4, Date.valueOf(student.getBirthday()));
			statement.setDouble(5, student.getAvg());
			statement.setDouble(6, student.getMinVote());
			statement.setDouble(7, student.getMaxVote());

			statement.executeUpdate();
		}
	}

	public static void updateStudent(Connection connection, int id, Map<String, Object> updateFields)
			throws SQLException {
		String query = "UPDATE school_students SET ";

		boolean isFirstField = true;
		for (String field : updateFields.keySet()) {
			if (!isFirstField) {
				query += ", ";
			}
			query += field + " = ?";
			isFirstField = false;
		}

		query += " WHERE id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			int parameterIndex = 1;

			for (Object value : updateFields.values()) {
				statement.setObject(parameterIndex, value);
				parameterIndex++;
			}

			statement.setInt(parameterIndex, id);

			statement.executeUpdate();
		}
	}

	public static void deleteStudent(Connection connection, int id) throws SQLException {
		String query = "DELETE FROM school_students WHERE id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);

			statement.executeUpdate();
		}
	}

	public static Student getBest(Connection connection) throws SQLException {
		String query = "SELECT * FROM school_students ORDER BY avg DESC LIMIT 1";

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String lastName = resultSet.getString("lastname");
				String gender = resultSet.getString("gender");
				Date birthday = resultSet.getDate("birthday");
				double avg = resultSet.getDouble("avg");
				double minVote = resultSet.getDouble("min_vote");
				double maxVote = resultSet.getDouble("max_vote");

				return new Student(id, name, lastName, gender, birthday.toString(), avg, minVote, maxVote);
			}
		}

		return null;
	}

	public static List<Student> getVoteRange(Connection connection, int min, int max) throws SQLException {
		String query = "SELECT * FROM school_students WHERE min_vote >= ? AND max_vote <= ?";
		List<Student> studentsInRange = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, min);
			statement.setInt(2, max);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String lastName = resultSet.getString("lastname");
					String gender = resultSet.getString("gender");
					Date birthday = resultSet.getDate("birthday");
					double avg = resultSet.getDouble("avg");
					double minVote = resultSet.getDouble("min_vote");
					double maxVote = resultSet.getDouble("max_vote");

					Student student = new Student(id, name, lastName, gender, birthday.toString(), avg, minVote,
							maxVote);
					studentsInRange.add(student);
				}
			}
		}

		return studentsInRange;
	}

}
