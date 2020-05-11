package com.amsdams.ex.simplerest.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amsdams.ex.simplerest.domain.PensioenRegeling;
import com.amsdams.ex.simplerest.repository.PensioenRegelingRepository;
import com.amsdams.ex.simplerest.service.PensioenRegelingService;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;
import com.amsdams.ex.simplerest.service.mapper.PensioenRegelingMapper;

/**
 * Service Implementation for managing {@link PensioenRegeling}.
 */
@Service
@Transactional
public class PensioenRegelingServiceImpl implements PensioenRegelingService {

    private final Logger log = LoggerFactory.getLogger(PensioenRegelingServiceImpl.class);

    private final PensioenRegelingRepository pensioenRegelingRepository;

    private final PensioenRegelingMapper pensioenRegelingMapper;

    public PensioenRegelingServiceImpl(PensioenRegelingRepository pensioenRegelingRepository, PensioenRegelingMapper pensioenRegelingMapper) {
        this.pensioenRegelingRepository = pensioenRegelingRepository;
        this.pensioenRegelingMapper = pensioenRegelingMapper;
    }

    /**
     * Save a pensioenRegeling.
     *
     * @param pensioenRegelingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PensioenRegelingDTO save(PensioenRegelingDTO pensioenRegelingDTO) {
        log.debug("Request to save PensioenRegeling : {}", pensioenRegelingDTO);
        PensioenRegeling pensioenRegeling = pensioenRegelingMapper.toEntity(pensioenRegelingDTO);
        pensioenRegeling = pensioenRegelingRepository.save(pensioenRegeling);
        return pensioenRegelingMapper.toDto(pensioenRegeling);
    }

    /**
     * Get all the pensioenRegelings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PensioenRegelingDTO> findAll() {
        log.debug("Request to get all PensioenRegelings");
        return pensioenRegelingRepository.findAll().stream()
            .map(pensioenRegelingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pensioenRegeling by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PensioenRegelingDTO> findOne(Long id) {
        log.debug("Request to get PensioenRegeling : {}", id);
        return pensioenRegelingRepository.findById(id)
            .map(pensioenRegelingMapper::toDto);
    }

    /**
     * Delete the pensioenRegeling by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PensioenRegeling : {}", id);
        pensioenRegelingRepository.deleteById(id);
    }
}
