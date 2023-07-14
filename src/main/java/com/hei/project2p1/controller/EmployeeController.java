package com.hei.project2p1.controller;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
    public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(value = "/")
    public String index(HttpSession session, Model model) {
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees",employees);
        model.addAttribute("employee", new Employee());
        return "index";
    }

    @GetMapping("/employee/{id}")
    public String showEmployeeDetails(@PathVariable("id") String id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee-details";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@RequestParam("image") MultipartFile image,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("birthDate") String birthDate) throws ParseException, IOException {


        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        Employee employee = Employee.builder().birthDate(date).firstName(firstName).lastName(lastName).build();
        if (!image.isEmpty()) {
            employee.setImage(image.getBytes());
        }
        employeeService.saveEmployees(List.of(employee));
        return "redirect:/";
    }

    @GetMapping("/employee/{id}/image")
    @ResponseBody
    public ResponseEntity<byte[]> getEmployeeImage(@PathVariable("id") String id) {
        Employee employee = employeeService.getEmployeeById(id);
        byte[] image = employee.getImage();;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    }
