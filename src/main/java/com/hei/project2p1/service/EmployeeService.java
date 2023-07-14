package com.hei.project2p1.service;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.RefIncrementation;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.IncrementationRepository;
import com.hei.project2p1.validator.CreateEmployeeValidator;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final IncrementationRepository incrementationRepository;
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
        List<RefIncrementation> refIncrementations =  incrementationRepository.findAll();
        if (refIncrementations.isEmpty()){
            incrementationRepository.save(RefIncrementation.builder().id("firstOne").inccremeentaionEmployee(0).build());
            refIncrementations =  incrementationRepository.findAll();
        }
        RefIncrementation refIncrementation = refIncrementations.get(0);
        refIncrementation.setInccremeentaionEmployee(refIncrementation.getInccremeentaionEmployee()+1);
        incrementationRepository.save(refIncrementation);
        String refIncrementetion = "ref" + refIncrementation.getInccremeentaionEmployee();
        Employee employee = employees.get(0);
        employee.setRef(refIncrementetion);
        return employeeRepository.saveAll(List.of(employee));
    }

    public Employee getEmployeeById(String id){
        return employeeRepository.getReferenceById(id);
    }
}
