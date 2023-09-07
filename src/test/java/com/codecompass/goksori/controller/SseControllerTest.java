package com.codecompass.goksori.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SSEControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testSseEndpointWithTake() {
        webTestClient.get().uri("/sse/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(String.class)
                .getResponseBody()
                .log()
                .as(StepVerifier::create)
                .expectNext("This is an SSE message: 0")
                .expectNext("This is an SSE message: 1")
                .expectNext("This is an SSE message: 2")
                .expectComplete()
                .verify();
    }
}
