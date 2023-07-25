package com.hei.project2p1.mapper;

import com.hei.project2p1.mapper.type.CreateEmployee;
import com.hei.project2p1.mapper.type.EmployeeView;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.utils.Utils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@AllArgsConstructor
public class EmployeeMapper {
  private final EmployeeRepository employeeRepository;

  public Employee toDomain(CreateEmployee createEmployee) throws ParseException, IOException {
    Employee employee = Employee.builder()
        .firstName(Utils.validateStringMy(createEmployee.getFirstName()))
        .lastName(Utils.validateStringMy(createEmployee.getLastName()))
        .address(Utils.validateStringMy(createEmployee.getAddress()))
        .csp(com.hei.project2p1.modele.Employee.csp.valueOf(createEmployee.getCsp()))
        .cinCreatePlace(Utils.validateStringMy(createEmployee.getCinCreatePlace()))
        .cinNumber(Utils.validateStringMy(createEmployee.getCinNumber()))
        .childrenNumber(createEmployee.getChildrenNumber())
        .cnapsNumber(Utils.validateStringMy(createEmployee.getCnapsNumber()))
        .sex(Employee.sex.valueOf(createEmployee.getSex()))
        .personalEmail(Utils.validateStringMy(createEmployee.getPersonalEmail()))
        .professionalEmail(Utils.validateStringMy(createEmployee.getProfessionalEmail()))
        .position(Utils.validateStringMy(createEmployee.getPosition()))
        .phoneNumbers((createEmployee.getPhoneNumbers()))
        .build();
    if (!createEmployee.getImage().isEmpty()) {
      employee.setImage(createEmployee.getImage().getBytes());
    }else if (createEmployee.getId() != null){
      Employee employeeNow = employeeRepository.getReferenceById(createEmployee.getId());
      employee.setImage(employeeNow.getImage());
    }
    if (createEmployee.getId() != null) {
      employee.setId(createEmployee.getId());
      employee.setRef(createEmployee.getRef());
    }
    if (Utils.validateStringMy(createEmployee.getBirthDate()) != null) {
      employee.setBirthDate(Utils.toDateYMD(createEmployee.getBirthDate()));
    }
    if (Utils.validateStringMy(createEmployee.getHireDate()) != null) {
      employee.setHireDate(Utils.toDateYMD(createEmployee.getHireDate()));
    }
    if (Utils.validateStringMy(createEmployee.getDepartureDate()) != null) {
      employee.setDepartureDate(Utils.toDateYMD(createEmployee.getDepartureDate()));
    }
    if (Utils.validateStringMy(createEmployee.getCinCreateDate()) != null) {
      employee.setCinCreateDate(Utils.toDateYMD(createEmployee.getCinCreateDate()));
    }
    return employee;
  }
  public EmployeeView toView(Employee createEmployee) {
    EmployeeView employeeView = new EmployeeView();
    employeeView.setId(Utils.validateStringMy(createEmployee.getId()));
    employeeView.setRef(Utils.validateStringMy(createEmployee.getRef()));
    employeeView.setBirthDate((createEmployee.getBirthDate()));
    employeeView.setFirstName(Utils.validateStringMy(createEmployee.getFirstName()));
    employeeView.setLastName(Utils.validateStringMy(createEmployee.getLastName()));
    employeeView.setAddress(Utils.validateStringMy(createEmployee.getAddress()));
    employeeView.setCsp(Utils.validateStringMy(createEmployee.getCsp().toString()));
    employeeView.setCinCreateDate((createEmployee.getCinCreateDate()));
    employeeView.setCinCreatePlace(Utils.validateStringMy(createEmployee.getCinCreatePlace()));
    employeeView.setCinNumber(Utils.validateStringMy(createEmployee.getCinNumber()));
    employeeView.setChildrenNumber(createEmployee.getChildrenNumber());
    employeeView.setCnapsNumber(Utils.validateStringMy(createEmployee.getCnapsNumber()));
    employeeView.setSex(Utils.validateStringMy(createEmployee.getSex().toString()));
    employeeView.setHireDate((createEmployee.getHireDate()));
    employeeView.setDepartureDate((createEmployee.getDepartureDate()));
    employeeView.setPersonalEmail(Utils.validateStringMy(createEmployee.getPersonalEmail()));
    employeeView.setProfessionalEmail(Utils.validateStringMy(createEmployee.getProfessionalEmail()));
    employeeView.setPosition(Utils.validateStringMy(createEmployee.getPosition()));
    employeeView.setImage(Base64.encodeBase64String(createEmployee.getImage()));
    return employeeView;
  }


}
