package com.hei.project2p1.controller;

import com.hei.project2p1.mapper.EmployeeMapper;
import com.hei.project2p1.mapper.toCsv.ToCSVConvertisor;
import com.hei.project2p1.mapper.type.CreateEmployee;
import com.hei.project2p1.mapper.type.EmployeeView;
import com.hei.project2p1.mapper.type.FilterEmployee;
import com.hei.project2p1.modele.BoundedPageSize;
import com.hei.project2p1.modele.Company;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PageFromOne;
import com.hei.project2p1.modele.PhoneNumber;
import com.hei.project2p1.modele.SessionUser;
import com.hei.project2p1.modele.type.PhoneNumberWithCode;
import com.hei.project2p1.service.CompanyService;
import com.hei.project2p1.service.EmployeeService;
import com.hei.project2p1.service.PhonNumberService;
import com.hei.project2p1.service.SessionUserService;
import com.hei.project2p1.utils.Utils;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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
    private final CompanyService companyService;
    private final SessionUserService sessionUserService;

    @GetMapping("/company")
    public String viewCompanyDetails(Model model) {
        Company company = companyService.getByOne();
        model.addAttribute("company", company);

        return "company-details";
    }
    @GetMapping(value = "/")
    public String index(HttpSession session, Model model) {
        return "index";
    }

    @PostMapping("/loging")
    public String addEmployee(@RequestParam(name = "username", required = false) String username,
                              @RequestParam(name = "password", required = false) String password,
                              HttpSession session) {
        try {
            // Attempt to log in the user
            SessionUser sessionUser = sessionUserService.loging(username, password);

            // If the login is successful, store the user's ID in the session as "token"
            session.setAttribute("token", sessionUser.getId());

            // Redirect the user to the "/employees" page
            return "redirect:/employees";
        } catch (Exception e) {
            // Handle any exceptions that may occur during the login process
            // You can customize the error handling based on the specific exception types
            e.printStackTrace(); // You can log the error or display a friendly error message to the user
            return "redirect:/"; // Redirect the user to a login page with an error parameter
        }
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
                         @RequestParam(name = "numberCode", required = false) String numberCode,
                         @RequestParam(name = "page", required = false) Integer page,
                         @RequestParam(name = "pageSize", required = false) Integer pageSize,
                         Model model) throws ParseException {
        try {
            checkSession(session);
        }catch (Exception e) {
            return "redirect:/";
        }
        Company company = companyService.getByOne();
        model.addAttribute("company", company);

        FilterEmployee filterEmployee = new FilterEmployee();
        filterEmployee.setFirsName(firstName);
        filterEmployee.setSex(sex);
        filterEmployee.setLastName(lastName);
        filterEmployee.setNumberCode(numberCode);
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
                lastNameOrder, sexOrder, numberCode, positionOrder, pageFromOne, boundedPageSize
            ).stream()
            .map(employeeMapper::toView).collect(
            Collectors.toList());
        model.addAttribute("employees", employees);

        return "filtered";
    }

    @GetMapping(value = "/employeesConvertion")
    public ResponseEntity<ByteArrayResource> employeesConvertion(HttpSession session,
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
                         @RequestParam(name = "numberCode", required = false) String numberCode,
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
        List<EmployeeView> employeesView = employeeService.getEmpoyeesWithfilter(
                firstName, lastName, sex1Enum, position, hireDateAfterDate, hireDateBeforeDate,
                departureDateAfterDate, departureDateBeforeDate, firstNameOrder,
                lastNameOrder, sexOrder,  numberCode, positionOrder,  pageFromOne, boundedPageSize
            ).stream()
            .map(employeeMapper::toView).collect(
                Collectors.toList());

        List<EmployeeView> employees = employeesView;
        employees = employees.stream().map(a->Utils.deletImage(a)).collect(Collectors.toList());
        String convertedString = ToCSVConvertisor.ObjectListToCsv(employees);

        byte[] convertedBytes = convertedString.getBytes();
        ByteArrayResource resource = new ByteArrayResource(convertedBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employeesDelailsList.csv");

        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource);
    }


    @GetMapping("/employees/create")
    public String addEmployeeUI(Model model,HttpSession session ) {
        try {
            checkSession(session);
        }catch (Exception e) {
            return "redirect:/";
        }
        model.addAttribute("createEmployee", new CreateEmployee());
        Company company = companyService.getByOne();
        model.addAttribute("company", company);
        return "employee-add";
    }

    @GetMapping("/employees/{id}")
    public String showEmployeeDetails(HttpSession session, @PathVariable("id") String id, Model model) {
        try {
            checkSession(session);
        }catch (Exception e) {
            return "redirect:/";
        }
        EmployeeView employee = employeeMapper.toView(employeeService.getEmployeeById(id));
        List<PhoneNumber> phoneNumbers = phonNumberService.findAllByEmployee_Id(employee.getId());
        model.addAttribute("employee", employee);
        model.addAttribute("phoneNumbers", phoneNumbers);
        Company company = companyService.getByOne();
        model.addAttribute("company", company);
        return "employee-details";
    }

    @GetMapping("/employees/{id}/update")
    public String showEmployeeUpdate(HttpSession session, @PathVariable("id") String id, Model model) {
        try {
            checkSession(session);
        }catch (Exception e) {
            return "redirect:/";
        }
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("createEmployee", new CreateEmployee());
        model.addAttribute("employee", employee);
        Company company = companyService.getByOne();
        model.addAttribute("company", company);
        return "employee-update";
    }

    @Transactional
    @PostMapping("/employeesAdd")
    public String addEmployee(
        @ModelAttribute("newEmployee") CreateEmployee createEmployee,
        @RequestParam("phoneNumbers") List<String> phoneNumbers,
        Model model
    ) throws ParseException, IOException {
        List<PhoneNumberWithCode> phoneNumberWithCodes = new ArrayList<>();
        for (String phoneNumber: phoneNumbers) {
            String[] phoneNumberList = phoneNumber.split(";");
            String numberCodePart = phoneNumberList[0];
            String numberPhonePart = phoneNumberList[1];
            phoneNumberWithCodes.add(new PhoneNumberWithCode(numberPhonePart,numberCodePart));
        }
        System.out.println(createEmployee);
        if (createEmployee.getId() == null || createEmployee.getId().equals("")) {
            Employee newEmployee = employeeService.saveEmployees(List.of(employeeMapper.toDomain(createEmployee))).get(0);
            phonNumberService.CreateManyPhoneNumber(phoneNumberWithCodes, newEmployee.getId(), null);
        } else {
            employeeService.updateEmployee(employeeMapper.toDomain(createEmployee));
            phonNumberService.CreateManyPhoneNumber(phoneNumberWithCodes, createEmployee.getId(), null);
        }
        Company company = companyService.getByOne();
        model.addAttribute("company", company);

        return "redirect:/employees";
    }


    private Void checkSession(HttpSession session){
        String token = (String) session.getAttribute("token");
        System.out.println(token);
        if (token == null) {
            throw new RuntimeException("forfiden");
        } else if (sessionUserService.getById(token) == null) {
            throw new RuntimeException("forfiden");
        }else if (sessionUserService.getById(token).getExpered().isBefore(Instant.now())) {
            throw new RuntimeException("forfiden");
        }
        return null;
    }
}
