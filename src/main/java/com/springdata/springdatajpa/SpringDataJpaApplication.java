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
          Student abir2 = new Student("abir","baaloul","abir.baaloul55@gmail.com",21);

          System.out.println("Adding houssam and abir and abir2");
          studentRepository.saveAll(List.of(houssam, abir,abir2));


          System.out.println("Finding sutdent with email : abir.baaloul5@gmail.com");
          studentRepository
                  .findStudentByEmail("abir.baaloul5@gmail.com")
                  .ifPresentOrElse(
                          System.out::println,
                          ()->System.out.println("Student with email : abir.baaloul5@gmail.com not found")
                  );

          studentRepository.findStudentByFirstNameEqualsAndAgeIsGreaterThanEqual(
                  "abir",
                  21
          ).forEach(System.out::println);

          studentRepository.chercherStudenatApartieDeSonNom("baaloul")
                  .forEach(System.out::println);

          studentRepository.chercherStudenatApartieDeSonPrenomNativeQuery("houssam")
                  .forEach(System.out::println);

          studentRepository.chercherStudenatApartieDeSonPrenomNativeQueryUsingNamedParam("houssam")
                  .forEach(System.out::println);

        };

        }


}


