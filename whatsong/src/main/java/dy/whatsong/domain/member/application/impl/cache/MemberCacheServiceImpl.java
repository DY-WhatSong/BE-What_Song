package dy.whatsong.domain.member.application.impl.cache;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.dto.MemberCacheDTO;
import dy.whatsong.domain.member.dto.MemberResponseDto;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;

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
    public void putMemberInCacheIfEmpty(MemberCacheDTO.BasicInfo basicInfoDTO) {
        Member findBySeqMember = memberCheckService.getInfoByMemberEmail(basicInfoDTO.getUsername());
        List<Member> memberList = currentRoomMember.computeIfAbsent(basicInfoDTO.getRoomCode(), k -> new ArrayList<>());
        memberList.add(findBySeqMember);
        currentRoomMember.put(basicInfoDTO.getRoomCode(),memberList);
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
    public void leaveMemberInCache(MemberCacheDTO.BasicInfo basicInfoDTO){
        List<Member> curretntList = currentRoomMember.get(basicInfoDTO.getRoomCode());
        List<Member> returnedList=new ArrayList<>();
        for (Member m:curretntList){
            if (!m.getEmail().equals(basicInfoDTO.getUsername())) returnedList.add(m);
        }
        currentRoomMember.put(basicInfoDTO.getRoomCode(),returnedList);
        System.out.println("modify?:"+currentRoomMember.toString());
    }

    @Override
    public Integer getUserCountInRoom(String roomCode) {
        return currentRoomMember.get(roomCode).size();
    }

    @Override
    public Boolean memberIfExistEnter(Long memberSeq,String roomCode) {
        Member infoByMemberSeq = memberCheckService.getInfoByMemberSeq(memberSeq);
        return currentRoomMember.get(roomCode).stream()
                .anyMatch(member -> member.getMemberSeq().equals(memberSeq));
    }

}
