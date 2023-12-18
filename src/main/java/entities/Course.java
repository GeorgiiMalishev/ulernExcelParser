package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Course {
    private UUID id;
    private String studentId;
    private int exerciseScore;
    private int practiceScore;
    private int seminarScore;
    private int activityScore;
    private ArrayList<Chapter> chapters = new ArrayList<Chapter>();
    public Course(){}

    public Course(UUID id, String studentId, int exerciseScore, int practiceScore, int seminarScore, int activityScore) {
        this.id = id;
        this.studentId = studentId;
        this.exerciseScore = exerciseScore;
        this.practiceScore = practiceScore;
        this.seminarScore = seminarScore;
        this.activityScore = activityScore;
    }

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
    }
    public Chapter getChapter(int i){return chapters.get(i);}
    public int getChaptersSize(){return chapters.size();}
    public int getAllScore(){return exerciseScore + practiceScore + seminarScore + activityScore;}
    // конструкторы и геттеры/сеттеры

    // метод для вставки данных в таблицу
    public void insertData(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO course (course_id, student_id, exerciseScore, practiceScore, seminarScore, activityScore) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
        preparedStatement.setString(1, id.toString());
        preparedStatement.setString(2, studentId.toString());
        preparedStatement.setInt(3, exerciseScore);
        preparedStatement.setInt(4, practiceScore);
        preparedStatement.setInt(5, seminarScore);
        preparedStatement.setInt(6, activityScore);

        preparedStatement.executeUpdate();
        for (var chapter : chapters) {
            chapter.insertData(connection);
        }
    }
}

