package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.dto.UserRecordUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.SingleConnRepo;
import com.whyweclimb.backend.domain.user.repo.SingleRecordRepo;
import com.whyweclimb.backend.domain.user.repo.UserRepo;
import com.whyweclimb.backend.entity.SingleConnection;
import com.whyweclimb.backend.entity.SingleRecord;
import com.whyweclimb.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SingleGameServiceImpl implements SingleGameService{

    private final UserRepo userRepo;
    private final SingleRecordRepo singleRecordRepo;
    private final SingleConnRepo singleConnRepo;

    @Transactional
    @Override
    public boolean setUserRecord(UserRecordUpdateRequest request) {
        LocalDate date = LocalDate.now();
        Optional<User> userOptional = userRepo.findById(request.getUserSeq());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getMaxLevel() < request.getMaxLevel()){
                user.setMaxLevel(request.getMaxLevel());
                userRepo.save(user);
            }
        }else{
            return false;
        }
        // 중도 퇴장 시 record 기록 X
        if(request.getRecord() == 0){
            return false;
        }
        Optional<SingleRecord> singleRecordOptional = singleRecordRepo.findByUserAndDate(userOptional.get(),date);
        if(singleRecordOptional.isPresent()){
            SingleRecord record = singleRecordOptional.get();
            if(record.getRecord() > request.getRecord()){
                record.setRecord(request.getRecord());
                singleRecordRepo.save(record);
            }
        }else{
            singleRecordRepo.save(SingleRecord.builder()
                    .user(userOptional.get())
                    .record(request.getRecord())
                    .date(date).build());
        }
        return true;
    }

	@Override
	public void countSingleModeEntrance() {
		LocalDate today = LocalDate.now();
		Optional<SingleConnection> connection = singleConnRepo.findByConnectionDate(today);
		if (connection.isPresent()) {
			singleConnRepo.save(SingleConnection.builder()
					.singleConnectionSeq(connection.get().getSingleConnectionSeq())
					.connectionCount(connection.get().getConnectionCount()+1)
					.connectionDate(connection.get().getConnectionDate())
					.build());
		}else {
			singleConnRepo.save(SingleConnection.builder()
					.connectionCount(1)
					.connectionDate(today)
					.build());
		}
	}
    
    
}
