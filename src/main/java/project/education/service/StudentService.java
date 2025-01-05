package project.education.service;

import org.springframework.stereotype.Service;
import project.education.model.Course;
import project.education.model.Student;
import project.education.repository.CourseRepository;
import project.education.repository.StudentRepository;

import java.util.List;

/**
 * Service for managing {@link Student} entities.
 * Provides business logic for handling students and their enrollment in courses.
 */
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    /**
     * Constructs a new {@code StudentService} with the specified repositories.
     *
     * @param studentRepository the repository for interacting with student data
     * @param courseRepository  the repository for interacting with course data
     */
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Retrieves all students from the database.
     *
     * @return a list of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param id the ID of the student
     * @return the student with the specified ID
     * @throws RuntimeException if the student is not found
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Студента з ID " + id + " не знайдено"));
    }

    /**
     * Saves a new or updated student to the database.
     *
     * @param student the student to save
     * @return the saved student
     */
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Enrolls a student in a specific course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @throws RuntimeException if the student or course is not found
     */
    public void enrollToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студента не знайдено"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не знайдено"));
        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            studentRepository.save(student);
        }
    }

    /**
     * Retrieves the list of courses available for a student to enroll in.
     *
     * @param studentId the ID of the student
     * @return a list of courses the student is not yet enrolled in
     */
    public List<Course> getAvailableCoursesForStudent(Long studentId) {
        Student student = getStudentById(studentId);
        List<Course> allCourses = courseRepository.findAll();
        allCourses.removeAll(student.getCourses());
        return allCourses;
    }

    /**
     * Removes a student from a specific course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @throws RuntimeException if the student or course is not found
     */
    public void leaveCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студента не знайдено"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не знайдено"));
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id the ID of the student to delete
     * @throws RuntimeException if the student is not found
     */
    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Студента з ID " + id + " не знайдено");
        }
    }
}
