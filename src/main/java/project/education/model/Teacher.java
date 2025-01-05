package project.education.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a teacher in the educational system.
 * A teacher can have multiple courses assigned to them.
 */
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

    /**
     * Default constructor for JPA.
     */
    public Teacher() {}

    /**
     * Constructs a new teacher with the specified name and email.
     *
     * @param name  the name of the teacher
     * @param email the email of the teacher
     */
    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the ID of the teacher.
     *
     * @return the ID of the teacher
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the teacher.
     *
     * @param id the ID to set
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the teacher.
     *
     * @return the name of the teacher
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the teacher.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the teacher.
     *
     * @return the email of the teacher
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the teacher.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the set of courses assigned to the teacher.
     *
     * @return the set of assigned courses
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the set of courses assigned to the teacher.
     *
     * @param courses the set of courses to set
     */
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
