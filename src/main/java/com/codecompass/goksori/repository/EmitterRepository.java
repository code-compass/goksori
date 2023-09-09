package com.codecompass.goksori.repository;

import com.codecompass.goksori.exception.GoksoriException;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Validated
@Repository
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public synchronized SseEmitter save(@NotBlank final String id) {
        if (emitters.containsKey(id)) {
            log.error("Duplicated emitter id. {}", id);
            throw new GoksoriException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final var emitter = new SseEmitter();
        emitters.put(id, emitter);
        emitter.onCompletion(() -> deleteById(id));
        emitter.onTimeout(() -> deleteById(id));

        return emitter;
    }

    public SseEmitter findById(@NotBlank final String id) {
        return emitters.get(id);
    }

    public void deleteById(@NotBlank final String id) {
        emitters.remove(id);
    }

    public List<SseEmitter> getAll() {
        return emitters.values().stream().toList();
    }
}
