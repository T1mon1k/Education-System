package project.education.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a course in the educational system.
 * A course is associated with a teacher and can have multiple students enrolled.
 */
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    /**
     * Default constructor for JPA.
     */
    public Course() {}

    /**
     * Constructs a new course with the specified title and teacher.
     *
     * @param title   the title of the course
     * @param teacher the teacher assigned to the course
     */
    public Course(String title, Teacher teacher) {
        this.title = title;
        this.teacher = teacher;
    }

    /**
     * Gets the ID of the course.
     *
     * @return the ID of the course
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the course.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the course.
     *
     * @return the title of the course
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the course.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the teacher assigned to the course.
     *
     * @return the teacher assigned to the course
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * Sets the teacher assigned to the course.
     *
     * @param teacher the teacher to set
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * Gets the set of students enrolled in the course.
     *
     * @return the set of enrolled students
     */
    public Set<Student> getStudents() {
        return students;
    }

    /**
     * Sets the set of students enrolled in the course.
     *
     * @param students the set of students to set
     */
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
