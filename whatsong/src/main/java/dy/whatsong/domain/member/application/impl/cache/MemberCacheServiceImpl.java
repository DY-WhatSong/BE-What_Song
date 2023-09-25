package dy.whatsong.domain.member.application.impl.cache;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.MemberRequestCacheDTO;
import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.streaming.application.service.RoomMemberService;
import dy.whatsong.domain.streaming.entity.redis.RoomMember;
import dy.whatsong.domain.streaming.repo.RoomMemberRepository;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.webjars.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@EssentialServiceLayer
@RequiredArgsConstructor
@CacheConfig(cacheNames = "members")
public class MemberCacheServiceImpl implements MemberCacheService {

    private final MemberCheckService memberCheckService;

    private final RoomMemberService roomMemberService;

    @Override
    public void putMemberInCacheIfEmpty(MemberRequestCacheDTO.BasicInfo basicInfoDTO) {
        String roomCode = basicInfoDTO.getRoomCode();
        String email = basicInfoDTO.getUsername();
        roomMemberService.saveRoomMemberInRedis(roomCode,email);
    }

    public RoomMember getRoomOfMemberList(String roomCode){
        return roomMemberService.getRoomMemberInfoByRoomCode(roomCode).orElseThrow(()->new NotFoundException("값이 없어"));
    }

    public void leaveMemberInCache(MemberRequestCacheDTO.BasicInfo basicInfoDTO){
        String roomCode = basicInfoDTO.getRoomCode();

        ArrayList<MemberDto.MemberStomp> originList = roomMemberService.getRoomMemberInfoByRoomCode(roomCode).get().getMemberList();
        ArrayList<MemberDto.MemberStomp> modifyList= new ArrayList<>();
        for (MemberDto.MemberStomp m:originList){
            if (!m.getEmail().equals(basicInfoDTO.getUsername())) modifyList.add(m);
        }

        roomMemberService.modifyRoomMemberInRedis(roomCode,modifyList);
    }

    @Override
    public Integer countMemberInRoom(String roomCode) {
        Optional<RoomMember> roomMemberInfoByRoomCode = roomMemberService.getRoomMemberInfoByRoomCode(roomCode);
        if (roomMemberInfoByRoomCode.isEmpty()||Optional.ofNullable(roomMemberInfoByRoomCode.get().getMemberList()).isEmpty()){
            return 0;
        }

        return roomMemberInfoByRoomCode.get().getMemberList().size();
    }

}
