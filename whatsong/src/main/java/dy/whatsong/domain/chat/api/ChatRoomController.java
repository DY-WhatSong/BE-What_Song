package dy.whatsong.domain.chat.api;

import dy.whatsong.domain.chat.model.ChatRoom;
import dy.whatsong.domain.chat.model.LoginInfo;
import dy.whatsong.domain.chat.repo.ChatRoomRepository;
import dy.whatsong.domain.member.service.TokenService;
import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final Properties.JwtProperties jwtProperties;
    private final ChatRoomRepository chatRoomRepository;
    private final TokenService tokenService;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream()
                .forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getChatRoomSequence())));
        return chatRooms;
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo(HttpServletRequest request) {
        String refreshToken = request.getHeader(jwtProperties.getREFRESH_TOKEN_HEADER());
        return LoginInfo.builder()
                .name(tokenService.getOauthIdAndSocialType(refreshToken))
                .build();
    }
}
