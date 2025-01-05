package project.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.education.model.Teacher;

/**
 * Repository interface for managing {@link Teacher} entities.
 * Provides methods for interacting with the database.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
