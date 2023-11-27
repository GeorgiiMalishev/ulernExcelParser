package entities;

import java.util.UUID;
import javax.persistence.*;

@Entity
public class Task {
    private String name;
    private UUID chapterId;
    private int score;

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TaskType type;
    @ManyToOne
    private Chapter chapter;

    public Task(TaskType type, String name, UUID chapterId, UUID id) {
        this.type = type;
        this.name = name;
        this.chapterId = chapterId;
        this.id = id;
    }

    public Task() {

    }
}
