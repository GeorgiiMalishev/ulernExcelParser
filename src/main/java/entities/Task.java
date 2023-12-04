package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Task {
    private UUID id;
    private UUID chapterId;
    private String name;
    private TaskType type;
    private int score;
    private final int columnIndex;

    public Task(String name, TaskType type, int i) {
        this.name = name;
        this.type = type;
        this.columnIndex = i;
    }

    public Task(Task pattern, UUID id, UUID chapterId, int score){
        this.name = pattern.name;
        this.type = pattern.type;
        this.columnIndex = pattern.columnIndex;
        this.id = id;
        this.chapterId = chapterId;
        this.score = score;
    }

    // геттеры и сеттеры

    public void insertData(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO task (task_id, chapter_id, task_title, task_type_id, score) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, chapterId.toString());
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, type.ordinal());
            preparedStatement.setInt(5, score);

            preparedStatement.executeUpdate();
        }
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
