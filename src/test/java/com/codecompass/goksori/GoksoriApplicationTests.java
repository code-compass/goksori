package com.codecompass.goksori;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class GoksoriApplicationTests {
    @Test
    void testMain() {
        try (final MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            final var args = new String[]{"arg1", "arg2"};
            GoksoriApplication.main(args);
            mockedStatic.verify(() ->
                    SpringApplication.run(GoksoriApplication.class, args)
            );
        }
    }
}
