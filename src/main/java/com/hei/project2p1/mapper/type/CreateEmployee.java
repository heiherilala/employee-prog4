package com.hei.project2p1.mapper.type;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PhoneNumber;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class CreateEmployee {
  private String id;
  private String ref;
  private String firstName;
  private String lastName;
  private String birthDate;
  private MultipartFile image;
  private String address;
  private String personalEmail;
  private String professionalEmail;
  private String cinNumber;
  private String cinCreateDate;
  private String cinCreatePlace;
  private String position;
  private Integer childrenNumber;
  private String hireDate;
  private String departureDate;
  private String cnapsNumber;
  private String sex;
  private String csp;
  private List<PhoneNumber> phoneNumbers;
}