package com.hei.project2p1.mapper.type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
public class EmployeeView {
    private String id;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String image;
    private String ref;
    private String address;
    private String personalEmail;
    private String professionalEmail;
    private String cinNumber;
    private Date cinCreateDate;
    private String cinCreatePlace;
    private String position;
    private Integer childrenNumber;
    private Date hireDate;
    private Date departureDate;
    private String cnapsNumber;
    private String sex;
    private String csp;
    private List<String> phoneNumbers;
}
