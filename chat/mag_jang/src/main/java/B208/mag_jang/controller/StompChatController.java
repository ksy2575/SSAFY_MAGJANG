package B208.mag_jang.controller;

import B208.mag_jang.domain.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    // Client가 SEND할 수 있는 경로
    // stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // "/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        System.out.println(message.getWriter()+ "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){
        System.out.println(message.getWriter() + " : " + message.getMessage());
        System.out.println("/sub/chat/room/" + message.getRoomId());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    // 귓속말 기능
    // "pub/chat/whisper"
    @MessageMapping(value = "/chat/whisper")
    public void whisper(ChatMessageDTO message){
        System.out.println("귓속말 : " + message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId() + "/" + message.getReader(), message);
    }

    // stomp.disconnect 시 quit 메세지 발송
    // "pub/chat/quit"
    @MessageMapping(value = "/chat/quit")
    public void quit(ChatMessageDTO message){
        message.setMessage(message.getWriter() + "님이 채팅방을 나갔습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
