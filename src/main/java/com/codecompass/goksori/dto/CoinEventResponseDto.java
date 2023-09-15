package com.codecompass.goksori.dto;

import com.codecompass.goksori.constant.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinEventResponseDto {
    private String id;
    private List<CoinEventDetailResponseDto> detailList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoinEventDetailResponseDto {
        private String coinName;
        private NotificationTypeEnum notificationType;
    }
}
