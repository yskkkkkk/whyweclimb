package com.whyweclimb.backend.domain.user.repo;

import com.whyweclimb.backend.entity.SimultaneousConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SingleConnectionRepository extends JpaRepository<SimultaneousConnection, Integer> {
    Optional<SimultaneousConnection> findByConnectionDate(LocalDate date);
}
