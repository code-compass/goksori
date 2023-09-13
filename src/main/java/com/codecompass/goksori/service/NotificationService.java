package com.codecompass.goksori.service;

import com.codecompass.goksori.constant.NotificationTypeEnum;
import com.codecompass.goksori.dto.CoinEventParamDto;
import com.codecompass.goksori.repository.EmitterRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class NotificationService {
    private final ServerSentEventService serverSentEventService;
    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe() {
        final var id = String.valueOf(System.currentTimeMillis());
        final var emitter = emitterRepository.save(id);
        serverSentEventService.connect(emitter, id);

        return emitter;
    }

    public void event(@NotNull final NotificationTypeEnum notificationTypeEnum) {
        serverSentEventService.sendEvent(
                emitterRepository.getAllEmitterMap(),
                List.of(
                        CoinEventParamDto.builder()
                                .coinName("BitCoin")
                                .notificationTypeEnum(notificationTypeEnum)
                                .build()
                )
        );
    }
}
