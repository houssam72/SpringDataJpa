package com.springdata.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentByFirstNameEqualsAndAgeIsGreaterThanEqual(String firstName,Integer age);

    @Query("SELECT s FROM Student s WHERE s.lastName=?1")
    List<Student> chercherStudenatApartieDeSonNom(String lastName);

    @Query(value = "select * from student where first_name=?1",nativeQuery = true)
    List<Student> chercherStudenatApartieDeSonPrenomNativeQuery(String firstName);

    @Query(value = "select * from student where first_name=:firstName",nativeQuery = true)
    List<Student> chercherStudenatApartieDeSonPrenomNativeQueryUsingNamedParam(@Param("firstName") String firstName);

}
