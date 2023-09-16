package com.codecompass.goksori.service;

import com.codecompass.goksori.component.UpbitWebSocketHandler;
import com.codecompass.goksori.exception.GoksoriException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpbitWebSocketServiceTest {
    @InjectMocks
    private UpbitWebSocketService upbitWebSocketService;
    @Mock
    private WebSocketClient webSocketClient;
    @Mock
    private UpbitWebSocketHandler upbitWebSocketHandler;

    @Test
    @SneakyThrows
    void testConnect() {
        final var session = mock(WebSocketSession.class);
        given(webSocketClient.execute(
                        any(WebSocketHandler.class),
                        any(WebSocketHttpHeaders.class),
                        any(URI.class)
                )
        ).willReturn(CompletableFuture.completedFuture(session));

        Assertions.assertDoesNotThrow(() -> upbitWebSocketService.connect());

        verify(webSocketClient).execute(
                upbitWebSocketHandler,
                new WebSocketHttpHeaders(),
                URI.create("wss://api.upbit.com/websocket/v1")
        );
        verify(session).sendMessage(
                new TextMessage("[{\"ticket\":\"test\"},{\"type\":\"ticker\",\"codes\":[\"KRW-BTC\"]}]")
        );
    }

    @Test
    void testConnectWhenException() {
        given(webSocketClient.execute(
                        any(WebSocketHandler.class),
                        any(WebSocketHttpHeaders.class),
                        any(URI.class)
                )
        ).willThrow(new RuntimeException());

        Assertions.assertThrows(
                GoksoriException.class,
                () -> upbitWebSocketService.connect()
        );
    }
}
