package com.codecompass.goksori.dto;

import com.codecompass.goksori.constant.NotificationTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEventRequestDto {
    @NotNull
    private NotificationTypeEnum notificationType;
}
