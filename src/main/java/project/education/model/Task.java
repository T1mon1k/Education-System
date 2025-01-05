package project.education.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a task assigned to students within a course.
 * A task contains details such as title, description, status, and associated student responses.
 */
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

    /**
     * Default constructor for JPA.
     */
    public Task() {}

    /**
     * Constructs a new task with the specified title, description, and associated course.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param course      the course the task is associated with
     */
    public Task(String title, String description, Course course) {
        this.title = title;
        this.description = description;
        this.course = course;
    }

    /**
     * Gets the ID of the task.
     *
     * @return the ID of the task
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the task.
     *
     * @return the title of the task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if the task is active.
     *
     * @return true if the task is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the task.
     *
     * @param active true to activate the task, false to deactivate it
     */

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the course associated with the task.
     *
     * @return the course associated with the task
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course associated with the task.
     *
     * @param course the course to associate with the task
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets the map of student responses for the task.
     *
     * @return the map of student responses
     */
    public Map<Long, String> getStudentAnswers() {
        return studentAnswers;
    }

    /**
     * Sets the map of student responses for the task.
     *
     * @param studentAnswers the map of student responses to set
     */
    public void setStudentAnswers(Map<Long, String> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    /**
     * Adds a response for a specific student to the task.
     *
     * @param studentId the ID of the student
     * @param response  the response of the student
     */
    public void addStudentResponse(Long studentId, String response) {
        this.studentAnswers.put(studentId, response);
    }

    /**
     * Gets the map of student statuses for the task.
     *
     * @return the map of student statuses
     */
    public Map<Long, Boolean> getStudentStatuses() {
        return studentStatuses;
    }

    /**
     * Sets the map of student statuses for the task.
     *
     * @param studentStatuses the map of student statuses to set
     */
    public void setStudentStatuses(Map<Long, Boolean> studentStatuses) {
        this.studentStatuses = studentStatuses;
    }

    /**
     * Sets the status of a specific student for the task.
     *
     * @param studentId the ID of the student
     * @param status    the status to set (true for completed, false otherwise)
     */
    public void setStudentStatus(Long studentId, Boolean status) {
        this.studentStatuses.put(studentId, status);
    }

    /**
     * Gets the status of a specific student for the task.
     *
     * @param studentId the ID of the student
     * @return the status of the student (true for completed, false otherwise)
     */
    public Boolean getStudentStatus(Long studentId) {
        return this.studentStatuses.getOrDefault(studentId, false);
    }
}
