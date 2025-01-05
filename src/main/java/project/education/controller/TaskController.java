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

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final CourseService courseService;

    public TaskController(TaskService taskService, CourseService courseService) {
        this.taskService = taskService;
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description, @RequestParam Long courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            Task task = new Task(title, description, course);
            taskService.saveTask(task);
        }
        return "redirect:/course/teacher/" + courseId;
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Long taskId, @RequestParam Long courseId) {
        taskService.deleteTask(taskId);
        return "redirect:/course/teacher/" + courseId;
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateTask(@PathVariable Long id, @RequestParam Long courseId) {
        taskService.deactivateTask(id);
        return "redirect:/course/teacher/" + courseId;
    }

    @PostMapping("/{id}/activate")
    public String activateTask(@PathVariable Long id, @RequestParam Long courseId) {
        taskService.activateTask(id);
        return "redirect:/course/teacher/" + courseId;
    }

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

    @GetMapping("/{taskId}/execute")
    public String executeTaskPage(@PathVariable Long taskId, @RequestParam Long studentId, @RequestParam Long courseId, Model model) {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("studentId", studentId);
        model.addAttribute("courseId", courseId);
        return "execute_task";
    }

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
