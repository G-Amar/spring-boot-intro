package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student bob = new Student(
                    "Bob",
                    "bob.mail.com",
                    LocalDate.of(2000, Month.JANUARY, 10)
            );

            Student ross = new Student(
                    "Ross",
                    "ross.mail.com",
                    LocalDate.of(1998, Month.MAY, 1)
            );

            studentRepository.saveAll(
                    List.of(bob, ross)
            );
        };
    }
}
