package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Mark {
    private UUID studentId;
    private int exerciseScore;
    private int activityScore;
    private int practiceScore;
    private int seminarScore;

    @Id
    private UUID Id;
    @OneToMany(mappedBy = "mark")
    private ArrayList<Chapter> chapters;
    @ManyToOne
    private Student student;

    public Mark(UUID studentId, UUID id) {
        this.studentId = studentId;
        Id = id;
    }

    public Mark() {

    }
}
