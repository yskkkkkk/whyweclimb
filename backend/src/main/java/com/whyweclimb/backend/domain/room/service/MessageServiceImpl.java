package com.whyweclimb.backend.domain.room.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.repo.AccessRedisRepo;
import com.whyweclimb.backend.domain.room.repo.CumConnRepo;
import com.whyweclimb.backend.domain.room.repo.RoomRepo;
import com.whyweclimb.backend.domain.room.repo.SimConnRepo;
import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.CumulativeConnection;
import com.whyweclimb.backend.entity.SimultaneousConnection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	private final AccessRedisRepo accessRedisRepo;
	private final RoomRepo roomRepo;
	private final SimConnRepo simConnRepo;
	private final CumConnRepo cumConnRepo;

	@Override
	@Transactional
	public void increaseNumberOfPeople(Access access) {
		countUpConnection(LocalDate.now());
		
		accessRedisRepo.save(pretreatment(access));
	}

	private Access pretreatment(Access access) {
		int max = 0;
		for (Access a : accessRedisRepo.findByRoomCode(access.getRoomCode())) {
			max = Math.max(max, a.getOrder());
		}
		access.setOrder(max+1);
		access.setReady(false);
		return access;
	}
	
	private void countUpConnection(LocalDate today) {
		Optional<CumulativeConnection> cumul = cumConnRepo.findByConnectionDate(today);
		if (cumul.isPresent()) {
			cumConnRepo.save(CumulativeConnection.builder()
					.cumulativeConnectionSeq(cumul.get().getCumulativeConnectionSeq())
					.connectionCount(cumul.get().getConnectionCount()+1)
					.connectionDate(cumul.get().getConnectionDate())
					.build());
		}else {
			cumConnRepo.save(CumulativeConnection.builder()
					.connectionCount(1)
					.connectionDate(today)
					.build());
		}

		long count = accessRedisRepo.count();

		Optional<SimultaneousConnection> simul = simConnRepo.findByConnectionDate(today);
		if (simul.isPresent()) {
			if(simul.get().getConnectionCount() < count) {
				simConnRepo.save(SimultaneousConnection.builder()
						.simultaneousConnectionSeq(simul.get().getSimultaneousConnectionSeq())
						.connectionCount(count)
						.connectionDate(simul.get().getConnectionDate())
						.build());
			}
		}else {
			simConnRepo.save(SimultaneousConnection.builder()
					.connectionCount(1L)
					.connectionDate(today)
					.build());
		}
	}
	

	@Override
	public void decreaseNumberOfPeople(String sessionId) {
		accessRedisRepo.deleteById(sessionId);
	}

	@Override
	public boolean roomStatus(String roomCode) {
		int now = accessRedisRepo.findByRoomCode(roomCode).size();
		int max = Objects.requireNonNull(roomRepo.findByRoomCode(roomCode).orElse(null)).getRoomMaxNum();

		return now < max;
	}

	@Override
	public List<Access> playerList(String roomCode) {
		List<Access> list = accessRedisRepo.findByRoomCode(roomCode);
		Collections.sort(list);
		return list;
	}

	@Override
	public String getReady(Integer userSeq){
		Access access = accessRedisRepo.findByUserSeq(userSeq).get();
		access.setReady(true);
		accessRedisRepo.save(access);
		
		return access.getRoomCode();
	}

	@Override
	public Access getAccess(String sessionId) {
		return accessRedisRepo.findBySessionId(sessionId);
	}
}
