package com.hei.project2p1.service;

import com.hei.project2p1.mapper.type.EmployeeView;
import com.hei.project2p1.modele.BoundedPageSize;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PageFromOne;
import com.hei.project2p1.modele.RefIncrementation;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.IncrementationRepository;
import com.hei.project2p1.repository.dao.EmployeeDao;
import com.hei.project2p1.utils.Utils;
import com.hei.project2p1.validator.CreateEmployeeValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.Date;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeDao employeeDao;
    private final Utils utils;

    public List<Employee> getEmpoyeesWithfilter ( String firsName,
                                                  String lastName,
                                                  Employee.sex sex,
                                                  String position,
                                                  Date hireDateAfter,
                                                  Date hireDateBefore,
                                                  Date departureDateAfter,
                                                  Date departureDateBefore,
                                                  String firstNameOrder,
                                                  String lastNameOrder,
                                                  String sexOrder,
                                                  String numberCode,
                                                  String positionOrder,
                                                  PageFromOne page,
                                                  BoundedPageSize pageSize) {
        Pageable pageable = PageRequest.of(
            page.getValue() - 1,
            pageSize.getValue(),
            Sort.by(ASC, "ref"));
        return employeeDao.findByCriteria(
            firsName, lastName, sex, position,
            hireDateBefore, hireDateAfter, departureDateAfter, departureDateBefore,
            firstNameOrder, lastNameOrder, sexOrder, numberCode, positionOrder,
            pageable
        );
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
    @Transactional
    public List<Employee> saveEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees.stream().map(utils::addRef).collect(Collectors.toList()));
    }
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Employee updateEmployee  = employeeRepository.getById(employee.getId());
// Assuming you have already created the updateEmployee object and retrieved the employee object from some source


// Update other attributes if they are not null or empty
        if (employee.getBirthDate() != null) {
            updateEmployee.setBirthDate(employee.getBirthDate());
        }

        if (employee.getFirstName() != null && !employee.getFirstName().isEmpty()) {
            updateEmployee.setFirstName(employee.getFirstName());
        }

        if (employee.getLastName() != null && !employee.getLastName().isEmpty()) {
            updateEmployee.setLastName(employee.getLastName());
        }

        if (employee.getImage() != null && employee.getImage().length != 0) {
            updateEmployee.setImage(employee.getImage());
        }

        if (employee.getAddress() != null && !employee.getAddress().isEmpty()) {
            updateEmployee.setAddress(employee.getAddress());
        }

        if (employee.getPersonalEmail() != null && !employee.getPersonalEmail().isEmpty()) {
            updateEmployee.setPersonalEmail(employee.getPersonalEmail());
        }

        if (employee.getProfessionalEmail() != null && !employee.getProfessionalEmail().isEmpty()) {
            updateEmployee.setProfessionalEmail(employee.getProfessionalEmail());
        }

        if (employee.getCinNumber() != null && !employee.getCinNumber().isEmpty()) {
            updateEmployee.setCinNumber(employee.getCinNumber());
        }

        if (employee.getCinCreateDate() != null) {
            updateEmployee.setCinCreateDate(employee.getCinCreateDate());
        }

        if (employee.getCinCreatePlace() != null && !employee.getCinCreatePlace().isEmpty()) {
            updateEmployee.setCinCreatePlace(employee.getCinCreatePlace());
        }

        if (employee.getPosition() != null && !employee.getPosition().isEmpty()) {
            updateEmployee.setPosition(employee.getPosition());
        }

        if (employee.getChildrenNumber() != null ) {
            updateEmployee.setChildrenNumber(employee.getChildrenNumber());
        }

        if (employee.getHireDate() != null) {
            updateEmployee.setHireDate(employee.getHireDate());
        }

        if (employee.getDepartureDate() != null) {
            updateEmployee.setDepartureDate(employee.getDepartureDate());
        }

        if (employee.getCnapsNumber() != null && !employee.getCnapsNumber().isEmpty()) {
            updateEmployee.setCnapsNumber(employee.getCnapsNumber());
        }

        if (employee.getSex() != null) {
            updateEmployee.setSex(employee.getSex());
        }

        if (employee.getCsp() != null) {
            updateEmployee.setCsp(employee.getCsp());
        }

        return employeeRepository.save(updateEmployee);
    }

    public boolean existById(String id){
        return employeeRepository.existsById(id);
    }

    public Employee getEmployeeById(String id){
        return employeeRepository.getReferenceById(id);
    }
}
