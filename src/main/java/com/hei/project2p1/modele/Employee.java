package com.hei.project2p1.modele;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "\"employee\"")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String firstName;
    private String lastName;
    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;
    private String ref;
    private String address;
    private String personalEmail;
    private String professionalEmail;
    private String cinNumber;
    private Date cinCreateDate;
    private String cinCreatePlace;
    private String position;
    private int childrenNumber;
    private Date hireDate;
    private Date departureDate;
    private String cnapsNumber;
    @OneToMany(mappedBy = "employee")
    private List<EmployeePhoneNumber> phoneNumbers;
    @Enumerated(EnumType.STRING)
    private sex sex;
    private csp csp;
    public enum sex {
        M, F
    }
    public enum csp{
        M1_1A, M2_1B, OS1_2A, OS2_2B, OS3_3, OP1, OP1A_3B, OP1B_4A, OP2B_5A, OP3_5B
    }
}
