package entities;

import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Chapter {
    private String name;
    private UUID markID;
    private int exerciseScore;
    private int activityScore;
    private int seminarScore;
    private int practiseScore;

    @Id
    private UUID id;
    @OneToMany(mappedBy = "chapter")
    private ArrayList<Task> tasks;
    @ManyToOne
    private Mark mark;

    public Chapter(String name, UUID markID, UUID id) {
        this.name = name;
        this.markID = markID;
        this.id = id;
    }

    public Chapter() {

    }
}
