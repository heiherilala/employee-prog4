package com.hei.project2p1.repository;

import com.hei.project2p1.modele.Company;
import com.hei.project2p1.modele.SessionUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionUserRepository extends JpaRepository<SessionUser, String> {
  public List<SessionUser> findAllByEmployee_Id(String employeeId);
}
