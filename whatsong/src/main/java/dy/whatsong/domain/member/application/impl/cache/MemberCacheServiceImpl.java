package dy.whatsong.domain.member.application.impl.cache;

import dy.whatsong.domain.member.application.service.cache.MemberCacheService;
import dy.whatsong.domain.member.application.service.check.MemberCheckService;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.global.annotation.EssentialServiceLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    @CachePut(key = "roomCode", unless = "#result == null")
    public ResponseEntity<?> putMemberInCacheIfEmpty(String roomCode,Long memberSeq) {
        Member findBySeqMember = memberCheckService.getInfoByMemberSeq(memberSeq);
        List<Member> findValue = currentRoomMember.get(roomCode);
        findValue.add(findBySeqMember);
        currentRoomMember.put(roomCode,findValue);
        return new ResponseEntity<>(getRoomOfMemberList(roomCode), HttpStatus.OK);
    }

    @Cacheable("'all'")
    public List<Member> getRoomOfMemberList(String roomCode){
        List<Member> roomMembers = currentRoomMember.get(roomCode);
        System.out.println("roomMembers:"+roomMembers);
        return roomMembers;
    }
}
