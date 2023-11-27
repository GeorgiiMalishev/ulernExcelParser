package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Student {
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }

    private String firstname;
    private String lastname;
    private String email;
    private String group;

    @Id
    private UUID id;
    @OneToMany(mappedBy = "student")
    private ArrayList<Mark> marks;


    public Student(String firstname, String lastname, String email, UUID id, String group) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id = id;
        this.group = group;
    }

    public Student() {

    }
}
