package com.hei.project2p1.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "\"employee\"")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date phone_number;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;
}
