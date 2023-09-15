package com.codecompass.goksori.dto;

import com.codecompass.goksori.constant.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinEventParamDto {
    private String coinName;
    private NotificationTypeEnum notificationTypeEnum;
}
