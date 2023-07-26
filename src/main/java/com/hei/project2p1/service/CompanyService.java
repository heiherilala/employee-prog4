package com.hei.project2p1.service;

import com.hei.project2p1.modele.BoundedPageSize;
import com.hei.project2p1.modele.Company;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PageFromOne;
import com.hei.project2p1.modele.PhoneNumber;
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

    public Company getByOne(){

        Company company = Company.builder()
            .name("Mon Entreprise")
            .Description("Une entreprise créative et innovante")
            .logo(new byte[]{0x12, 0x34, 0x56, 0x78}) // Remplacez ceci avec vos propres données d'image (byte[])
            .Slogan("Innovation et Excellence")
            .address("123 Rue de la République")
            .email("contact@monentreprise.com")
            .stat("0123456789")
            .rcs("987654321")
            .phoneNumbers(new ArrayList<>())
            .build();
        PhoneNumber phoneNumber1 = PhoneNumber.builder()
            .number("0123456789")
            .company(company)
            .build();

        PhoneNumber phoneNumber2 = PhoneNumber.builder()
            .number("9876543210")
            .company(company)
            .build();
        company.getPhoneNumbers().add(phoneNumber1);
        company.getPhoneNumbers().add(phoneNumber2);


        List<Company> companies = companyRepository.findAll();
        if (companies.isEmpty()) {
            return company;
        }else {
            return companies.get(0);
        }
    }

}
