package project.education.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ElementCollection
    @CollectionTable(name = "student_answers", joinColumns = @JoinColumn(name = "task_id"))
    @MapKeyColumn(name = "student_id")
    @Column(name = "answer")
    private Map<Long, String> studentAnswers = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "student_task_statuses", joinColumns = @JoinColumn(name = "task_id"))
    @MapKeyColumn(name = "student_id")
    @Column(name = "status")
    private Map<Long, Boolean> studentStatuses = new HashMap<>();

    public Task() {}

    public Task(String title, String description, Course course) {
        this.title = title;
        this.description = description;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Map<Long, String> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(Map<Long, String> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public void addStudentResponse(Long studentId, String response) {
        this.studentAnswers.put(studentId, response);
    }

    public Map<Long, Boolean> getStudentStatuses() {
        return studentStatuses;
    }

    public Map<Long, String> getStudentResponses() {
        return studentAnswers;
    }

    public void setStudentStatuses(Map<Long, Boolean> studentStatuses) {
        this.studentStatuses = studentStatuses;
    }

    public void setStudentStatus(Long studentId, Boolean status) {
        this.studentStatuses.put(studentId, status);
    }

    public Boolean getStudentStatus(Long studentId) {
        return this.studentStatuses.getOrDefault(studentId, false);
    }
}
