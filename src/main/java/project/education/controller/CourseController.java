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

/**
 * Controller for handling requests related to courses.
 * Provides functionality to manage courses, teachers, students, and tasks.
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final TaskService taskService;
    private final StudentService studentService;

    /**
     * Constructor to initialize the required services.
     *
     * @param courseService  service for managing courses
     * @param teacherService service for managing teachers
     * @param taskService    service for managing tasks
     * @param studentService service for managing students
     */
    public CourseController(CourseService courseService, TeacherService teacherService, TaskService taskService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.taskService = taskService;
        this.studentService = studentService;
    }

    /**
     * Retrieves the course page for a specific course.
     *
     * @param id    the ID of the course
     * @param model the model to populate with course and student data
     * @return the view name for the course page
     */
    @GetMapping("/{id}")
    public String getCoursePage(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("students", course.getStudents());
        return "student_courses";
    }

    /**
     * Adds a new course to the system.
     *
     * @param title     the title of the course
     * @param teacherId the ID of the teacher assigned to the course
     * @return a redirect to the teacher page
     */
    @PostMapping("/add")
    public String addCourse(@RequestParam String title, @RequestParam Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        courseService.saveCourse(new Course(title, teacher));
        return "redirect:/teacher";
    }

    /**
     * Deletes a specific course.
     *
     * @param courseId  the ID of the course to delete
     * @param teacherId the ID of the teacher associated with the course
     * @return a redirect to the teacher page for the given teacher
     */
    @PostMapping("/{courseId}/delete")
    public String deleteCourse(@PathVariable Long courseId, @RequestParam Long teacherId) {
        courseService.deleteCourse(courseId);
        return "redirect:/teacher/" + teacherId;
    }

    /**
     * Retrieves the course page for a specific student.
     *
     * @param courseId  the ID of the course
     * @param studentId the ID of the student
     * @param model     the model to populate with course, task, and student data
     * @return the view name for the student course page
     */
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

    /**
     * Retrieves the course page for a specific teacher.
     *
     * @param courseId the ID of the course
     * @param model    the model to populate with course, student, and task data
     * @return the view name for the teacher course page
     */
    @GetMapping("/teacher/{courseId}")
    public String getTeacherCoursePage(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("students", course.getStudents());
        model.addAttribute("tasks", taskService.getTasksByCourseId(courseId));
        return "teacher_courses";
    }

}
