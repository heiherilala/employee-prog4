package com.hei.project2p1.validator;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PhoneNumber;
import java.util.List;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class CreateEmployeeValidator implements Consumer<Employee> {
  @Override public void accept(Employee employee) {
    if (employee.getFirstName() == null) {
      throw new RuntimeException("firstName not null");
    }
    if (employee.getLastName() == null) {
      throw new RuntimeException("lastname not blanc");
    }
    if (employee.getFirstName() == "") {
      throw new RuntimeException("firstName not blanc");
    }
    if (employee.getLastName() == "") {
      throw new RuntimeException("lastname not null");
    }
    if (employee.getPhoneNumbers() != null) {
      List<PhoneNumber> phoneNumbers = employee.getPhoneNumbers();
      for (PhoneNumber p : phoneNumbers) {
        if (p.getNumber().length() != 10) {
          throw new RuntimeException("lastname not null");
        }
      }
    }
  }
}