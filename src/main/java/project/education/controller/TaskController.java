package project.education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.education.model.Course;
import project.education.model.Student;
import project.education.model.Task;
import project.education.service.CourseService;
import project.education.service.TaskService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller for handling operations related to tasks.
 * Provides functionality for adding, deleting, activating, deactivating tasks,
 * viewing student responses, and submitting task responses.
 */
@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final CourseService courseService;

    /**
     * Constructor to initialize the required services.
     *
     * @param taskService   service for managing tasks
     * @param courseService service for managing courses
     */
    public TaskController(TaskService taskService, CourseService courseService) {
        this.taskService = taskService;
        this.courseService = courseService;
    }

    /**
     * Adds a new task to a course.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param courseId    the ID of the course the task belongs to
     * @return a redirect to the course teacher page
     */
    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description, @RequestParam Long courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            Task task = new Task(title, description, course);
            taskService.saveTask(task);
        }
        return "redirect:/course/teacher/" + courseId;
    }

    /**
     * Deletes a specific task.
     *
     * @param taskId   the ID of the task to delete
     * @param courseId the ID of the course the task belongs to
     * @return a redirect to the course teacher page
     */
    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Long taskId, @RequestParam Long courseId) {
        taskService.deleteTask(taskId);
        return "redirect:/course/teacher/" + courseId;
    }

    /**
     * Deactivates a specific task, making it unavailable for students.
     *
     * @param id       the ID of the task to deactivate
     * @param courseId the ID of the course the task belongs to
     * @return a redirect to the course teacher page
     */
    @PostMapping("/{id}/deactivate")
    public String deactivateTask(@PathVariable Long id, @RequestParam Long courseId) {
        taskService.deactivateTask(id);
        return "redirect:/course/teacher/" + courseId;
    }

    /**
     * Activates a specific task, making it available for students.
     *
     * @param id       the ID of the task to activate
     * @param courseId the ID of the course the task belongs to
     * @return a redirect to the course teacher page
     */
    @PostMapping("/{id}/activate")
    public String activateTask(@PathVariable Long id, @RequestParam Long courseId) {
        taskService.activateTask(id);
        return "redirect:/course/teacher/" + courseId;
    }

    /**
     * Displays student responses to a specific task.
     *
     * @param taskId   the ID of the task
     * @param courseId the ID of the course the task belongs to
     * @param model    the model to populate with task and student response data
     * @return the view name for displaying task responses
     */
    @GetMapping("/{taskId}/responses")
    public String viewResponses(@PathVariable Long taskId, @RequestParam Long courseId, Model model) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            Map<Long, String> responses = task.getStudentAnswers();
            Set<Long> studentIds = responses.keySet();
            List<Student> students = taskService.getStudentsByIds(studentIds);
            model.addAttribute("responses", responses);
            model.addAttribute("students", students);
        }
        model.addAttribute("task", task);
        model.addAttribute("courseId", courseId);
        return "responses";
    }

    /**
     * Displays the page for a student to execute a specific task.
     *
     * @param taskId    the ID of the task
     * @param studentId the ID of the student
     * @param courseId  the ID of the course the task belongs to
     * @param model     the model to populate with task and student data
     * @return the view name for executing a task
     */
    @GetMapping("/{taskId}/execute")
    public String executeTaskPage(@PathVariable Long taskId, @RequestParam Long studentId, @RequestParam Long courseId, Model model) {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("studentId", studentId);
        model.addAttribute("courseId", courseId);
        return "execute_task";
    }

    /**
     * Submits a student's response to a specific task.
     *
     * @param taskId    the ID of the task
     * @param studentId the ID of the student
     * @param courseId  the ID of the course the task belongs to
     * @param answer    the student's response to the task
     * @return a redirect to the student's course page
     */
    @PostMapping("/{taskId}/submit")
    public String submitTask(@PathVariable Long taskId, @RequestParam Long studentId, @RequestParam Long courseId, @RequestParam String answer) {
        Task task = taskService.getTaskById(taskId);
        if (task != null && task.isActive()) {
            task.addStudentResponse(studentId, answer);
            task.setStudentStatus(studentId, true);
            taskService.saveTask(task);
        }
        return "redirect:/course/student/" + courseId + "?studentId=" + studentId;
    }
}
