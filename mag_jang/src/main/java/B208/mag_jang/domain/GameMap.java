package B208.mag_jang.domain;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GameMap {

    private Map<String, Game> games = new HashMap<>();

    public void addPlayer(String roomId, String nickname) {
        this.games.get(roomId).addPlayer(nickname);
    }

    public void removeNickname(String roomId, String writer) {
        if(games.get(roomId)==null) {
            System.out.println("RoomMap : " + writer + "의 quit 요청, " + roomId + "가 null 입니다.");
            return;
        }
    }
    public Game getGame(String roomId){
        return games.get(roomId);
    }

    public void setNewGame(String roomId){
        games.put(roomId, new Game(roomId));
    }

    public void initGame(String roomId, int startMoney) {
        games.get(roomId).initGame(startMoney);
    }

    public int getPlayerListSize(String roomId) { return games.get(roomId).getPlayerListSize(); }

    public void setCurrJobs(String roomId, Set<String> jobs) { games.get(roomId).setCurrJobs(jobs); }
}