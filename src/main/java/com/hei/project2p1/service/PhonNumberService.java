package com.hei.project2p1.service;

import com.hei.project2p1.mapper.PhonNumberMapper;
import com.hei.project2p1.mapper.type.CreatePhoneNumber;
import com.hei.project2p1.modele.PhoneNumber;
import com.hei.project2p1.modele.type.PhoneNumberWithCode;
import com.hei.project2p1.repository.CompanyRepository;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.PhonNumberRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhonNumberService {
    private final PhonNumberRepository phonNumberRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final PhonNumberMapper phonNumberMapper;
    @Transactional
    public PhoneNumber CreateOnePhoneNumber (String number, String numberCode , String employeeId, String companyId)
        throws ParseException, IOException {
        List<PhoneNumber> phoneNumbersToCheque = phonNumberRepository.findAllByNumberAndNumberCode(number, numberCode);
        for (PhoneNumber phoneNumber:phoneNumbersToCheque) {
            if (employeeId != null) {
                if (phoneNumber.getCompany()!=null){ throw new RuntimeException("the pone number is used "); }
                if (phoneNumber.getEmployee()!=null){
                    if(phoneNumber.getEmployee().getId() != employeeId){
                        throw new RuntimeException("the phone number is used ");
                    }}
            } else if (companyId != null) {
                if (phoneNumber.getCompany()!=null){
                    if(!phoneNumber.getCompany().getId().equalsIgnoreCase(companyId)){
                        throw new RuntimeException("the phone number is used ");
                    }}
                if (phoneNumber.getEmployee()!=null){ throw new RuntimeException("the pone number is used "); }
            }
        }

        CreatePhoneNumber createPhoneNumber = new CreatePhoneNumber();
        createPhoneNumber.setNumber(number);
        createPhoneNumber.setNumberCode(numberCode);
        createPhoneNumber.setEmployeeId(employeeId);
        createPhoneNumber.setCompanyId(companyId);

        PhoneNumber addPhoneNumber = phonNumberMapper.toDomain(createPhoneNumber);

        return phonNumberRepository.save(addPhoneNumber);
    }
    @Transactional
    public List<PhoneNumber> CreateManyPhoneNumber (List<PhoneNumberWithCode> phoneNumberWithCodes, String employeeId, String companyId){
        System.out.println(phoneNumberWithCodes);
        if (companyId == null) {
            List<PhoneNumber> phoneNumberToDelete = phonNumberRepository.findAllByEmployee(employeeRepository.getReferenceById(employeeId));

            phonNumberRepository.deleteAll(phoneNumberToDelete);
        }
        if (employeeId == null) {
            List<PhoneNumber> phoneNumberToDelete = phonNumberRepository.findAllByCompany_Id(companyId);
            phonNumberRepository.deleteAll(phoneNumberToDelete);
        }
        return phoneNumberWithCodes.stream().map(phoneNumberWithCode -> {
            try {
                return CreateOnePhoneNumber(phoneNumberWithCode.getNumber(),phoneNumberWithCode.getNumberCode(), employeeId, companyId);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public List<PhoneNumber> findAllByCompany_Id(String companyId){
        return phonNumberRepository.findAllByCompany_Id(companyId);
    }
    public List<PhoneNumber> findAllByEmployee_Id(String employId){
        return phonNumberRepository.findAllByEmployee(employeeRepository.getReferenceById(employId));
    }

}
