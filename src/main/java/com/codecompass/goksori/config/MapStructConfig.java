package com.codecompass.goksori.config;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true)
)
public class MapStructConfig {
}
