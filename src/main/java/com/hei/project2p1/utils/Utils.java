package com.hei.project2p1.utils;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.RefIncrementation;
import com.hei.project2p1.repository.IncrementationRepository;
import com.hei.project2p1.validator.CreateEmployeeValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Utils {
    private final IncrementationRepository incrementationRepository;
    private final CreateEmployeeValidator createEmployeeValidator;
    public Employee addRef(Employee employee){
        createEmployeeValidator.accept(employee);
        List<RefIncrementation> refIncrementations =  incrementationRepository.findAll();
        if (refIncrementations.isEmpty()){
            incrementationRepository.save(RefIncrementation.builder().id("firstOne").inccremeentaionEmployee(0).build());
            refIncrementations =  incrementationRepository.findAll();
        }
        RefIncrementation refIncrementation = refIncrementations.get(0);
        refIncrementation.setInccremeentaionEmployee(refIncrementation.getInccremeentaionEmployee()+1);
        incrementationRepository.save(refIncrementation);
        String refIncrementetion = "ref" + refIncrementation.getInccremeentaionEmployee();
        employee.setRef(refIncrementetion);
        return employee;
    }
}
