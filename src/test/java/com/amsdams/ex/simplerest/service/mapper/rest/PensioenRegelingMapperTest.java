package com.amsdams.ex.simplerest.service.mapper.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amsdams.ex.simplerest.service.mapper.PensioenRegelingMapper;
import com.amsdams.ex.simplerest.service.mapper.PensioenRegelingMapperImpl;


public class PensioenRegelingMapperTest {

    private PensioenRegelingMapper pensioenRegelingMapper;

    @BeforeEach
    public void setUp() {
        pensioenRegelingMapper = new PensioenRegelingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(pensioenRegelingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pensioenRegelingMapper.fromId(null)).isNull();
    }
}
