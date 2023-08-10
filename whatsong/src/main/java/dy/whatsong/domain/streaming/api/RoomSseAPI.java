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
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/channels/stream/room/{roomCode}")
@RequiredArgsConstructor
public class RoomSseAPI {

    private final RoomSseService roomSseService;

}
