package com.hei.project2p1.repository;

import com.hei.project2p1.modele.Employee;
import org.springframework.data.domain.Pageable;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
  List<Employee> findByFirstNameAndLastNameAndSexAndPositionAndHireDateAfterAndHireDateBeforeAndDepartureDateAfterAndDepartureDateBefore(
      String firsName, String lastName, String sex, String position, Date hireDateBefore, Date hireDateAfter, Date departureDateAfter, Date departureDateBefore, Pageable pageable
  );
}
