package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.dto.UserRecordUpdateRequest;
import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.SingleConnectionRepository;
import com.whyweclimb.backend.domain.user.repo.SingleRecordRepository;
import com.whyweclimb.backend.domain.user.repo.UserRepository;
import com.whyweclimb.backend.entity.SimultaneousConnection;
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
    public boolean enterUser(int userSeq) {
        LocalDate date = LocalDate.now();
        Optional<SimultaneousConnection> simultaneousConnectionOptional = singleConnectionRepository.findByConnectionDate(date);
        if(simultaneousConnectionOptional.isPresent()){
            SimultaneousConnection simultaneousConnection = simultaneousConnectionOptional.get();
            simultaneousConnection.setConnectionCount(simultaneousConnection.getConnectionCount() + 1);
            singleConnectionRepository.save(simultaneousConnection);
        }else{
            singleConnectionRepository.save(SimultaneousConnection.builder()
                    .connectionCount(1)
                    .connectionDate(date)
                    .build());
        }
        return true;
    }

    @Transactional
    @Override
    public boolean setUserRecord(UserRecordUpdateRequest request) {
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
        // 중도 퇴장 시 record 기록 X
        if(request.getRecord() == 0){
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
}
