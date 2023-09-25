package dy.whatsong.domain.streaming.application.service;

import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.streaming.entity.redis.RoomMember;

import java.util.ArrayList;
import java.util.Optional;

public interface RoomMemberService {

    void saveRoomMemberInRedis(String roomCode,String email);

    Optional<RoomMember> getRoomMemberInfoByRoomCode(String roomCode);

    void deleteRoomMemberInRedis(String roomCode);

    void modifyRoomMemberInRedis(String roomCode, ArrayList<MemberDto.MemberStomp> memberStomps);
}
