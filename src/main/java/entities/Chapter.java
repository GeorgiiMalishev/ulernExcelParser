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
        String insertDataSQL = "INSERT INTO chapter (chapter_id, course_id, chapter_title) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, courseId.toString());
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();
        }
        for (var task : tasks) {
            task.insertData(connection);
        }
    }
}
