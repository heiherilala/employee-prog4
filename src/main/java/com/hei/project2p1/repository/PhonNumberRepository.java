package com.hei.project2p1.repository;

import com.hei.project2p1.modele.RefIncrementation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonNumberRepository extends JpaRepository<RefIncrementation, String> {

}
