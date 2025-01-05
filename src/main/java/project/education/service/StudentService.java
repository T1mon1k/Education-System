package project.education.service;

import org.springframework.stereotype.Service;
import project.education.model.Course;
import project.education.model.Student;
import project.education.repository.CourseRepository;
import project.education.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Студента з ID " + id + " не знайдено"));
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void enrollToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студента не знайдено"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не знайдено"));
        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            studentRepository.save(student);
        }
    }

    public List<Course> getAvailableCoursesForStudent(Long studentId) {
        Student student = getStudentById(studentId);
        List<Course> allCourses = courseRepository.findAll();
        allCourses.removeAll(student.getCourses());
        return allCourses;
    }

    public void leaveCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студента не знайдено"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не знайдено"));
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Студента з ID " + id + " не знайдено");
        }
    }
}
