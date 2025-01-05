package project.education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.education.model.Student;
import project.education.service.CourseService;
import project.education.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String getStudentsPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "student";
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam String name, @RequestParam String email) {
        studentService.saveStudent(new Student(name, email));
        return "redirect:/student";
    }

    @GetMapping("/{id}")
    public String getStudentAccount(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("courses", student.getCourses());
        model.addAttribute("availableCourses", studentService.getAvailableCoursesForStudent(id));
        return "student_account";
    }

    @PostMapping("/{id}/enroll")
    public String enrollToCourse(@PathVariable Long id, @RequestParam Long courseId) {
        studentService.enrollToCourse(id, courseId);
        return "redirect:/student/" + id;
    }

    @PostMapping("/{id}/leave")
    public String leaveCourse(@PathVariable Long id, @RequestParam Long courseId) {
        studentService.leaveCourse(id, courseId);
        return "redirect:/student/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}
