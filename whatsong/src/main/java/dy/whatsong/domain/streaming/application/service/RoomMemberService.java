package dy.whatsong.domain.streaming.application.service;

import dy.whatsong.domain.streaming.entity.redis.RoomMember;

import java.util.Optional;

public interface RoomMemberService {

    void saveRoomMemberInRedis(RoomMember roomMember);

    Optional<RoomMember> getRoomMemberInfoByRoomCode(String roomCode);

    void deleteRoomMemberInRedis(String roomCode);
}
