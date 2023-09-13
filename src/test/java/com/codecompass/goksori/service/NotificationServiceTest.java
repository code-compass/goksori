package com.codecompass.goksori.service;

import com.codecompass.goksori.constant.NotificationTypeEnum;
import com.codecompass.goksori.dto.CoinEventParamDto;
import com.codecompass.goksori.repository.EmitterRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @InjectMocks
    private NotificationService notificationService;
    @Mock
    private ServerSentEventService serverSentEventService;
    @Mock
    private EmitterRepository emitterRepository;

    @Test
    @SneakyThrows
    void testSubscribe() {
        final var emitter = mock(SseEmitter.class);
        given(emitterRepository.save(any())).willReturn(emitter);

        Assertions.assertEquals(
                emitter,
                notificationService.subscribe()
        );

        verify(emitterRepository).save(any());
        verify(serverSentEventService).connect(any(), anyString());
    }

    @Test
    void testEvent() {
        final var emitterMap = Map.of("id", new SseEmitter());
        given(emitterRepository.getAllEmitterMap()).willReturn(emitterMap);

        Assertions.assertDoesNotThrow(
                () -> notificationService.event(NotificationTypeEnum.ASCEND)
        );
        verify(serverSentEventService).sendEvent(
                emitterMap,
                List.of(
                        CoinEventParamDto.builder()
                                .coinName("BitCoin")
                                .notificationTypeEnum(NotificationTypeEnum.ASCEND)
                                .build()
                )
        );
    }
}
