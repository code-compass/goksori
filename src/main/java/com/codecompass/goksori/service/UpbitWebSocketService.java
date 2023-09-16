package com.codecompass.goksori.service;

import com.codecompass.goksori.component.UpbitWebSocketHandler;
import com.codecompass.goksori.exception.GoksoriException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;

import java.net.URI;

@Slf4j
@Component
@AllArgsConstructor
public class UpbitWebSocketService {
    private final WebSocketClient webSocketClient;
    private final UpbitWebSocketHandler upbitWebSocketHandler;

    @PostConstruct
    public void connect() {
        try {
            final var session = webSocketClient.execute(
                    upbitWebSocketHandler,
                    new WebSocketHttpHeaders(),
                    URI.create("wss://api.upbit.com/websocket/v1")
            );
            session.get().sendMessage(
                    new TextMessage("[{\"ticket\":\"test\"},{\"type\":\"ticker\",\"codes\":[\"KRW-BTC\"]}]")
            );

        } catch (final Exception e) {
            throw new GoksoriException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
