package com.codecompass.goksori.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping("/events")
    public static Flux<ServerSentEvent<String>> getEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(
                        sequence -> ServerSentEvent.<String>builder()
                                .id(String.valueOf(sequence))
                                .event("event-type")
                                .data("This is an SSE message: " + sequence)
                                .build()
                )
                .take(3);
    }
}
