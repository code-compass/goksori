package com.codecompass.goksori.repository;

import com.codecompass.goksori.exception.GoksoriException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmitterRepositoryTest {
    @InjectMocks
    private EmitterRepository emitterRepository;

    @Test
    void testSave() {
        Assertions.assertNotNull(emitterRepository.save("id"));
        Assertions.assertThrows(
                GoksoriException.class,
                () -> emitterRepository.save("id")
        );
    }

    @Test
    void testFindById() {
        emitterRepository.save("id");
        Assertions.assertNotNull(emitterRepository.findById("id"));
    }

    @Test
    void testDeleteById() {
        emitterRepository.save("id");
        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> emitterRepository.deleteById("id")),
                () -> Assertions.assertDoesNotThrow(() -> emitterRepository.deleteById("id1"))
        );
    }

    @Test
    void testGetAll() {
        emitterRepository.save("id1");
        emitterRepository.save("id2");

        Assertions.assertEquals(
                2,
                CollectionUtils.size(emitterRepository.getAllEmitterMap())
        );
    }
}
