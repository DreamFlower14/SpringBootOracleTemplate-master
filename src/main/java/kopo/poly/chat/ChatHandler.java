package kopo.poly.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    // 웹소켓에 접속되는 사용자들을 저장, 중복 제거하기 위한 SET 데이터 구조 사용
    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new LinkedHashSet<>());

    // 채팅룸 조회하기 위해 사용
    public static Map<String, String> roomInfo = Collections.synchronizedMap(new LinkedHashMap<>());

}
