package dy.whatsong.domain.streaming.application.impl;

import dy.whatsong.domain.streaming.application.service.RoomMemberService;
import dy.whatsong.domain.streaming.entity.redis.RoomMember;
import dy.whatsong.domain.streaming.repo.RoomMemberRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.webjars.NotFoundException;

import java.util.Optional;

@EssentialServiceLayer
@RequiredArgsConstructor
public class RoomMemberServiceImpl implements RoomMemberService {
    private final RoomMemberRepository roomMemberRepository;

    @Override
    public void saveRoomMemberInRedis(RoomMember roomMember){
        System.out.println("레디스 저장=회원:"+roomMember.getRoomCode());
        roomMemberRepository.save(roomMember);
    }

    @Override
    public Optional<RoomMember> getRoomMemberInfoByRoomCode(String roomCode){
        return roomMemberRepository.findById(roomCode);
    }

    @Override
    public void deleteRoomMemberInRedis(String roomCode) {
        roomMemberRepository.deleteById(roomCode);
    }
}
