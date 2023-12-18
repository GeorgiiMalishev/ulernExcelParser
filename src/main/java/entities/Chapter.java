package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Chapter implements DatabaseStorable {
    private UUID id;
    private UUID courseId;
    private String name;

    private int exerciseScore;
    private int practiceScore;
    private int seminarScore;
    private int activityScore;

    public void setExerciseScore(int exerciseScore) {
        this.exerciseScore = exerciseScore;
    }

    public void setPracticeScore(int practiceScore) {
        this.practiceScore = practiceScore;
    }

    public void setSeminarScore(int seminarScore) {
        this.seminarScore = seminarScore;
    }

    public void setActivityScore(int activityScore) {
        this.activityScore = activityScore;
    }

    public ArrayList<Task> tasks = new ArrayList<Task>();

    public Chapter(String name) {
        this.name = name;
    }
    public Chapter(Chapter pattern, UUID id, UUID courseID){
        this.name = pattern.name;
        this.id = id;
        this.courseId = courseID;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void insertData(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO chapter (chapter_id, course_id, chapter_title, exerciseScore, practiceScore, seminarScore, activityScore) VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
        preparedStatement.setString(1, id.toString());
        preparedStatement.setString(2, courseId.toString());
        preparedStatement.setString(3, name);
        preparedStatement.setInt(4, exerciseScore);
        preparedStatement.setInt(5, practiceScore);
        preparedStatement.setInt(6, seminarScore);
        preparedStatement.setInt(7, activityScore);
        preparedStatement.executeUpdate();
        for (var task : tasks) {
            task.insertData(connection);
        }
    }
    public void addScore(Task task, int score){
        switch (task.getType()) {
            case Activity -> activityScore = score;
            case Seminar -> seminarScore = score;
            case Exercise -> exerciseScore = score;
            case Practise -> practiceScore = score;
        }
}}
