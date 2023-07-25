package com.hei.project2p1.controller;

import com.hei.project2p1.mapper.EmployeeMapper;
import com.hei.project2p1.mapper.type.CreateEmployee;
import com.hei.project2p1.mapper.type.EmployeeView;
import com.hei.project2p1.mapper.type.FilterEmployee;
import com.hei.project2p1.modele.BoundedPageSize;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PageFromOne;
import com.hei.project2p1.modele.PhoneNumber;
import com.hei.project2p1.service.EmployeeService;
import com.hei.project2p1.service.PhonNumberService;
import com.hei.project2p1.utils.Utils;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private final PhonNumberService phonNumberService;
    @GetMapping(value = "/")
    public String index(HttpSession session, Model model) {
        List<EmployeeView> employees = employeeService.getEmployees().stream().map(employeeMapper::toView).collect(
            Collectors.toList());
        model.addAttribute("employees", employees);
        model.addAttribute("employee", new Employee());
        return "index";
    }

    @GetMapping(value = "/employees")
    public String filter(HttpSession session,
                         @RequestParam(name = "firstName", required = false) String firstName,
                         @RequestParam(name = "lastName", required = false) String lastName,
                         @RequestParam(name = "sex", required = false) String sex,
                         @RequestParam(name = "position", required = false) String position,
                         @RequestParam(name = "hireDateAfter", required = false) String hireDateAfter,
                         @RequestParam(name = "hireDateBefore", required = false) String hireDateBefore,
                         @RequestParam(name = "departureDateAfter", required = false) String departureDateAfter,
                         @RequestParam(name = "departureDateBefore", required = false) String departureDateBefore,
                         @RequestParam(name = "firstNameOrder", required = false) String firstNameOrder,
                         @RequestParam(name = "lastNameOrder", required = false) String lastNameOrder,
                         @RequestParam(name = "sexOrder", required = false) String sexOrder,
                         @RequestParam(name = "positionOrder", required = false) String positionOrder,
                         @RequestParam(name = "page", required = false) Integer page,
                         @RequestParam(name = "pageSize", required = false) Integer pageSize,
                         Model model) throws ParseException {
        FilterEmployee filterEmployee = new FilterEmployee();
        filterEmployee.setFirsName(firstName);
        filterEmployee.setSex(sex);
        filterEmployee.setLastName(lastName);
        filterEmployee.setPosition(position);
        filterEmployee.setPage(1);
        filterEmployee.setPageSize(10);
        PageFromOne pageFromOne = new PageFromOne(1);
        BoundedPageSize boundedPageSize = new BoundedPageSize(10);
        Employee.sex sex1Enum  = null;
        Date hireDateAfterDate = null;
        Date hireDateBeforeDate = null;
        Date departureDateAfterDate = null;
        Date departureDateBeforeDate = null;
        if (hireDateAfter != null ) {
            if (!hireDateAfter.isEmpty()) {
                hireDateAfterDate  = Utils.toDateYMD(hireDateAfter);
                filterEmployee.setHireDateAfter(Utils.toDateYMD(hireDateAfter));
            }
        }
        if (hireDateBefore != null ) {
            if (!hireDateBefore.isEmpty()) {
                hireDateBeforeDate  = Utils.toDateYMD(hireDateBefore);
                filterEmployee.setHireDateBefore(Utils.toDateYMD(hireDateBefore));
            }
        }
        if (departureDateAfter != null ) {
            if (!departureDateAfter.isEmpty()) {
                departureDateAfterDate  = Utils.toDateYMD(departureDateAfter);
                filterEmployee.setDepartureDateAfter(Utils.toDateYMD(departureDateAfter));
            }
        }
        if (departureDateBefore != null ) {
            if (!departureDateBefore.isEmpty()) {
                departureDateBeforeDate  = Utils.toDateYMD(departureDateBefore);
                filterEmployee.setDepartureDateBefore(Utils.toDateYMD(departureDateBefore));
            }
        }
        if (page != null) {
            filterEmployee.setPage(page);
            pageFromOne = new PageFromOne(page);
        }
        if (pageSize != null) {
            filterEmployee.setPageSize(pageSize);
            boundedPageSize = new BoundedPageSize(pageSize);
        }
        if (sex != null){
            if (sex.equals("M")) {
                sex1Enum = Employee.sex.valueOf(sex);
            }
            if (sex.equals("F")) {
                sex1Enum = Employee.sex.valueOf(sex);
            }
        }



        model.addAttribute("filterEmployee", filterEmployee);
        List<EmployeeView> employees = employeeService.getEmpoyeesWithfilter(
            firstName, lastName, sex1Enum, position, hireDateAfterDate, hireDateBeforeDate,
                departureDateAfterDate, departureDateBeforeDate, firstNameOrder,
                lastNameOrder, sexOrder, positionOrder , pageFromOne, boundedPageSize
            ).stream()
            .map(employeeMapper::toView).collect(
            Collectors.toList());
        model.addAttribute("employees", employees);

        return "filtered";
    }

    @GetMapping("/employees/create")
    public String addEmployeeUI(Model model) {
        model.addAttribute("createEmployee", new CreateEmployee());
        return "employee-add";
    }

    @GetMapping("/employees/{id}")
    public String showEmployeeDetails(@PathVariable("id") String id, Model model) {
        EmployeeView employee = employeeMapper.toView(employeeService.getEmployeeById(id));
        List<PhoneNumber> phoneNumbers = phonNumberService.findAllByEmployee_Id(employee.getId());
        model.addAttribute("employee", employee);
        model.addAttribute("phoneNumbers", phoneNumbers);
        return "employee-details";
    }

    @GetMapping("/employees/{id}/update")
    public String showEmployeeUpdate(@PathVariable("id") String id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("createEmployee", new CreateEmployee());
        model.addAttribute("employee", employee);
        return "employee-update";
    }

    @Transactional
    @PostMapping("/employeesAdd")
    public String addEmployee(
        @ModelAttribute("newEmployee") CreateEmployee createEmployee,
        @RequestParam("phoneNumbers") List<String> phoneNumbers,
        Model model
    ) throws ParseException, IOException {
        if (createEmployee.getId() == null || createEmployee.getId().equals("")) {
            Employee newEmployee = employeeService.saveEmployees(List.of(employeeMapper.toDomain(createEmployee))).get(0);
            phonNumberService.CreateManyPhoneNumber(phoneNumbers, newEmployee.getId(), null);
        } else {
            employeeService.updateEmployee(employeeMapper.toDomain(createEmployee));
            phonNumberService.CreateManyPhoneNumber(phoneNumbers, createEmployee.getId(), null);
        }

        return "redirect:/employees";
    }

}
