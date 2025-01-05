package project.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.education.model.Task;

import java.util.List;

/**
 * Repository interface for managing {@link Task} entities.
 * Provides methods for interacting with the database.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Finds all tasks associated with a specific course.
     *
     * @param courseId the ID of the course
     * @return a list of tasks belonging to the specified course
     */
    List<Task> findByCourseId(Long courseId);
}


