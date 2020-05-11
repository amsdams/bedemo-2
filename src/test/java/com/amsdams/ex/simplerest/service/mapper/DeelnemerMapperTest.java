package com.amsdams.ex.simplerest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DeelnemerMapperTest {

    private DeelnemerMapper deelnemerMapper;

    @BeforeEach
    public void setUp() {
        deelnemerMapper = new DeelnemerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(deelnemerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deelnemerMapper.fromId(null)).isNull();
    }
}
