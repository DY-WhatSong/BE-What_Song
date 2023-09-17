package dy.whatsong.domain.streaming.application.impl;

import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.streaming.application.service.RoomMemberService;
import dy.whatsong.domain.streaming.entity.redis.RoomMember;
import dy.whatsong.domain.streaming.repo.RoomMemberRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EssentialServiceLayer
@RequiredArgsConstructor
public class RoomMemberServiceImpl implements RoomMemberService {

    private final RoomMemberRepository roomMemberRepository;

    private final MemberCheckService memberCheckService;
    @Override
    public void saveRoomMemberInRedis(String roomCode, String email) {
        System.out.println("!!!!!!!!!!!!!");
        Optional<RoomMember> findRM = getRoomMemberInfoByRoomCode(roomCode);
        MemberDto.MemberStomp findBySeqMember = memberCheckService.getInfoByMemberEmail(email).toStompDTO();
        if (findRM.isEmpty()) {
            System.out.println("empty");
            roomMemberRepository.save(RoomMember.builder()
                    .roomCode(roomCode)
                    .memberList(new ArrayList<>(List.of(findBySeqMember)))
                    .build()
            );
        } else {
            System.out.println("not empty");
            ArrayList<MemberDto.MemberStomp> originList = findRM.get().getMemberList();
            if (Optional.ofNullable(originList).isEmpty()) {
                roomMemberRepository.save(
                        RoomMember.builder()
                                .roomCode(roomCode)
                                .memberList(new ArrayList<>(List.of(findBySeqMember)))
                                .build()
                );
            } else {
                originList.add(findBySeqMember);
                roomMemberRepository.save(
                        RoomMember.builder()
                                .roomCode(roomCode)
                                .memberList(originList)
                                .build()
                );
            }
        }
    }

    @Override
    public Optional<RoomMember> getRoomMemberInfoByRoomCode(String roomCode){
        return roomMemberRepository.findById(roomCode);
    }

    @Override
    public void deleteRoomMemberInRedis(String roomCode) {
        roomMemberRepository.deleteById(roomCode);
    }

    @Override
    public void modifyRoomMemberInRedis(String roomCode, ArrayList<MemberDto.MemberStomp> memberStomps) {
        roomMemberRepository.save(RoomMember.builder()
                        .roomCode(roomCode)
                        .memberList(memberStomps)
                .build());
    }
}
