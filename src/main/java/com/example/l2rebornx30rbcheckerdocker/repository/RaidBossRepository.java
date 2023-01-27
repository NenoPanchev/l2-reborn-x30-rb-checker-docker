package com.example.l2rebornx30rbcheckerdocker.repository;

import com.example.l2rebornx30rbcheckerdocker.model.entity.RaidBoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaidBossRepository extends JpaRepository<RaidBoss, Long> {
    List<RaidBoss> findAllByOrderByRespawnEnd();
    boolean existsByName(String name);
    Optional<RaidBoss> findByName(String name);
}
