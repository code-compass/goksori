package com.codecompass.goksori.service;

import com.codecompass.goksori.dto.CoinEventParamDto;
import com.codecompass.goksori.dto.CoinEventResponseDto;
import com.codecompass.goksori.dto.mapper.CoinEventDtoMapper;
import com.codecompass.goksori.exception.GoksoriException;
import com.codecompass.goksori.repository.EmitterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ServerSentEventService {
    private final EmitterRepository emitterRepository;
    private final ObjectMapper objectMapper;

    public void connect(@NotNull final SseEmitter emitter, @NotBlank final String id) {
        sendData(emitter, id, "CONNECT", "{}");
    }

    public void sendEvent(
            @NotNull final Map<String, SseEmitter> emitterMap,
            @NotEmpty @Valid final List<CoinEventParamDto> eventParamDtoList
    ) {
        final var detailList = CoinEventDtoMapper.mapper.map(eventParamDtoList);
        emitterMap.forEach(
                (id, emitter) -> sendData(
                        emitter,
                        id,
                        "EVENT",
                        convertObjectToJsonString(
                                CoinEventResponseDto.builder()
                                        .id(id)
                                        .detailList(detailList)
                                        .build()
                        )
                )
        );
    }

    private String convertObjectToJsonString(@NotNull final CoinEventResponseDto responseDto) {
        try {
            return objectMapper.writeValueAsString(responseDto);
        } catch (final JsonProcessingException e) {
            log.error("error while convert object to json string.", e);
            throw new GoksoriException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendData(
            final SseEmitter emitter,
            final String id,
            final String name,
            final String data
    ) {
        try {
            emitter.send(
                    SseEmitter.event()
                            .id(id)
                            .name(name)
                            .data(data)
            );
        } catch (final IOException e) {
            log.error("error while send data.", e);
            emitterRepository.deleteById(id);
        }
    }
}
