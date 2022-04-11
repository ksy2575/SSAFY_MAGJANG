package B208.mag_jang.controller;

import B208.mag_jang.domain.ChatMessageDTO;
import B208.mag_jang.domain.DealDTO;
import B208.mag_jang.domain.Player;
import B208.mag_jang.service.AsyncService;
import B208.mag_jang.service.GameService;
import B208.mag_jang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final UserService userService;
    private final AsyncService asyncService;

    // game시작 시 room에 있는 유저아이디를 game으로 넘겨주며 roommap 삭제
    // 프론트 : "/sub/game/start/{roomId}" 구독
    @MessageMapping(value = "/start")
    public void gameStart(ChatMessageDTO message) throws InterruptedException {

        if(gameService.gameStart(message.getRoomId())){
            // 1. 게임시작 메세지 전송
            System.out.println("gameStart : 게임을 시작합니다!");
            template.convertAndSend("/sub/game/start/" + message.getRoomId(), gameService.getGame(message.getRoomId())); // GameDTO 만들어 전송하기


            // 2. 3초 후 능력 생성
            // AsyncService의 @Async 메서드 호출, callback 등록을 통해 능력 생성
            asyncService.sleep(message.getRoomId(), 3000).addCallback((result) -> {
                System.out.println("callback returns : " + result);
                try {
                    initJobs(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, (e) -> {
                System.out.println("error");
            });
        }else{//게임 인원 부족
            System.out.println("gameStart Failed : 게임 인원이 부족합니다");
            template.convertAndSend("/sub/game/start/" + message.getRoomId(), (Object) null);
        }


    }

    // 플레이어 능력 생성 후 반환
    // 프론트 : "/sub/game/jobs/{roomId}" 구독
    public void initJobs(String roomId) throws InterruptedException {
        System.out.println("initJobs : "+roomId);
        template.convertAndSend("/sub/game/jobs/" + roomId, gameService.getNextJobs(roomId)); // 플레이어별 능력 리스트 전송

        // @Async 메서드 호출, callback 등록을 통해 처리
        asyncService.sleep(roomId, 3000).addCallback((result) -> {
            System.out.println("callback!! : " + result);
            try {
                initOrder(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, (e) -> {
            System.out.println("error");
        });
    }

    // 플레이어 순서를 생성 후 반환
    public void initOrder(String roomId) throws InterruptedException {
        System.out.println("initOrder : "+roomId);
        template.convertAndSend("/sub/game/order/" + roomId, gameService.initOrder(roomId));

        // @Async 메서드 호출, callback 등록을 통해 처리
        asyncService.sleep(roomId, 3000).addCallback((result) -> {
            System.out.println("callback!! : " + result);
            try {
                initDeal(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, (e) -> {
            System.out.println("error");
        });
    }

    public void sendCurrBroker(String roomId){
        //브로커 주기
        Player player = gameService.getCurrBroker(roomId);
//        Player player2 = new Player("김주호");
        template.convertAndSend("/sub/game/broker/" + roomId, player);
    }

    // 거래 조건 생성
    public void initDeal(String roomId) throws InterruptedException {
        sendCurrBroker(roomId);


        // @Async 메서드 호출, callback 등록을 통해 처리
        asyncService.sleep(roomId, 3000).addCallback((result) -> {
            System.out.println("callback!! : " + result);
            //3초 기다렸다가 딜 생성
            System.out.println("initDeal : "+roomId);
            DealDTO deal = gameService.initDeal(roomId);
            template.convertAndSend("/sub/game/deal/" + roomId, deal);
        }, (e) -> {
            System.out.println("error");
        });

    }
    
    // 함께 거래할 멤버 리스트를 받아 모두에게 전송 - 금액을 함께 적어서 전송할지 논의 필요
    // 프론트에서 {멤버 : 돈} 객체 발신 및 수신하도록 구현 -> 이러면 roomId는 어떻게 받지? -> 기존 ChatMessageDTO의 message를 Object로 바꾸고, 경우에 따라 처리...
    // 프론트 발신 : "/pub/game/choice/{roomId}", 수신 : "/sub/game/choice/{roomId}"
    // 요거 조금만 응용하면 한 명 한 명 클릭 보이게 할 수 있음 : 플레이어 클릭 시 메세지 + 금액 넣고 버튼 클릭 시 메세지

    @MessageMapping(value = "/choice")
    public void dealMemberChoice(ChatMessageDTO message) {
        String member = (String) message.getMessage();
        System.out.println("dealMemberChoice : 거래 멤버 선택 " + member);
        template.convertAndSend("/sub/game/choice/" + message.getRoomId(), member); // 선택된 멤버 전송
    }

    @MessageMapping(value = "/finalchoice") // 현재 : [["player1", 300], ["player2", 200] ,,,] -> JSON 객체 구조로 바꾸기
    public void dealMemberChoiceComplete(ChatMessageDTO message) throws InterruptedException {
        System.out.println(message.getMessage());
        if(message.getMessage().equals("") || message.getMessage() == null){
            // 최종 멤버 결정 실패 시 투표를 건너뛰고 다음 턴으로 진행(마지막 턴이라면 라운드 +1)
            System.out.println("결정 실패");
            message.setMessage(new ArrayList<>());
            template.convertAndSend("/sub/game/finalchoice/" + message.getRoomId(), message); // {멤버 : 돈} 전송
//            template.convertAndSend("/sub/game/finalchoice/" + message.getRoomId(), (Object) ""); // {멤버 : 돈} 전송
            //컨트롤러 메서드 만들기
            startNext(message.getRoomId());
        }else{
            List<ArrayList<Object>> moneys = (ArrayList<ArrayList<Object>>)message.getMessage(); // [["player1", 300], ["player2", 200] ,,,]
            gameService.setDealAmount(message.getRoomId(), moneys);
            DealDTO deal = gameService.getDeal(message.getRoomId());
            System.out.println("dealMemberChoice : 분배금 확인 " + deal);
            template.convertAndSend("/sub/game/finalchoice/" + message.getRoomId(), deal); // DealDTO 전송 -> {멤버 : 돈} 만 보내도록 수정 필요
        }
    }

    @MessageMapping(value = "/vote")
    public void vote(ChatMessageDTO message) throws InterruptedException {
        System.out.println(message.getWriter() + " 투표 : " + (boolean)message.getMessage());
        DealDTO deal = gameService.getDeal(message.getRoomId());
        deal.addVote(message.getWriter(), (boolean)message.getMessage());

        //깽판 횟수 ++
        if(!(boolean)message.getMessage()) gameService.setGangAmount(message.getRoomId(), message.getWriter());


        List<String> player = new ArrayList<>();
        player.add(message.getWriter());
        template.convertAndSend("/sub/game/vote/" + message.getRoomId(), player); // 투표 완료 메시지 전송

        if(deal.isVoteOK()){ // dto가 메서드를 갖고 있는 형태 -> Deal을 dto로 하는게 맞는지 고민 필요
            deal.calcMoney(gameService.getGame(message.getRoomId()).getRound());
            template.convertAndSend("/sub/game/finalvote/" + message.getRoomId(), deal); // {멤버 : 돈} 전송, 이를 파싱하여 자신의 돈 추적 - ㅇ

            gameService.updatePlayerMoney(message.getRoomId()); // 거래 결과를 플레이어별 돈에 반영
            gameService.updateGameLog(message.getRoomId());
            startNext(message.getRoomId()); // 다음 스텝 진행 turn++
        }

    }

    private void startNext(String roomId) throws InterruptedException {
        gameService.startNext(roomId);
        if(!gameService.isGameFinished(roomId)){
            if(gameService.getGame(roomId).getTurn() == 1){
                template.convertAndSend("/sub/game/rank/" + roomId, gameService.initOrder(roomId)); // List<String> 형의 순위 전송
                initJobs(roomId);
            }else{
                initDeal(roomId);
            }
        }else{
            finishGame(roomId);
        }
    }

    public void finishGame(String roomId){
        System.out.println("game finished!!!!");
        // 최종 순위와 로그 전송(로그 파싱 쉬우면 그냥 로그만 전송)
        List<Player> playerList = gameService.initOrderWithMoney(roomId);
        template.convertAndSend("/sub/game/finalrank/" + roomId, playerList); // List<Player> 형의 순위 전송
        template.convertAndSend("/sub/game/log/" + roomId, gameService.getNicknames(roomId)); // List<String> 형의 플레이어 리스트 전송
        template.convertAndSend("/sub/game/log/" + roomId, gameService.getLog(roomId)); // int[round][turn][playerIndex] 형의 3차원 배열로 전송

//        List<String> proGangPlayerList = gameService.getProGangPlayer(roomId);
//        template.convertAndSend("/sub/game/winner/" + roomId, gameService.getWinners(roomId)); // 우승자 리스트 전송
//        template.convertAndSend("/sub/game/progang/" + roomId, proGangPlayerList); // 프로깽판러 리스트 전송
        
        // 각 플레이어별 승점 5500 -> 550, 1등 5
//        for(Player player : playerList){
//            userService.setRankPoint(player.getNickName(), player.getMoney()/10);
//        }
//        // 우승 플레이어 가산점
//        for(Player player : gameService.getWinners(roomId)){
//            userService.setRankPoint(player.getNickName(), (int) (player.getMoney()/100));
//        }
//        // 프로깽판러
//        for(String nickname : proGangPlayerList){
//            userService.setProGangAmount(nickname);
//        }
          // 상위 랭킹 확인, 유저 정보 반환 부분
//        System.out.println(userService.getRank());
//        System.out.println(userService.getUser("김주호"));

    }
}
