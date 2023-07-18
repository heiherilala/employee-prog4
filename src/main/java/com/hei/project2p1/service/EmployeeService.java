package com.hei.project2p1.service;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.RefIncrementation;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.IncrementationRepository;
import com.hei.project2p1.utils.Utils;
import com.hei.project2p1.validator.CreateEmployeeValidator;
import jakarta.servlet.http.HttpSession;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final Utils utils;
    private final CreateEmployeeValidator createEmployeeValidator;
    public List<Employee> getEmployeesFromSession(HttpSession session) {
        List<Employee> employees = (List<Employee>) session.getAttribute("employees");
        if (employees == null) {
            employees = new ArrayList<>();
            session.setAttribute("employees", employees);
        }
        return employees;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> saveEmployees(List<Employee> employees) {
        createEmployeeValidator.accept(employees.get(0));
        return employeeRepository.saveAll(employees.stream().map(utils::addRef).collect(Collectors.toList()));
    }

    public Employee getEmployeeById(String id){
        return employeeRepository.getReferenceById(id);
    }
}
