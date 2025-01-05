package project.education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.education.model.Course;
import project.education.model.Task;
import project.education.model.Teacher;
import project.education.model.Student;
import project.education.service.CourseService;
import project.education.service.TeacherService;
import project.education.service.StudentService;
import project.education.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final TaskService taskService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, TeacherService teacherService, TaskService taskService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.taskService = taskService;
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public String getCoursePage(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("students", course.getStudents());
        return "student_courses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String title, @RequestParam Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        courseService.saveCourse(new Course(title, teacher));
        return "redirect:/teacher";
    }

    @PostMapping("/{courseId}/delete")
    public String deleteCourse(@PathVariable Long courseId, @RequestParam Long teacherId) {
        courseService.deleteCourse(courseId);
        return "redirect:/teacher/" + teacherId;
    }

    @GetMapping("/student/{courseId}")
    public String getStudentCoursePage(@PathVariable Long courseId, @RequestParam Long studentId, Model model) {
        Course course = courseService.getCourseById(courseId);
        List<Task> tasks = taskService.getTasksForCourse(courseId);
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("students", course.getStudents());
        model.addAttribute("studentId", studentId);
        return "student_courses";
    }

    @GetMapping("/teacher/{courseId}")
    public String getTeacherCoursePage(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("students", course.getStudents());
        model.addAttribute("tasks", taskService.getTasksByCourseId(courseId));
        return "teacher_courses";
    }

}
