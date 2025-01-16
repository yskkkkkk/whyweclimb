package com.whyweclimb.backend.domain.room.repo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whyweclimb.backend.entity.SimultaneousConnection;

// 동시 접속 관련
@Repository
public interface SimConnRepo extends JpaRepository<SimultaneousConnection, Integer> {
	Optional<SimultaneousConnection> findByConnectionDate(LocalDate connectionDate);
}
