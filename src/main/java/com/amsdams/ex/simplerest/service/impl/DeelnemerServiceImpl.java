package com.amsdams.ex.simplerest.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amsdams.ex.simplerest.domain.Deelnemer;
import com.amsdams.ex.simplerest.repository.DeelnemerRepository;
import com.amsdams.ex.simplerest.service.DeelnemerService;
import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;
import com.amsdams.ex.simplerest.service.mapper.DeelnemerMapper;

/**
 * Service Implementation for managing {@link Deelnemer}.
 */
@Service
@Transactional
public class DeelnemerServiceImpl implements DeelnemerService {

    private final Logger log = LoggerFactory.getLogger(DeelnemerServiceImpl.class);

    private final DeelnemerRepository deelnemerRepository;

    private final DeelnemerMapper deelnemerMapper;

    public DeelnemerServiceImpl(DeelnemerRepository deelnemerRepository, DeelnemerMapper deelnemerMapper) {
        this.deelnemerRepository = deelnemerRepository;
        this.deelnemerMapper = deelnemerMapper;
    }

    /**
     * Save a deelnemer.
     *
     * @param deelnemerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeelnemerDTO save(DeelnemerDTO deelnemerDTO) {
        log.debug("Request to save Deelnemer : {}", deelnemerDTO);
        Deelnemer deelnemer = deelnemerMapper.toEntity(deelnemerDTO);
        deelnemer = deelnemerRepository.save(deelnemer);
        return deelnemerMapper.toDto(deelnemer);
    }

    /**
     * Get all the deelnemers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeelnemerDTO> findAll() {
        log.debug("Request to get all Deelnemers");
        return deelnemerRepository.findAll().stream()
            .map(deelnemerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one deelnemer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeelnemerDTO> findOne(Long id) {
        log.debug("Request to get Deelnemer : {}", id);
        return deelnemerRepository.findById(id)
            .map(deelnemerMapper::toDto);
    }

    /**
     * Delete the deelnemer by id.
     *
     * @param id the id of the entity.
     */
    /*@Override
    public void delete(Long id) {
        log.debug("Request to delete Deelnemer : {}", id);
        deelnemerRepository.deleteById(id);
    }
    */
}
