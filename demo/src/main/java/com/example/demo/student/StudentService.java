package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service //same as @component, but easier to read
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student){
        Optional<Student> response = studentRepository.findStudentByEmail(student.getEmail());
        if(response.isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        boolean studentExists = studentRepository.existsById(id);
        if(!studentExists){
            throw new IllegalStateException(
                    "student with id " + id + " does not exist"
            );
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + id + " does not exist"
                ));

        if(name != null &&
                !name.isEmpty()){
            student.setName(name);
        }

        //ensure email has value and not already registered
        if(email != null &&
                !email.isEmpty()){
            if(studentRepository.findStudentByEmail(email).isPresent()){
                throw new IllegalStateException("email already taken");
            }
            student.setEmail(email);
        }

    }
}
