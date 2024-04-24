package com.springdata.springdatajpa;

import com.github.javafaker.Faker;
import com.springdata.springdatajpa.Student.Student;
import com.springdata.springdatajpa.Student.StudentRepository;
import com.springdata.springdatajpa.StudentCard.StudentCard;
import com.springdata.springdatajpa.StudentCard.StudentCardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentCardRepository StudentCardRepository , StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@alHoussam.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            StudentCard studentCard = new StudentCard("123456789123456",student);

            StudentCardRepository.save(studentCard);

            studentRepository.findById(1L)
                    .ifPresent(System.out::println);

            StudentCardRepository.findById(1L)
                    .ifPresent(System.out::println);

            StudentCardRepository.deleteById(1L);
        };
    }

    private void sortingAndPagination(StudentRepository studentRepository){
        PageRequest pageRequest = PageRequest.of(
                0
                ,5
                ,Sort.by("firstName").ascending());

        Page<Student> page= studentRepository.findAll(pageRequest);
        System.out.println(page);

    }

    private void sorting(StudentRepository studentRepository){
        Sort sort= Sort.by("firstName").ascending().and(Sort.by("age").descending());

        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName()+" "+student.getAge()));
    }


    private void generateRandomStudents(StudentRepository studentRepository){
        Faker faker = new Faker();
        for(int i=0; i<=100; i++){
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email= String.format("%s.%s@alHoussam.edu",firstName,lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17,55));
            studentRepository.save(student);
        }
    }


}


