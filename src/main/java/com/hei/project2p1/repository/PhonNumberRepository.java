package com.hei.project2p1.repository;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PhoneNumber;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonNumberRepository extends JpaRepository<PhoneNumber, String> {
  List<PhoneNumber> findAllByNumber(String number);
  List<PhoneNumber> findAllByCompany_Id(String companyId);
  List<PhoneNumber> findAllByEmployee(Employee employee);


}
