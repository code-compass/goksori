package com.codecompass.goksori.service;

import com.codecompass.goksori.constant.NotificationTypeEnum;
import com.codecompass.goksori.dto.CoinEventParamDto;
import com.codecompass.goksori.exception.GoksoriException;
import com.codecompass.goksori.repository.EmitterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServerSentEventServiceTest {
    @InjectMocks
    private ServerSentEventService serverSentEventService;
    @Mock
    private EmitterRepository emitterRepository;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void testConnect() {
        final var emitter = mock(SseEmitter.class);

        Assertions.assertDoesNotThrow(() -> serverSentEventService.connect(emitter, "id"));
        verify(emitter).send(any());
    }

    @Test
    @SneakyThrows
    void testEvent() {
        final var emitter = mock(SseEmitter.class);

        Assertions.assertDoesNotThrow(
                () -> serverSentEventService.sendEvent(
                        Map.of("id", emitter),
                        List.of(
                                CoinEventParamDto.builder()
                                        .coinName("coin")
                                        .notificationTypeEnum(NotificationTypeEnum.ASCEND)
                                        .build()
                        )
                )
        );
        verify(emitter).send(any());
    }

    @Test
    @SneakyThrows
    void testConvertObjectToJsonStringWhenJsonProcessingException() {
        final var emitter = mock(SseEmitter.class);
        doThrow(JsonProcessingException.class).when(objectMapper).writeValueAsString(any());

        final Executable executable = () -> serverSentEventService.sendEvent(
                Map.of("id", emitter),
                List.of(
                        CoinEventParamDto.builder()
                                .coinName("coin")
                                .notificationTypeEnum(NotificationTypeEnum.ASCEND)
                                .build()
                )
        );
        Assertions.assertThrows(GoksoriException.class, executable);
        verify(emitter, never()).send(any());
    }

    @Test
    @SneakyThrows
    void testSendDataWhenIoException() {
        final var emitter = mock(SseEmitter.class);
        doThrow(IOException.class).when(emitter).send(any());

        Assertions.assertDoesNotThrow(
                () -> serverSentEventService.connect(emitter, "id")
        );
        verify(emitterRepository).deleteById("id");
    }
}
