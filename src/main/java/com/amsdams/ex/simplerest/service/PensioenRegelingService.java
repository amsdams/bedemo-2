package com.amsdams.ex.simplerest.service;

import java.util.List;
import java.util.Optional;

import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;

/**
 * Service Interface for managing {@link com.amsdams.ex.simplerest.domain.PensioenRegeling}.
 */
public interface PensioenRegelingService {

    /**
     * Save a pensioenRegeling.
     *
     * @param pensioenRegelingDTO the entity to save.
     * @return the persisted entity.
     */
    PensioenRegelingDTO save(PensioenRegelingDTO pensioenRegelingDTO);

    /**
     * Get all the pensioenRegelings.
     *
     * @return the list of entities.
     */
    List<PensioenRegelingDTO> findAll();


    /**
     * Get the "id" pensioenRegeling.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PensioenRegelingDTO> findOne(Long id);

    /**
     * Delete the "id" pensioenRegeling.
     *
     * @param id the id of the entity.
     */
    //void delete(Long id);
}
