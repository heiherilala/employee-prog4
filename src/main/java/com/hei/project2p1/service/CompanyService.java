package com.hei.project2p1.service;

import com.hei.project2p1.modele.BoundedPageSize;
import com.hei.project2p1.modele.Company;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PageFromOne;
import com.hei.project2p1.repository.CompanyRepository;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.dao.EmployeeDao;
import com.hei.project2p1.utils.Utils;
import com.hei.project2p1.validator.CreateEmployeeValidator;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@AllArgsConstructor
public class CompanyService {
    private CompanyRepository companyRepository;
    public Company getById(String id){
        return companyRepository.getReferenceById(id);
    }

}
