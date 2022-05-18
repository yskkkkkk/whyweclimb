package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.dto.UserRecordUpdateRequest;
import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.SingleConnectionRepository;
import com.whyweclimb.backend.domain.user.repo.SingleRecordRepository;
import com.whyweclimb.backend.domain.user.repo.UserRepository;
import com.whyweclimb.backend.entity.CumulativeConnection;
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

    private final UserRepository userRepository;
    private final SingleRecordRepository singleRecordRepository;
    private final SingleConnectionRepository singleConnectionRepository;
    
    @Transactional
    @Override
    public boolean setUserRecord(UserRecordUpdateRequest request) {
        if(request.getRecord() == 0){
            return false;
        }
        LocalDate date = LocalDate.now();
        Optional<User> userOptional = userRepository.findById(request.getUserSeq());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getMaxLevel() < request.getMaxLevel()){
                user.setMaxLevel(request.getMaxLevel());
                userRepository.save(user);
            }
        }else{
            return false;
        }
        Optional<SingleRecord> singleRecordOptional = singleRecordRepository.findByUserAndDate(userOptional.get(),date);
        if(singleRecordOptional.isPresent()){
            SingleRecord record = singleRecordOptional.get();
            if(record.getRecord() > request.getRecord()){
                record.setRecord(request.getRecord());
                singleRecordRepository.save(record);
            }
        }else{
            singleRecordRepository.save(SingleRecord.builder()
                    .user(userOptional.get())
                    .record(request.getRecord())
                    .date(date).build());
        }
        return true;
    }

	@Override
	public void countSingleModeEntrance() {
		LocalDate today = LocalDate.now();
		Optional<SingleConnection> connection = singleConnectionRepository.findByConnectionDate(today);
		if (connection.isPresent()) {
			singleConnectionRepository.save(SingleConnection.builder()
					.singleConnectionSeq(connection.get().getSingleConnectionSeq())
					.connectionCount(connection.get().getConnectionCount()+1)
					.connectionDate(connection.get().getConnectionDate())
					.build());
		}else {
			singleConnectionRepository.save(SingleConnection.builder()
					.connectionCount(1)
					.connectionDate(today)
					.build());
		}
	}
    
    
}
