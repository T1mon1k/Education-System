package project.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.education.model.Student;

/**
 * Repository interface for managing {@link Student} entities.
 * Provides methods for interacting with the database.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
