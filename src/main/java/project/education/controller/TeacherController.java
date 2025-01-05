package project.education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.education.model.Teacher;
import project.education.model.Course;
import project.education.service.TeacherService;
import project.education.service.CourseService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final CourseService courseService;

    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping
    public String getTeachersPage(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("courses", courseService.getAllCourses());
        return "teacher";
    }

    @GetMapping("/{id}")
    public String getTeacherAccount(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courseService.getCoursesByTeacherId(id));
        return "teacher_account";
    }

    @PostMapping("/add")
    public String addTeacher(@RequestParam String name, @RequestParam String email) {
        teacherService.saveTeacher(new Teacher(name, email));
        return "redirect:/teacher";
    }

    @PostMapping("/course/add")
    public String addCourse(@RequestParam String title, @RequestParam Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        if (teacher == null) {
            throw new RuntimeException("Викладач із таким ID не знайдений!");
        }
        Course course = new Course(title, teacher);
        courseService.saveCourse(course);
        return "redirect:/teacher";
    }

    @PostMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teacher";
    }

    @PostMapping("/{id}/course/add")
    public String addCourseToTeacher(@PathVariable Long id, @RequestParam String title) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            throw new RuntimeException("Викладач із таким ID не знайдений!");
        }
        Course course = new Course(title, teacher);
        courseService.saveCourse(course);
        return "redirect:/teacher/" + id;
    }

}
