package com.codecompass.goksori;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class TestUtils {
    @SneakyThrows
    public static String writeValueAsString(final Object obj) {
        if (obj == null) {
            return StringUtils.EMPTY;
        }
        return new ObjectMapper().writeValueAsString(obj);
    }
}
