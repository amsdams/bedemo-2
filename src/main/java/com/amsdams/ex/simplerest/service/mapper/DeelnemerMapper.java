package com.amsdams.ex.simplerest.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.amsdams.ex.simplerest.domain.Deelnemer;
import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;

/**
 * Mapper for the entity {@link Deelnemer} and its DTO {@link DeelnemerDTO}.
 */
@Mapper(componentModel = "spring", uses = {PensioenRegelingMapper.class})
public interface DeelnemerMapper extends EntityMapper<DeelnemerDTO, Deelnemer> {

    @Override
	@Mapping(source = "pensioenRegeling.id", target = "pensioenRegelingId")
    @Mapping(source = "pensioenRegeling.naam", target = "pensioenRegelingNaam")
    DeelnemerDTO toDto(Deelnemer deelnemer);

    @Override
	@Mapping(source = "pensioenRegelingId", target = "pensioenRegeling")
    Deelnemer toEntity(DeelnemerDTO deelnemerDTO);

    default Deelnemer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deelnemer deelnemer = new Deelnemer();
        deelnemer.setId(id);
       
        return deelnemer;
    }
}
