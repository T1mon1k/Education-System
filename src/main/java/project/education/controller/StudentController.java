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

/**
 * Controller for managing student-related operations.
 * Provides functionality for viewing, adding, updating, and deleting students,
 * as well as enrolling or unenrolling them in courses.
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    /**
     * Constructor to initialize the required services.
     *
     * @param studentService service for managing students
     * @param courseService  service for managing courses
     */
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Displays the page with the list of all students and available courses.
     *
     * @param model the model to populate with student and course data
     * @return the view name for the students page
     */
    @GetMapping
    public String getStudentsPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "student";
    }

    /**
     * Adds a new student to the system.
     *
     * @param name  the name of the student
     * @param email the email of the student
     * @return a redirect to the students page
     */
    @PostMapping("/add")
    public String addStudent(@RequestParam String name, @RequestParam String email) {
        studentService.saveStudent(new Student(name, email));
        return "redirect:/student";
    }

    /**
     * Displays the account page for a specific student.
     *
     * @param id    the ID of the student
     * @param model the model to populate with student and course data
     * @return the view name for the student account page
     */
    @GetMapping("/{id}")
    public String getStudentAccount(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("courses", student.getCourses());
        model.addAttribute("availableCourses", studentService.getAvailableCoursesForStudent(id));
        return "student_account";
    }

    /**
     * Enrolls a student in a specific course.
     *
     * @param id       the ID of the student
     * @param courseId the ID of the course
     * @return a redirect to the student's account page
     */
    @PostMapping("/{id}/enroll")
    public String enrollToCourse(@PathVariable Long id, @RequestParam Long courseId) {
        studentService.enrollToCourse(id, courseId);
        return "redirect:/student/" + id;
    }

    /**
     * Removes a student from a specific course.
     *
     * @param id       the ID of the student
     * @param courseId the ID of the course
     * @return a redirect to the student's account page
     */
    @PostMapping("/{id}/leave")
    public String leaveCourse(@PathVariable Long id, @RequestParam Long courseId) {
        studentService.leaveCourse(id, courseId);
        return "redirect:/student/" + id;
    }

    /**
     * Deletes a student from the system.
     *
     * @param id the ID of the student
     * @return a redirect to the students page
     */
    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}
