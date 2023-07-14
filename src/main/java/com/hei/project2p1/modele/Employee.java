package com.hei.project2p1.modele;

import jakarta.persistence.*;
import lombok.*;
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
}
