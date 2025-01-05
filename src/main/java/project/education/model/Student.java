package project.education.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a student in the educational system.
 * A student can be enrolled in multiple courses.
 */
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    /**
     * Default constructor for JPA.
     */
    public Student() {}

    /**
     * Constructs a new student with the specified name and email.
     *
     * @param name  the name of the student
     * @param email the email of the student
     */
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the ID of the student.
     *
     * @return the ID of the student
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the student.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the student.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the student.
     *
     * @return the email of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the student.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the set of courses the student is enrolled in.
     *
     * @return the set of enrolled courses
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the set of courses the student is enrolled in.
     *
     * @param courses the set of courses to set
     */
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
