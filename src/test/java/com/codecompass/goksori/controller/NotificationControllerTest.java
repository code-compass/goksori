package com.codecompass.goksori.controller;

import com.codecompass.goksori.TestUtils;
import com.codecompass.goksori.constant.NotificationTypeEnum;
import com.codecompass.goksori.dto.TestEventRequestDto;
import com.codecompass.goksori.service.NotificationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest
class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Test
    @SneakyThrows
    void testSubscribe() {
        final var emitter = new SseEmitter();
        given(notificationService.subscribe()).willReturn(emitter);

        mockMvc.perform(MockMvcRequestBuilders.get("/subscribe")
                        .accept(MediaType.TEXT_EVENT_STREAM))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    void testEvent() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                TestUtils.writeValueAsString(
                                        TestEventRequestDto.builder()
                                                .notificationType(NotificationTypeEnum.ASCEND)
                                                .build()
                                )
                        )
        ).andExpect(MockMvcResultMatchers.status().isOk());
        verify(notificationService).event(NotificationTypeEnum.ASCEND);
    }
}
