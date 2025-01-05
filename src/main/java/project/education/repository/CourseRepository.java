package project.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.education.model.Course;

import java.util.List;

/**
 * Repository interface for managing {@link Course} entities.
 * Provides methods for interacting with the database.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Finds all courses associated with a specific teacher.
     *
     * @param teacherId the ID of the teacher
     * @return a list of courses taught by the specified teacher
     */
    List<Course> findByTeacherId(Long teacherId);
}
