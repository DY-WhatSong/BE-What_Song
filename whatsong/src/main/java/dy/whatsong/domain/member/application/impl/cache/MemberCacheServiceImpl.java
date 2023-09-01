package dy.whatsong.domain.member.application.impl.cache;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@EssentialServiceLayer
@RequiredArgsConstructor
@CacheConfig(cacheNames = "members")
public class MemberCacheServiceImpl implements MemberCacheService {

    private final MemberCheckService memberCheckService;

    private HashMap<String,List<Member>> currentRoomMember;


    @PostConstruct
    public void initializeMemberCaching(){
        currentRoomMember=new LinkedHashMap<>();
    }

    @Override
    @CachePut(key = "#roomCode", unless = "#result == null")
    public List<MemberResponseDto.CheckResponse> putMemberInCacheIfEmpty(String roomCode,Long memberSeq) {
        Member findBySeqMember = memberCheckService.getInfoByMemberSeq(memberSeq);
        List<Member> memberList = currentRoomMember.computeIfAbsent(roomCode, k -> new ArrayList<>());
        memberList.add(findBySeqMember);
        currentRoomMember.put(roomCode,memberList);
        return getRoomOfMemberList(roomCode);
    }

    public List<MemberResponseDto.CheckResponse> getRoomOfMemberList(String roomCode){
        List<MemberResponseDto.CheckResponse> roomMembers = currentRoomMember.getOrDefault(roomCode, Collections.emptyList())
                .stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
        System.out.println("roomMembers:"+roomMembers);
        return roomMembers;
    }

    @CachePut(key = "#roomCode")
    public List<MemberResponseDto.CheckResponse> leaveMemberInCache(String roomCode,Long memberSeq){
        List<Member> curretntList = currentRoomMember.get(roomCode);
        List<Member> returnedList=new ArrayList<>();
        for (Member m:curretntList){
            if (!m.getMemberSeq().equals(memberSeq)) returnedList.add(m);
        }
        currentRoomMember.put(roomCode,returnedList);
        System.out.println("modify?:"+currentRoomMember.toString());
        return getRoomOfMemberList(roomCode);
    }

    @Override
    public Integer getUserCountInRoom(String roomCode) {
        return currentRoomMember.get(roomCode).size();
    }

}
