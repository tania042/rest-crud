package pixel_academy.rest_crud_app_09.rest;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pixel_academy.rest_crud_app_09.entity.Student;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    //definim @PostConstructor ca sa incarcam studentii nostri o singura data
    @PostConstruct
    public void loadData() {

        theStudents = new ArrayList<>();


        theStudents.add(new Student("Munteanu", "Eugen"));
        theStudents.add(new Student("Ojog", "Maria"));
        theStudents.add(new Student("Gonzales", "Pedro"));
    }

    // definim endpoint-ul pentru /student
    @GetMapping("/students")
    public List<Student> getStudents() {

        return theStudents;

    }
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        // verificam din nou studentId si dimensiunea listei
        if ( (studentId >= theStudents.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        return theStudents.get(studentId);
    }

    // exception hadler

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex) {

        // crearea StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // returnarea ResponseEntity

        return  new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception ex) {

        // crearea StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Mesaj personalizat de erroare");
        error.setTimeStamp(System.currentTimeMillis());

        // returnarea ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
