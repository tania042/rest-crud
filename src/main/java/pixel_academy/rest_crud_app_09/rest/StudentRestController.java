package pixel_academy.rest_crud_app_09.rest;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
