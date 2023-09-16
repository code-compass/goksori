package com.codecompass.goksori.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UpbitWebSocketHandlerTest {
    @InjectMocks
    private UpbitWebSocketHandler upbitWebSocketHandler;

    @Test
    void testAfterConnectionEstablished() {
        Assertions.assertDoesNotThrow(
                () -> upbitWebSocketHandler.afterConnectionEstablished(mock(WebSocketSession.class))
        );
    }

    @Test
    void testAfterConnectionClosed() {
        Assertions.assertDoesNotThrow(
                () -> upbitWebSocketHandler.afterConnectionClosed(mock(WebSocketSession.class), CloseStatus.NORMAL)
        );
    }

    @Test
    void testHandlerBinaryMessage() {
        final var binaryMessage = mock(BinaryMessage.class);
        final ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put("{\"test\": \"test\"}".getBytes(StandardCharsets.UTF_8));
        given(binaryMessage.getPayload()).willReturn(byteBuffer);

        Assertions.assertDoesNotThrow(
                () -> upbitWebSocketHandler.handleBinaryMessage(mock(WebSocketSession.class), binaryMessage)
        );
    }
}
