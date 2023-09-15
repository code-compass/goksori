package com.codecompass.goksori.dto.mapper;

import com.codecompass.goksori.config.MapStructConfig;
import com.codecompass.goksori.dto.CoinEventParamDto;
import com.codecompass.goksori.dto.CoinEventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface CoinEventDtoMapper {
    CoinEventDtoMapper mapper = Mappers.getMapper(CoinEventDtoMapper.class);

    @Mapping(target = "notificationType", source = "notificationTypeEnum")
    CoinEventResponseDto.CoinEventDetailResponseDto map(CoinEventParamDto paramDto);

    List<CoinEventResponseDto.CoinEventDetailResponseDto> map(List<CoinEventParamDto> paramDtoList);
}
