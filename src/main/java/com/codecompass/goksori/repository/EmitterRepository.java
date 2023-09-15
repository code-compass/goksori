package com.codecompass.goksori.repository;

import com.codecompass.goksori.annotation.ExcludeFromJacocoGeneratedReport;
import com.codecompass.goksori.exception.GoksoriException;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Validated
@Repository
@ExcludeFromJacocoGeneratedReport
public class EmitterRepository {
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public synchronized SseEmitter save(@NotBlank final String id) {
        if (emitterMap.containsKey(id)) {
            log.error("Duplicated emitter id. {}", id);
            throw new GoksoriException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final var emitter = createEmitter(id);
        emitterMap.put(id, emitter);

        return emitter;
    }

    private SseEmitter createEmitter(final String id) {
        final var emitter = new SseEmitter();
        emitter.onCompletion(() -> deleteById(id));
        emitter.onTimeout(() -> deleteById(id));

        return emitter;
    }

    public SseEmitter findById(@NotBlank final String id) {
        return emitterMap.get(id);
    }

    public void deleteById(@NotBlank final String id) {
        emitterMap.remove(id);
    }

    public Map<String, SseEmitter> getAllEmitterMap() {
        return this.emitterMap;
    }
}
