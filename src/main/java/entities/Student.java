package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstname;
    private String lastname;
    private String email;
    private String group;
    private String id;
    private ArrayList<Course> courses = new ArrayList<Course>();

    private String vkId;
    private String sex;
    private String country;
    private String city;
    private String birthday;
    private List<String> schools;

    public Student(String firstname, String lastname, String email, String group, String id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.group = group;
        this.id = id;
    }

    public void setVkInfo(String vkID, String sex, String country, String city, String birthday, List<String> schools){
        this.vkId = vkID;
        this.sex = sex;
        this.country = country;
        this.city = city;
        this.birthday = birthday;
        this.schools = schools;
    }
    public String getFirstname(){return firstname;}
    public String getLastname(){return lastname;}
    public void addCourse(Course course){
        courses.add(course);
    }
    public Course getCourse(int index){
        return courses.get(index);
    }
    public double getAverageScore(){
        double totalGrades = 0.0;
        for (Course course : courses) {
            totalGrades += course.getAllScore();
        }
        return totalGrades / courses.size();
    }

    public void insertData(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, lastname);
        preparedStatement.setString(3, firstname);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, group);
        preparedStatement.setString(6, vkId);
        preparedStatement.setString(7, sex);
        preparedStatement.setString(8, country);
        preparedStatement.setString(9, city);
        preparedStatement.setString(10, birthday);

        preparedStatement.executeUpdate();

        for (var course : courses) {
            course.insertData(connection);
        }
    }
}
