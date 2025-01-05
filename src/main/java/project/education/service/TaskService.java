package project.education.service;

import org.springframework.stereotype.Service;
import project.education.model.Task;
import project.education.model.Student;
import project.education.repository.TaskRepository;
import project.education.repository.StudentRepository;

import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;

    public TaskService(TaskRepository taskRepository, StudentRepository studentRepository) {
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
    }

    public List<Task> getTasksByCourseId(Long courseId) {
        return taskRepository.findByCourseId(courseId);
    }

    public List<Student> getStudentsByIds(Set<Long> studentIds) {
        return studentRepository.findAllById(studentIds);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deactivateTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Завдання не знайдено!"));
        task.setActive(false);
        taskRepository.save(task);
    }

    public void activateTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Завдання не знайдено!"));
        task.setActive(true);
        taskRepository.save(task);
    }

    public List<Task> getTasksForCourse(Long courseId) {
        List<Task> tasks = taskRepository.findByCourseId(courseId);
        return tasks;
    }
}
