package com.codecompass.goksori.service;

import com.codecompass.goksori.exception.GoksoriException;
import com.codecompass.goksori.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe() {
        final var id = String.valueOf(System.currentTimeMillis());
        final var emitter = emitterRepository.save(id);
        sendData(emitter, id, "data:" + id);

        return emitter;
    }

    private void sendData(final SseEmitter emitter, final String id, final Object data) {
        try {
            emitter.send(
                    SseEmitter.event()
                            .name(id)
                            .data(data)
            );
        } catch (final IOException e) {
            log.error("error while send data.", e);
            emitterRepository.deleteById(id);
            throw new GoksoriException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void test() {
        emitterRepository.getAll().forEach(
                emitter -> sendData(
                        emitter,
                        "TEST",
                        "TEST"
                )
        );
    }
}
