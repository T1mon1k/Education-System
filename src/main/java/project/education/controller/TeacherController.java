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

/**
 * Controller for managing teacher-related operations.
 * Provides functionality for viewing, adding, updating, and deleting teachers,
 * as well as managing courses assigned to teachers.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final CourseService courseService;

    /**
     * Constructor to initialize the required services.
     *
     * @param teacherService service for managing teachers
     * @param courseService  service for managing courses
     */
    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    /**
     * Displays the page with the list of all teachers and courses.
     *
     * @param model the model to populate with teacher and course data
     * @return the view name for the teachers page
     */
    @GetMapping
    public String getTeachersPage(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("courses", courseService.getAllCourses());
        return "teacher";
    }

    /**
     * Displays the account page for a specific teacher.
     *
     * @param id    the ID of the teacher
     * @param model the model to populate with teacher and course data
     * @return the view name for the teacher account page
     */
    @GetMapping("/{id}")
    public String getTeacherAccount(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courseService.getCoursesByTeacherId(id));
        return "teacher_account";
    }

    /**
     * Adds a new teacher to the system.
     *
     * @param name  the name of the teacher
     * @param email the email of the teacher
     * @return a redirect to the teachers page
     */
    @PostMapping("/add")
    public String addTeacher(@RequestParam String name, @RequestParam String email) {
        teacherService.saveTeacher(new Teacher(name, email));
        return "redirect:/teacher";
    }

    /**
     * Adds a new course and assigns it to a specific teacher.
     *
     * @param title     the title of the course
     * @param teacherId the ID of the teacher the course is assigned to
     * @return a redirect to the teachers page
     */
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

    /**
     * Deletes a specific teacher from the system.
     *
     * @param id the ID of the teacher to delete
     * @return a redirect to the teachers page
     */
    @PostMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teacher";
    }

    /**
     * Adds a new course to a specific teacher.
     *
     * @param id    the ID of the teacher
     * @param title the title of the course
     * @return a redirect to the teacher's account page
     */
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
