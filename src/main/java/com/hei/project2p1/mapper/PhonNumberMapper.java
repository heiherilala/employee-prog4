package com.hei.project2p1.mapper;

import com.hei.project2p1.mapper.type.CreateEmployee;
import com.hei.project2p1.mapper.type.CreatePhoneNumber;
import com.hei.project2p1.mapper.type.EmployeeView;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PhoneNumber;
import com.hei.project2p1.service.CompanyService;
import com.hei.project2p1.service.EmployeeService;
import com.hei.project2p1.utils.Utils;
import java.io.IOException;
import java.text.ParseException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhonNumberMapper {
  private CompanyService companyService;
  private EmployeeService employeeService;
  public PhoneNumber toDomain(CreatePhoneNumber createPhoneNumber) throws ParseException, IOException {
    PhoneNumber phoneNumber = PhoneNumber.builder()
        .id(createPhoneNumber.getId())
        .number(createPhoneNumber.getNumber())
        .numberCode(createPhoneNumber.getNumberCode())
        .build();
    if (createPhoneNumber.getId() == null) {
      PhoneNumber phoneNumberForId = new PhoneNumber();
      phoneNumber.setId(phoneNumberForId.getId());
    }
    if (createPhoneNumber.getCompanyId() != null) {
      phoneNumber.setCompany(companyService.getById(createPhoneNumber.getCompanyId()));
    }
    if (createPhoneNumber.getEmployeeId() != null) {
      phoneNumber.setEmployee(employeeService.getEmployeeById(createPhoneNumber.getEmployeeId()));
    }
    return phoneNumber;
  }

}
