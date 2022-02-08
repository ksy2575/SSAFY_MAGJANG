package B208.mag_jang.domain;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class ChatRoomDTO {
    private String roomId;
    private String name;
    // WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDTO create(String name){
        ChatRoomDTO room = new ChatRoomDTO();
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<WebSocketSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "ChatRoomDTO{" +
                "roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
