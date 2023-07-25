package com.hei.project2p1.repository;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.RefIncrementation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncrementationRepository extends JpaRepository<RefIncrementation, String> {
  @Query(
      value = "update ref_incrementetion set inccremeentaion_employee = inccremeentaion_employee + 1 ",
      nativeQuery = true)
  void incrementEmployeeRef();
}
