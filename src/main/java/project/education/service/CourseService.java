package project.education.service;

import org.springframework.stereotype.Service;
import project.education.model.Course;
import project.education.repository.CourseRepository;

import java.util.List;

/**
 * Service for managing {@link Course} entities.
 * Provides business logic for handling courses.
 */
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    /**
     * Constructs a new {@code CourseService} with the specified repository.
     *
     * @param courseRepository the repository for interacting with course data
     */
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Retrieves all courses from the database.
     *
     * @return a list of all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param id the ID of the course
     * @return the course with the specified ID
     * @throws RuntimeException if the course is not found
     */
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Курс не знайдено"));
    }

    /**
     * Saves a new or updated course to the database.
     *
     * @param course the course to save
     * @return the saved course
     */
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Deletes a course by its ID.
     *
     * @param id the ID of the course to delete
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    /**
     * Retrieves all courses associated with a specific teacher.
     *
     * @param teacherId the ID of the teacher
     * @return a list of courses taught by the specified teacher
     */
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
}
