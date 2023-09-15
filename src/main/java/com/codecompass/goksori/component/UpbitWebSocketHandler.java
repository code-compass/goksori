package com.codecompass.goksori.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class UpbitWebSocketHandler extends AbstractWebSocketHandler {
    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        log.info("Establish websocket connection: {}", session.getUri());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus closeStatus) {
        log.info("Close websocket connection: {}, {}", session.getUri(), closeStatus.getReason());
    }

    @Override
    public void handleBinaryMessage(final WebSocketSession session, final BinaryMessage message) {
        log.info("=================handleBinaryMessage=================");
        log.info(StandardCharsets.UTF_8.decode(message.getPayload()).toString());
    }
}
