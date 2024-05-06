package com.springdata.springdatajpa.Course;

import com.springdata.springdatajpa.Enrolment.Enrolment;
import com.springdata.springdatajpa.Student.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "course")
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_id",
            sequenceName = "course_id",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_id"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;

    @Column(
            name = "department",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String department;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "course"
    )
    private List<Enrolment> enrolments= new ArrayList<>();


    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public Course(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void addEnrolment(Enrolment enrolment){
        if(!enrolments.contains(enrolment)){
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment){
        enrolments.remove(enrolment);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
