package dy.whatsong.domain.streaming.api;

import dy.whatsong.domain.streaming.application.service.RoomSseService;
import dy.whatsong.domain.streaming.dto.MRSseRequest;
import dy.whatsong.domain.streaming.dto.MRSseResponse;
import dy.whatsong.domain.streaming.entity.room.MRSse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/channels/stream/room/{roomCode}")
@RequiredArgsConstructor
public class RoomSseAPI {

    private final RoomSseService roomSseService;

    private final Map<String,Flux<MRSseResponse>> sseStreamToRoom=new ConcurrentHashMap<>();

    /*@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<MRSseResponse>> connectionRoomSSE(@PathVariable String roomCode){
        return sseStreamToRoom.computeIfAbsent(roomCode,code->{
            return Flux.interval(Duration.ofSeconds(1))
                    .map(seq->{
                        MemberRe
                    })
        })
    }*/

    @PostMapping
    public Flux<ServerSentEvent<MRSseResponse>> sendCurrentRoomState(@PathVariable("roomCode")String roomCode,@RequestBody MRSseRequest.playerCurrentState playerCurrentState){
        MRSse findMRSse = roomSseService.getMRSseByRoomCode(roomCode);
        return Flux.just(MRSseResponse.builder()
                            .currentTime(playerCurrentState.getTimeStamp())
                            .roomCode(roomCode)
                            .status(findMRSse.getStatus())
                            .videoId(findMRSse.getVideoId())
                            .build())
                .map(MRSSeRes->ServerSentEvent.builder(MRSSeRes).build());
    }
}
