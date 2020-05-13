package com.amsdams.ex.simplerest.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.amsdams.ex.simplerest.domain.PensioenRegeling;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;

/**
 * Mapper for the entity {@link PensioenRegeling} and its DTO {@link PensioenRegelingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PensioenRegelingMapper extends EntityMapper<PensioenRegelingDTO, PensioenRegeling> {


    @Override
	@Mapping(target = "deelnemers", ignore = true)
    @Mapping(target = "removeDeelnemer", ignore = true)
    PensioenRegeling toEntity(PensioenRegelingDTO pensioenRegelingDTO);

    default PensioenRegeling fromId(Long id) {
        if (id == null) {
            return null;
        }
        PensioenRegeling pensioenRegeling = new PensioenRegeling();
        pensioenRegeling.setId(id);
 
        return pensioenRegeling;
    }
}
