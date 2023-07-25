package com.hei.project2p1.mapper;

import com.hei.project2p1.mapper.type.CreateEmployee;
import com.hei.project2p1.mapper.type.EmployeeView;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.utils.Utils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmployeeMapper {
  public Employee toDomain(CreateEmployee createEmployee) throws ParseException, IOException {
    Employee employee = Employee.builder()
        .birthDate(Utils.toDateYMD(createEmployee.getBirthDate()))
        .firstName(createEmployee.getFirstName())
        .lastName(createEmployee.getLastName())
        .address(createEmployee.getAddress())
        .csp(com.hei.project2p1.modele.Employee.csp.valueOf(createEmployee.getCsp()))
        .cinCreateDate(Utils.toDateYMD(createEmployee.getCinCreateDate()))
        .cinCreatePlace(createEmployee.getCinCreatePlace())
        .cinNumber(createEmployee.getCinNumber())
        .childrenNumber(createEmployee.getChildrenNumber())
        .cnapsNumber(createEmployee.getCnapsNumber())
        .sex(Employee.sex.valueOf(createEmployee.getSex()))
        .hireDate(Utils.toDateYMD(createEmployee.getHireDate()))
        .departureDate(Utils.toDateYMD(createEmployee.getDepartureDate()))
        .personalEmail(createEmployee.getPersonalEmail())
        .professionalEmail(createEmployee.getProfessionalEmail())
        .position(createEmployee.getPosition())
        .image((createEmployee.getImage().getBytes()))
        .build();
    if (createEmployee.getId() != null) {
      employee.setId(createEmployee.getId());
      employee.setRef(createEmployee.getRef());
    }
    return employee;
  }
  public EmployeeView toView(Employee createEmployee) {
    EmployeeView employeeView = new EmployeeView();
    employeeView.setId((createEmployee.getId()));
    employeeView.setRef(createEmployee.getRef());
    employeeView.setBirthDate((createEmployee.getBirthDate()));
    employeeView.setFirstName(createEmployee.getFirstName());
    employeeView.setLastName(createEmployee.getLastName());
    employeeView.setAddress(createEmployee.getAddress());
    employeeView.setCsp((createEmployee.getCsp().toString()));
    employeeView.setCinCreateDate((createEmployee.getCinCreateDate()));
    employeeView.setCinCreatePlace(createEmployee.getCinCreatePlace());
    employeeView.setCinNumber(createEmployee.getCinNumber());
    employeeView.setChildrenNumber(createEmployee.getChildrenNumber());
    employeeView.setCnapsNumber(createEmployee.getCnapsNumber());
    employeeView.setSex((createEmployee.getSex().toString()));
    employeeView.setHireDate((createEmployee.getHireDate()));
    employeeView.setDepartureDate((createEmployee.getDepartureDate()));
    employeeView.setPersonalEmail(createEmployee.getPersonalEmail());
    employeeView.setProfessionalEmail(createEmployee.getProfessionalEmail());
    employeeView.setPosition(createEmployee.getPosition());
    employeeView.setImage(Base64.encodeBase64String(createEmployee.getImage()));
    return employeeView;
  }


}
