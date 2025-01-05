package project.education.service;

import org.springframework.stereotype.Service;
import project.education.model.Task;
import project.education.model.Student;
import project.education.repository.TaskRepository;
import project.education.repository.StudentRepository;

import java.util.List;
import java.util.Set;

/**
 * Service for managing {@link Task} entities.
 * Provides business logic for handling tasks and their interactions with students.
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;

    /**
     * Constructs a new {@code TaskService} with the specified repositories.
     *
     * @param taskRepository    the repository for interacting with task data
     * @param studentRepository the repository for interacting with student data
     */
    public TaskService(TaskRepository taskRepository, StudentRepository studentRepository) {
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves tasks associated with a specific course.
     *
     * @param courseId the ID of the course
     * @return a list of tasks for the specified course
     */
    public List<Task> getTasksByCourseId(Long courseId) {
        return taskRepository.findByCourseId(courseId);
    }

    /**
     * Retrieves students by their IDs.
     *
     * @param studentIds the set of student IDs
     * @return a list of students with the specified IDs
     */
    public List<Student> getStudentsByIds(Set<Long> studentIds) {
        return studentRepository.findAllById(studentIds);
    }

    /**
     * Saves a new or updated task to the database.
     *
     * @param task the task to save
     * @return the saved task
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete
     */
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the task with the specified ID, or null if not found
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    /**
     * Deactivates a task, making it unavailable for students.
     *
     * @param id the ID of the task to deactivate
     * @throws RuntimeException if the task is not found
     */
    public void deactivateTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Завдання не знайдено!"));
        task.setActive(false);
        taskRepository.save(task);
    }

    /**
     * Activates a task, making it available for students.
     *
     * @param id the ID of the task to activate
     * @throws RuntimeException if the task is not found
     */
    public void activateTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Завдання не знайдено!"));
        task.setActive(true);
        taskRepository.save(task);
    }

    /**
     * Retrieves all tasks for a specific course.
     *
     * @param courseId the ID of the course
     * @return a list of tasks for the specified course
     */
    public List<Task> getTasksForCourse(Long courseId) {
        List<Task> tasks = taskRepository.findByCourseId(courseId);
        return tasks;
    }
}
