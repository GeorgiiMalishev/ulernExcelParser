package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Student {
    private String firstname;
    private String lastname;
    private String email;
    private String group;
    private String id;
    private ArrayList<Course> courses = new ArrayList<Course>();

    private String studentVk;

    public Student(String firstname, String lastname, String email, String group, String id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.group = group;
        this.id = id;
    }

    public void addCourse(Course course){
        courses.add(course);
    }
    public Course getCourse(int index){
        return courses.get(index);
    }

    public void insertData(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO student (student_id, last_name, first_name, email, [group]) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, group);

            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
        for (var course : courses) {
            course.insertData(connection);
        }
    }
}
