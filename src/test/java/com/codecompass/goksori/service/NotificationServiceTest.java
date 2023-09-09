package com.codecompass.goksori.service;

import com.codecompass.goksori.exception.GoksoriException;
import com.codecompass.goksori.repository.EmitterRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @InjectMocks
    private NotificationService notificationService;

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
        verify(emitter).send(any());
    }

    @Test
    @SneakyThrows
    void testSendDataWhenIoException() {
        final var emitter = mock(SseEmitter.class);
        given(emitterRepository.save(any())).willReturn(emitter);
        doThrow(IOException.class).when(emitter).send(any());

        Assertions.assertThrows(
                GoksoriException.class,
                () -> notificationService.subscribe()
        );
        verify(emitterRepository).deleteById(any());
    }

    @Test
    @SneakyThrows
    void testTest() {
        final var emitter = mock(SseEmitter.class);
        given(emitterRepository.getAll()).willReturn(List.of(emitter));

        Assertions.assertDoesNotThrow(
                () -> notificationService.test()
        );

        verify(emitter).send(any());
    }
}
