package dy.whatsong.domain.streaming.config;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomChannels {
    private ConcurrentHashMap<Long,RoomChannel> map=new ConcurrentHashMap<>();

    public RoomChannel connect(Long roomId){
        return map.computeIfAbsent(roomId,key->new RoomChannel().onClose(()->
                map.remove(roomId)));
    }

    public void post(Long roomId,String message){
        Optional.ofNullable(map.get(roomId)).ifPresentOrElse(ch->ch.send(message),()->{

        });
    }
}
