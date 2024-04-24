package com.springdata.springdatajpa.StudentCard;

import com.springdata.springdatajpa.Student.Student;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name= "student_card")
@Table(
        name="student_card",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_card_number_unique",columnNames = "card_number")
        }
)
public class StudentCard {

    @Id
    @SequenceGenerator(
            name = "student_card_sequence",
            sequenceName = "student_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_card_sequence"
    )
    @Column(
            name="id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name="card_number",
            nullable = false,
            length = 15
    )
    private String cardNumber;

    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
//          orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_fk"
            )
    )
    private Student student;

    public StudentCard(String cardNumber , Student student) {
        this.cardNumber = cardNumber;
        this.student=student;
    }

    public StudentCard() {

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCard that = (StudentCard) o;
        return Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public String toString() {
        return "StudentCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
