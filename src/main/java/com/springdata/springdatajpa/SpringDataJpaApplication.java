package com.springdata.springdatajpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
          Student houssam = new Student("houssam","baaloul","houssam.baaloul78@gmail.com",24);
          Student abir = new Student("abir","baaloul","abir.baaloul5@gmail.com",23);

          System.out.println("Adding houssam and abir");
          studentRepository.saveAll(List.of(houssam, abir));

          System.out.println("Number of students: ");
          System.out.println(studentRepository.count());

          studentRepository
                  .findById(2L)
                  .ifPresentOrElse(
                          System.out::println,
                          ()->System.out.println("Student with id 2 notFound")
                  );

            studentRepository
                    .findById(3L)
                    .ifPresentOrElse(
                            System.out::println,
                            ()->System.out.println("Student with id 3 notFound")
                    );

            System.out.println("Select all students");
            List<Student> students = studentRepository.findAll();
            students.forEach(System.out::println);

            System.out.println("Delete houssam");
            studentRepository.deleteById(1L);

            System.out.println("Number of students: ");
            System.out.println(studentRepository.count());

        };
    }


}
