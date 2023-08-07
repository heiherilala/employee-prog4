package com.hei.project2p1.service;

import com.hei.project2p1.modele.Company;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PhoneNumber;
import com.hei.project2p1.modele.SessionUser;
import com.hei.project2p1.repository.CompanyRepository;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.SessionUserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionUserService {
    private SessionUserRepository sessionUserRepository;
    private EmployeeRepository employeeRepository;
    public SessionUser CreateOrUpdateSessionUserService(String employeeId){
        List<SessionUser> sessionUsers = sessionUserRepository.findAllByEmployee_Id(employeeId);
        if (!sessionUsers.isEmpty()) {
            SessionUser sessionUser = sessionUsers.get(0);
            if (sessionUser.getExpered().isAfter(Instant.now())) {
                return sessionUser;
            }else {
                return sessionUserRepository.save(sessionUser.toBuilder().expered(Instant.now().plusSeconds(24*60*60)).build());
            }
        }
        return sessionUserRepository.save(new SessionUser().toBuilder().employee(employeeRepository.getReferenceById(employeeId)).expered(Instant.now().plusSeconds(24*60*60)).build()) ;
    };

    public SessionUser loging(String username, String password ){
        List<Employee> employees = employeeRepository.findAllByFirstName(username);
        System.out.println("first");
        if (employees.isEmpty()) {
            System.out.println("second0");
            throw new RuntimeException("that username doesn't exist");

        }else {
            Employee employee = employees.get(0);
            if (employee.getId().equals(password)) {
                System.out.println("second1");
                return CreateOrUpdateSessionUserService(employee.getId());

            }
            else {
                System.out.println("second2");
                throw new RuntimeException("wrong password");
            }
        }
    };
    public SessionUser getById(String id){
        return sessionUserRepository.getReferenceById(id);
    }

}
