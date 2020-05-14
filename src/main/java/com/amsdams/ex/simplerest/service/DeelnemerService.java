package com.amsdams.ex.simplerest.service;

import java.util.List;
import java.util.Optional;

import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;

/**
 * Service Interface for managing {@link com.amsdams.ex.simplerest.domain.Deelnemer}.
 */
public interface DeelnemerService {

    /**
     * Save a deelnemer.
     *
     * @param deelnemerDTO the entity to save.
     * @return the persisted entity.
     */
    DeelnemerDTO save(DeelnemerDTO deelnemerDTO);

    /**
     * Get all the deelnemers.
     *
     * @return the list of entities.
     */
    List<DeelnemerDTO> findAll();


    /**
     * Get the "id" deelnemer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeelnemerDTO> findOne(Long id);

}
