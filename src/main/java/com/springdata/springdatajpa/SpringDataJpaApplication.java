package com.springdata.springdatajpa;

import com.github.javafaker.Faker;
import com.springdata.springdatajpa.Book.Book;
import com.springdata.springdatajpa.Course.Course;
import com.springdata.springdatajpa.Enrolment.Enrolment;
import com.springdata.springdatajpa.Enrolment.EnrolmentId;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

            student.addBook(new Book("one piece", LocalDate.now().plusDays(2)));
            student.addBook(new Book("Naruto", LocalDate.now().plusDays(3)));
            student.addBook(new Book("Dragon Ball ", LocalDate.now()));

            student.setStudentCard(studentCard);

            Course CSCourse= new Course("Computer Science","IT");
            Course AmigosCourse= new Course("Amigoscode Spring Data Jpa","IT");
            Course CodeCourse= new Course("CodeEvolution","TECH");

            student.addEnrolment(new Enrolment(new EnrolmentId(student.getId(), CSCourse.getId()) ,student,CSCourse, LocalDateTime.now()));
            student.addEnrolment(new Enrolment(new EnrolmentId(student.getId(), AmigosCourse.getId()) ,student,AmigosCourse, LocalDateTime.now().minusDays(5)));
            student.addEnrolment(new Enrolment(new EnrolmentId(student.getId(), CodeCourse.getId()) ,student,CodeCourse, LocalDateTime.now().minusMonths(8)));



            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(s->{
                        System.out.println("fetch book lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book->{
                            System.out.println(
                                    s.getFirstName() + " borrwed  " + book.getBookName()
                            );
                        });
                    });
//
//            StudentCardRepository.findById(1L)
//                    .ifPresent(System.out::println);
//
//            StudentCardRepository.deleteById(1L);
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


