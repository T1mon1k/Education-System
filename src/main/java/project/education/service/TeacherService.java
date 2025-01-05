package project.education.service;

import org.springframework.stereotype.Service;
import project.education.model.Teacher;
import project.education.repository.TeacherRepository;

import java.util.List;

/**
 * Service for managing {@link Teacher} entities.
 * Provides business logic for handling teachers.
 */
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    /**
     * Constructs a new {@code TeacherService} with the specified repository.
     *
     * @param teacherRepository the repository for interacting with teacher data
     */
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Retrieves all teachers from the database.
     *
     * @return a list of all teachers
     */
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    /**
     * Saves a new or updated teacher to the database.
     *
     * @param teacher the teacher to save
     * @return the saved teacher
     */
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    /**
     * Deletes a teacher by their ID.
     *
     * @param id the ID of the teacher to delete
     */
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    /**
     * Retrieves a teacher by their ID.
     *
     * @param id the ID of the teacher
     * @return the teacher with the specified ID
     * @throws RuntimeException if the teacher is not found
     */
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Викладача не знайдено"));
    }
}
